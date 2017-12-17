package it.unisa.aDoctor.process;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.PackageBean;
import it.unisa.aDoctor.parser.ClassParser;
import it.unisa.aDoctor.parser.CodeParser;
import it.unisa.aDoctor.parser.InnerClassVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:56
 * Description:
 */
public class FolderToJavaProjectConverter {

    public static ArrayList<ClassBean> extractClasses(String pPath) throws CoreException, IOException {
        ArrayList<ClassBean> system = new ArrayList();
        ArrayList<PackageBean> packages = convert(pPath);

        for (PackageBean packageBean : packages) {
            system.addAll(packageBean.getClasses());
        }
        return system;
    }

    public static ArrayList<PackageBean> convert(String pPath) throws CoreException, IOException {
        File projectDirectory = new File(pPath);
        CodeParser codeParser = new CodeParser();
        ArrayList<PackageBean> packages = new ArrayList();
        if (projectDirectory.isDirectory()) {
            File[] var4 = projectDirectory.listFiles();
            int var5 = var4.length;

            label102:
            for(int var6 = 0; var6 < var5; ++var6) {
                File subDir = var4[var6];
                if (subDir.isDirectory()) {
                    ArrayList<File> javaFiles = listJavaFiles(subDir);
                    if (javaFiles.size() > 0) {
                        Iterator var9 = javaFiles.iterator();

                        while(true) {
                            while(true) {
                                if (!var9.hasNext()) {
                                    continue label102;
                                }

                                File javaFile = (File)var9.next();
                                CompilationUnit parsed = codeParser.createParser(FileUtilities.readFile(javaFile.getAbsolutePath()));
                                TypeDeclaration typeDeclaration = (TypeDeclaration)parsed.types().get(0);

                                ArrayList<String> imports = new ArrayList();
                                for (Object importedResource : parsed.imports()) {
                                    imports.add(importedResource.toString());
                                }

                                InnerClassVisitor innerClassVisitor;
                                Collection innerTypes;
                                ArrayList innerClasses;
                                Iterator var19;
                                TypeDeclaration innerType;
                                ClassBean innerClass;
                                PackageBean packageBean;
                                ClassBean classBean;
                                if (!isAlreadyCreated(parsed.getPackage().getName().getFullyQualifiedName(), packages)) {
                                    packageBean = new PackageBean();
                                    packageBean.setName(parsed.getPackage().getName().getFullyQualifiedName());
                                    classBean = ClassParser.parse(typeDeclaration, packageBean.getName(), imports);
                                    classBean.setPathToClass(javaFile.getAbsolutePath());
                                    innerClassVisitor = new InnerClassVisitor();
                                    typeDeclaration.accept(innerClassVisitor);
                                    innerTypes = innerClassVisitor.getInnerClasses();
                                    innerClasses = new ArrayList();
                                    for(Iterator it=innerTypes.iterator();it.hasNext();){
                                        innerType = (TypeDeclaration)it.next();
                                        innerClass = ClassParser.parse(innerType, packageBean.getName(), imports);

                                        for(int i = 0; i < innerType.modifiers().size(); ++i) {
                                            if (("" + innerType.modifiers().get(i)).equals("static")) {
                                                innerClass.setStatic(true);
                                            }
                                        }

                                        innerClasses.add(innerClass);
                                    }

                                    classBean.setInnerClasses(innerClasses);
                                    packageBean.addClass(classBean);
                                    packages.add(packageBean);
                                } else {
                                    packageBean = getPackageByName(parsed.getPackage().getName().getFullyQualifiedName(), packages);
                                    classBean = ClassParser.parse(typeDeclaration, packageBean.getName(), imports);
                                    classBean.setPathToClass(javaFile.getAbsolutePath());
                                    innerClassVisitor = new InnerClassVisitor();
                                    typeDeclaration.accept(innerClassVisitor);
                                    innerTypes = innerClassVisitor.getInnerClasses();
                                    innerClasses = new ArrayList();
                                    var19 = innerTypes.iterator();

                                    while(var19.hasNext()) {
                                        innerType = (TypeDeclaration)var19.next();
                                        innerClass = ClassParser.parse(innerType, packageBean.getName(), imports);

                                        for(int i = 0; i < innerType.modifiers().size(); ++i) {
                                            if (("" + innerType.modifiers().get(i)).equals("static")) {
                                                innerClass.setStatic(true);
                                            }
                                        }

                                        innerClasses.add(innerClass);
                                    }

                                    classBean.setInnerClasses(innerClasses);
                                    packageBean.addClass(classBean);
                                }
                            }
                        }
                    }
                }
            }
        }

        Iterator var23 = packages.iterator();

        while(var23.hasNext()) {
            PackageBean pb = (PackageBean)var23.next();
            String textualContent = "";

            ClassBean cb;
            for(Iterator var26 = pb.getClasses().iterator(); var26.hasNext(); textualContent = textualContent + cb.getTextContent()) {
                cb = (ClassBean)var26.next();
            }

            pb.setTextContent(textualContent);
        }

        return packages;
    }

    private static ArrayList<File> listJavaFiles(File pDirectory) {
        ArrayList<File> javaFiles = new ArrayList();
        File[] fList = pDirectory.listFiles();
        if (fList != null) {
            for(File file:fList){
                if (file.isFile()) {
                    if (file.getName().contains(".java")) {
                        javaFiles.add(file);
                    }
                } else if (file.isDirectory()) {
                    File directory = new File(file.getAbsolutePath());
                    javaFiles.addAll(listJavaFiles(directory));
                }
            }
        }

        return javaFiles;
    }

    private static boolean isAlreadyCreated(String pPackageName, ArrayList<PackageBean> pPackages) {
        Iterator var2 = pPackages.iterator();

        PackageBean pb;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            pb = (PackageBean)var2.next();
        } while(!pb.getName().equals(pPackageName));

        return true;
    }

    private static PackageBean getPackageByName(String pPackageName, ArrayList<PackageBean> pPackages) {
        Iterator var2 = pPackages.iterator();

        PackageBean pb;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            pb = (PackageBean)var2.next();
        } while(!pb.getName().equals(pPackageName));

        return pb;
    }
}