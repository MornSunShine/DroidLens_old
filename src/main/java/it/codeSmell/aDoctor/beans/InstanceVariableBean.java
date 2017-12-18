package it.codeSmell.aDoctor.beans;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:43
 * Description:
 */
public class InstanceVariableBean {
    private String visibility= null;
    private String type= null;
    private String name= null;
    private String initialization= null;

    public String getVisibility() {
        return this.visibility;
    }

    public void setVisibility(String pVisibility) {
        this.visibility = pVisibility;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String pType) {
        this.type = pType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public String getInitialization() {
        return this.initialization;
    }

    public void setInitialization(String pInitialization) {
        this.initialization = pInitialization;
    }

    public String toString() {
        return "(" + this.visibility + "|" + this.type + "|" + this.name + "|" + this.initialization + ")";
    }

    public boolean equals(Object arg) {
        return this.getName().equals(((InstanceVariableBean) arg).getName());
    }
}