package it.morn.droidLens.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:37
 * Description:有关Class的实体类，包含Class相关的基本属性，包括名称、父类、内部类、包、方法等基本属性
 */
public final class ClassBean {
    private String name = null;
    private Collection<InstanceVariableBean> instanceVariables=new ArrayList();
    private Collection<MethodBean> methods=new ArrayList();
    private Collection<String> imports;
    private Collection<ClassBean> innerClasses;
    private String textContent;
    private int LOC;
    private String superclass;
    private String belongingPackage;
    private String pathToClass;
    private int entityClassUsage;
    private boolean isStatic;
    private File androidManifest;

    public int getLOC() {
        return this.LOC;
    }

    public void setLOC(int lOC) {
        this.LOC = lOC;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public Collection<InstanceVariableBean> getInstanceVariables() {
        return this.instanceVariables;
    }

    public void setInstanceVariables(Collection<InstanceVariableBean> pInstanceVariables) {
        this.instanceVariables = pInstanceVariables;
    }

    public void addInstanceVariables(InstanceVariableBean pInstanceVariable) {
        this.instanceVariables.add(pInstanceVariable);
    }

    public void removeInstanceVariables(InstanceVariableBean pInstanceVariable) {
        this.instanceVariables.remove(pInstanceVariable);
    }

    public Collection<MethodBean> getMethods() {
        return this.methods;
    }

    public void setMethods(Collection<MethodBean> pMethods) {
        this.methods = pMethods;
    }

    public void addMethod(MethodBean pMethod) {
        this.methods.add(pMethod);
    }

    public void removeMethod(MethodBean pMethod) {
        this.methods.remove(pMethod);
    }

    public String toString() {
        return "name = " + this.name + "\ninstanceVariables = " + this.instanceVariables + "\nmethods = " + this.methods + "\n";
    }

    public int compareTo(Object pClassBean) {
        return this.getName().compareTo(((ClassBean) pClassBean).getName());
    }

    public Collection<String> getImports() {
        return this.imports;
    }

    public void setImports(Collection<String> imports) {
        this.imports = imports;
    }

    public String getTextContent() {
        return this.textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getSuperclass() {
        return this.superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public String getBelongingPackage() {
        return this.belongingPackage;
    }

    public void setBelongingPackage(String belongingPackage) {
        this.belongingPackage = belongingPackage;
    }

    public int getEntityClassUsage() {
        return this.entityClassUsage;
    }

    public String getPathToClass() {
        return this.pathToClass;
    }

    public void setPathToClass(String pathToClass) {
        this.pathToClass = pathToClass;
    }

    public void setNumberOfGetterAndSetter(int entityClassUsage) {
        this.entityClassUsage = entityClassUsage;
    }

    public boolean equals(Object arg) {
        return this.getName().equals(((ClassBean) arg).getName()) && this.getBelongingPackage().equals(((ClassBean) arg).getBelongingPackage());
    }

    public Collection<ClassBean> getInnerClasses() {
        return this.innerClasses;
    }

    public void setInnerClasses(Collection<ClassBean> innerClasses) {
        this.innerClasses = innerClasses;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public File getAndroidManifest() {
        return androidManifest;
    }

    public void setAndroidManifest(File androidManifest) {
        this.androidManifest = androidManifest;
    }
}