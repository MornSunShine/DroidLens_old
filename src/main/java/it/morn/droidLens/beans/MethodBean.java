package it.morn.droidLens.beans;

import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:43
 * Description:
 */
public class MethodBean {
    private ClassBean belongingClass;
    private String name;
    private String textContent;
    private Collection<InstanceVariableBean> usedInstanceVariables= new ArrayList();
    private Collection<MethodBean> methodCalls= new ArrayList();
    private Type returnType;
    private List<SingleVariableDeclaration> parameters;
    private boolean isStatic;

    public String getName() {
        return this.name;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public String getTextContent() {
        return this.textContent;
    }

    public void setTextContent(String pTextContent) {
        this.textContent = pTextContent;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public Collection<InstanceVariableBean> getUsedInstanceVariables() {
        return this.usedInstanceVariables;
    }

    public void setUsedInstanceVariables(Collection<InstanceVariableBean> pUsedInstanceVariables) {
        this.usedInstanceVariables = pUsedInstanceVariables;
    }

    public void addUsedInstanceVariables(InstanceVariableBean pInstanceVariable) {
        this.usedInstanceVariables.add(pInstanceVariable);
    }

    public void removeUsedInstanceVariables(InstanceVariableBean pInstanceVariable) {
        this.usedInstanceVariables.remove(pInstanceVariable);
    }

    public Collection<MethodBean> getMethodCalls() {
        return this.methodCalls;
    }

    public void setMethodCalls(Collection<MethodBean> pMethodCalls) {
        this.methodCalls = pMethodCalls;
    }

    public void addMethodCalls(MethodBean pMethodCall) {
        this.methodCalls.add(pMethodCall);
    }

    public void removeMethodCalls(MethodBean pMethodCall) {
        this.methodCalls.remove(pMethodCall);
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        String content = this.textContent.length() > 10 ? this.textContent.replace("\n", " ")
                .replace("\t", "")
                .substring(0, 10)
                .concat("...") : "";
        str.append("(").append(this.name).append("|")
                .append(content)
                .append("|");

        for (InstanceVariableBean usedInstanceVariable : this.usedInstanceVariables) {
            str.append((usedInstanceVariable).getName())
                    .append(",");
        }

        str.deleteCharAt(str.length() - 1)
                .append("|");
        for (MethodBean methodCall : this.methodCalls) {
            str.append((methodCall).getName())
                    .append(",");
        }
        str.deleteCharAt(str.length() - 1)
                .append(")");
        return str.toString();
    }

    public int compareTo(Object o) {
        return this.getName().compareTo(((MethodBean) o).getName());
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<SingleVariableDeclaration> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<SingleVariableDeclaration> parameters) {
        this.parameters = parameters;
    }

    public ClassBean getBelongingClass() {
        return this.belongingClass;
    }

    public void setBelongingClass(ClassBean belongingClass) {
        this.belongingClass = belongingClass;
    }

    public boolean equals(Object arg) {
        return this.getName().equals(((MethodBean) arg).getName());
    }
}