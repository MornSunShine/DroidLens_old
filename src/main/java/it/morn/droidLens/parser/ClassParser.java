package it.morn.droidLens.parser;

import it.morn.droidLens.beans.ClassBean;
import it.morn.droidLens.beans.InstanceVariableBean;
import it.morn.droidLens.beans.MethodBean;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:45
 * Description:
 */
public class ClassParser {
    public static ClassBean parse(TypeDeclaration pClassNode) {
        int numberOfGetterOrSetter = 0;
        ClassBean classBean = new ClassBean();
        if (pClassNode.getSuperclassType() != null) {
            classBean.setSuperclass(pClassNode.getSuperclassType().toString());
        } else {
            classBean.setSuperclass(null);
        }

        classBean.setName(pClassNode.getName().toString());
        Collection<FieldDeclaration> instanceVariableNodes = new ArrayList();
        pClassNode.accept(new InstanceVariableVisitor(instanceVariableNodes));
        classBean.setTextContent(pClassNode.toString());
        Pattern newLine = Pattern.compile("\n");
        String[] lines = newLine.split(pClassNode.toString());
        classBean.setLOC(lines.length);
        Collection<InstanceVariableBean> instanceVariableBeans = new ArrayList();

        for (FieldDeclaration instanceVariableNode : instanceVariableNodes) {
            instanceVariableBeans.add(InstanceVariableParser.parse(instanceVariableNode));
        }

        classBean.setInstanceVariables(instanceVariableBeans);
        Collection<MethodDeclaration> methodNodes = new ArrayList();
        pClassNode.accept(new MethodVisitor(methodNodes));
        Collection<MethodBean> methodBeans = new ArrayList();

        for (MethodDeclaration methodNode : methodNodes) {
            if ((methodNode.getName().toString().contains("get") || methodNode.getName().toString().contains("set")) && methodNode.parameters().isEmpty()) {
                numberOfGetterOrSetter++;
            }
            methodBeans.add(MethodParser.parse(methodNode, instanceVariableBeans));
        }

        classBean.setNumberOfGetterAndSetter(numberOfGetterOrSetter);

        classBean.setMethods(methodBeans);
        return classBean;
    }

    public static ClassBean parse(TypeDeclaration pClassNode, String belongingPackage, List<String> imports) {
        int numberOfGetterOrSetter = 0;
        ClassBean classBean = new ClassBean();
        if (pClassNode.getSuperclassType() != null) {
            classBean.setSuperclass(pClassNode.getSuperclassType().toString());
        } else {
            classBean.setSuperclass(null);
        }

        classBean.setName(pClassNode.getName().toString());
        classBean.setImports(imports);
        classBean.setBelongingPackage(belongingPackage);
        Collection<FieldDeclaration> instanceVariableNodes = new ArrayList();
        pClassNode.accept(new InstanceVariableVisitor(instanceVariableNodes));
        classBean.setTextContent(pClassNode.toString());
        Pattern newLine = Pattern.compile("\n");
        String[] lines = newLine.split(pClassNode.toString());
        classBean.setLOC(lines.length);
        Collection<InstanceVariableBean> instanceVariableBeans = new ArrayList();

        for (FieldDeclaration instanceVariableNode : instanceVariableNodes) {
            instanceVariableBeans.add(InstanceVariableParser.parse(instanceVariableNode));
        }

        classBean.setInstanceVariables(instanceVariableBeans);
        Collection<MethodDeclaration> methodNodes = new ArrayList();
        pClassNode.accept(new MethodVisitor(methodNodes));
        Collection<MethodBean> methodBeans = new ArrayList();

        for (MethodDeclaration methodNode : methodNodes) {
            if ((methodNode.getName().toString().contains("get") || methodNode.getName().toString().contains("set")) && methodNode.parameters().isEmpty()) {
                numberOfGetterOrSetter++;
            }
            methodBeans.add(MethodParser.parse(methodNode, instanceVariableBeans));
        }

        classBean.setNumberOfGetterAndSetter(numberOfGetterOrSetter);

        classBean.setMethods(methodBeans);
        return classBean;
    }

    private static MethodBean isInto(MethodBean pClassMethodInvocation, Collection<MethodBean> pMethodBeans) {
        for (MethodBean methodBean : pMethodBeans) {
            if (methodBean.getName().equals(pClassMethodInvocation.getName())) {
                return methodBean;
            }
        }
        return null;
    }
}