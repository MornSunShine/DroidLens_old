package it.morn.droidLens.beans;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:43
 * Description:
 */
public class PackageBean {
    private String name = null;
    private Collection<ClassBean> classes = new ArrayList();
    private String textContent;

    public String getName() {
        return this.name;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public String getTextContent() {
        return this.textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Collection<ClassBean> getClasses() {
        return this.classes;
    }

    public void setClasses(Collection<ClassBean> pClasses) {
        this.classes = pClasses;
    }

    public void addClass(ClassBean pClass) {
        this.classes.add(pClass);
    }

    public void removeClass(ClassBean pClass) {
        this.classes.remove(pClass);
    }
}