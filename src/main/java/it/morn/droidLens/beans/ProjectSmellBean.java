package it.morn.droidLens.beans;

import java.util.List;

/**
 * Author: MaoMorn
 * Date: 2018/5/29
 * Time: 12:16
 * Description:
 */
public class ProjectSmellBean {
    private final String projectName;
    private final List<ClassSmellBean> classValues;

    public ProjectSmellBean(String projectName, List<ClassSmellBean> classValues) {
        this.projectName = projectName;
        this.classValues = classValues;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<ClassSmellBean> getClassValues() {
        return classValues;
    }
}
