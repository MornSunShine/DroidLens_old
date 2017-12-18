package it.codeSmell.aDoctor.parser;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.PackageBean;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:53
 * Description:
 */
public class PackageParser {
    public PackageParser() {
    }

    public static PackageBean parse(IPackageFragment pPackage) throws JavaModelException {
        PackageBean packageBean = new PackageBean();
        CodeParser codeParser = new CodeParser();
        String textualContent = "";
        ArrayList<ClassBean> classes = new ArrayList();
        packageBean.setName(pPackage.getElementName());
        ICompilationUnit[] var5 = pPackage.getCompilationUnits();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            ICompilationUnit cu = var5[var7];
            textualContent = textualContent + cu.getSource();
            CompilationUnit parsed = codeParser.createParser(cu.getSource());
            TypeDeclaration typeDeclaration = (TypeDeclaration)parsed.types().get(0);
            ArrayList<String> imported = new ArrayList();
            IImportDeclaration[] var12 = cu.getImports();
            int var13 = var12.length;

            for(int var14 = 0; var14 < var13; ++var14) {
                IImportDeclaration importedResource = var12[var14];
                imported.add(importedResource.getElementName());
            }

            classes.add(ClassParser.parse(typeDeclaration, packageBean.getName(), imported));
        }

        packageBean.setTextContent(textualContent);
        return packageBean;
    }
}