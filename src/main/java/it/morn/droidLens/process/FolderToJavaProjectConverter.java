package it.morn.droidLens.process;

import it.morn.droidLens.beans.ClassBean;
import it.morn.droidLens.beans.PackageBean;
import it.morn.droidLens.parser.ClassParser;
import it.morn.droidLens.parser.CodeParser;
import it.morn.droidLens.parser.InnerClassVisitor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:56
 * Description:
 */
public class FolderToJavaProjectConverter {
    /**
     * 提取目录下的所有类
     * @param pPath 目录路径
     * @return 类列表
     * @throws IOException 读取异常
     */
    public static ArrayList<ClassBean> extractClasses(String pPath) throws IOException {
        ArrayList<ClassBean> system = new ArrayList();
        ArrayList<PackageBean> packages = convert(pPath);
        for (PackageBean packageBean : packages) {
            system.addAll(packageBean.getClasses());
        }
        return system;
    }

    /**
     *
     * @param pPath
     * @return
     * @throws IOException
     */
    public static ArrayList<PackageBean> convert(String pPath) throws IOException {
        File projectDirectory = new File(pPath);
        CodeParser codeParser = new CodeParser();
        ArrayList<PackageBean> packages = new ArrayList();
        if (projectDirectory.isDirectory()) {
            for (File subDir : projectDirectory.listFiles()) {
                if (subDir.isDirectory()) {
                    ArrayList<File> javaFiles = listJavaFiles(subDir);
                    if (javaFiles.size() > 0) {
                        for (File javaFile : javaFiles) {
                            CompilationUnit parsed = codeParser.createParser(FileUtilities.readFile(javaFile.getAbsolutePath()));
                            //测试代码
                            if (0 == parsed.types().size()) {
                                continue;
                            }
                            TypeDeclaration typeDeclaration = (TypeDeclaration) parsed.types().get(0);
                            ArrayList<String> imports = new ArrayList();
                            for (Object importedResource : parsed.imports()) {
                                imports.add(importedResource.toString());
                            }
                            PackageBean packageBean;
                            boolean tag = isAlreadyCreated(parsed.getPackage().getName().getFullyQualifiedName(), packages);
                            if (tag) {
                                packageBean = getPackageByName(parsed.getPackage().getName().getFullyQualifiedName(), packages);
                            } else {
                                packageBean = new PackageBean();
                                packageBean.setName(parsed.getPackage().getName().getFullyQualifiedName());
                            }
                            ArrayList innerClasses = new ArrayList();
                            InnerClassVisitor innerClassVisitor = new InnerClassVisitor();
                            Collection<TypeDeclaration> innerTypes = innerClassVisitor.getInnerClasses();
                            ClassBean classBean = ClassParser.parse(typeDeclaration, packageBean.getName(), imports);
                            classBean.setPathToClass(javaFile.getAbsolutePath());
                            typeDeclaration.accept(innerClassVisitor);
                            for (TypeDeclaration innerType : innerTypes) {
                                ClassBean innerClass = ClassParser.parse(innerType, packageBean.getName(), imports);
                                for (Object modifier : innerType.modifiers()) {
                                    if (modifier.equals("static")) {
                                        innerClass.setStatic(true);
                                    }
                                }
                                innerClasses.add(innerClass);
                            }
                            classBean.setInnerClasses(innerClasses);
                            packageBean.addClass(classBean);
                            if (!tag) {
                                packages.add(packageBean);
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < packages.size(); i++) {
            String textualContent = "";
            for (ClassBean cb : packages.get(i).getClasses()) {
                textualContent = textualContent + cb.getTextContent();
            }
            packages.get(i).setTextContent(textualContent);
        }
        return packages;
    }

    /**
     * 获取目录下的所有java文件
     * @param pDirectory 目标目录
     * @return 搜集的java文件列表
     */
    private static ArrayList<File> listJavaFiles(File pDirectory) {
        return  (ArrayList<File>) FileUtils.listFiles(pDirectory, FileFilterUtils.suffixFileFilter("java"), TrueFileFilter.INSTANCE);
    }

    /**
     * 包列表中包含指定包判断
     * @param pPackageName 包名
     * @param pPackages 包列表
     * @return 包含判断结果
     */
    private static boolean isAlreadyCreated(String pPackageName, ArrayList<PackageBean> pPackages) {
        for (PackageBean pb : pPackages) {
            if (pb.getName().equals(pPackageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从列表中寻找指定包
     * @param pPackageName 包名
     * @param pPackages 包列表
     * @return 需求的包对象
     */
    private static PackageBean getPackageByName(String pPackageName, ArrayList<PackageBean> pPackages) {
        for (PackageBean pb : pPackages) {
            if (pb.getName().equals(pPackageName)) {
                return pb;
            }
        }
        return null;
    }

    /**
     * 定位AndroidManifest.xml应用入口文件，针对目录下只有一个该文件的项目
     * @param project 项目目录
     * @return AndroidManifest.xml文件
     */
    public static File getAndroidManifest(File project) {
        Collection<File> files = FileUtils.
                listFiles(project, FileFilterUtils.suffixFileFilter("xml"), TrueFileFilter.INSTANCE);
        for (File file : files) {
            if (file.getName().equals("AndroidManifest.xml")) {
                return file;
            }
        }
        return null;
    }

    /**
     * 定位项目中的所有AndroidManifest.xml，针对项目中有多个入口文件的情况
     * @param project 项目目录
     * @return AndroidManifest.xml列表
     */
    public static Collection<File> getAndroidManifests(File project){
        return FileUtils.listFiles(project, FileFilterUtils.nameFileFilter("AndroidManifest.xml"), TrueFileFilter.INSTANCE);
    }

    /**
     * 递归搜集指定目录下所有给定名称的文件
     * @param list 文件列表
     * @param dir 目标目录
     * @param fileName 目标文件名称
     * @return 搜集的文件列表
     */
    public static ArrayList<File> getAndroidManifests(ArrayList<File> list,File dir,String fileName){
        File file=new File(dir.getAbsolutePath()+"\\"+fileName);
        if(file.exists()){
            list.add(file);
            return list;
        }
        for(File temp:dir.listFiles()){
            if(temp.isDirectory()){
                list=getAndroidManifests(list,temp,fileName);
            }
        }
        return list;
    }

    /**
     * 非递归遍历目录下所有文件，搜集指定名称文件
     * @param dir 目标目录
     * @param fileName 目标文件名
     * @return 搜集的文件列表
     */
    public static ArrayList<File> getAndroidManifests(File dir,String fileName){
        LinkedList<File> dirs=new LinkedList<>();
        ArrayList<File> list=new ArrayList<>();
        dirs.add(dir);
        File file;
        while(!dirs.isEmpty()){
            File temp=dirs.removeFirst();
            file=new File(temp.getAbsolutePath()+"\\"+fileName);
            if(file.exists()){
                list.add(file);
                continue;
            }
            for(File f:temp.listFiles()){
                if(f.isDirectory()){
                    dirs.add(f);
                }
            }
        }
        return list;
    }
}