package it.codeSmell.aDoctor.process;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.PackageBean;
import it.codeSmell.aDoctor.parser.ClassParser;
import it.codeSmell.aDoctor.parser.CodeParser;
import it.codeSmell.aDoctor.parser.InnerClassVisitor;
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
    public static ArrayList<ClassBean> extractClasses(String pPath) throws IOException {
        ArrayList<ClassBean> system = new ArrayList();
        ArrayList<PackageBean> packages = convert(pPath);
        for (PackageBean packageBean : packages) {
            system.addAll(packageBean.getClasses());
        }
        return system;
    }

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

    private static ArrayList<File> listJavaFiles(File pDirectory) {
        return FileUtilities.listJavaFiles(pDirectory);
    }

    private static boolean isAlreadyCreated(String pPackageName, ArrayList<PackageBean> pPackages) {
        for (PackageBean pb : pPackages) {
            if (pb.getName().equals(pPackageName)) {
                return true;
            }
        }
        return false;
    }

    private static PackageBean getPackageByName(String pPackageName, ArrayList<PackageBean> pPackages) {
        for (PackageBean pb : pPackages) {
            if (pb.getName().equals(pPackageName)) {
                return pb;
            }
        }
        return null;
    }
}