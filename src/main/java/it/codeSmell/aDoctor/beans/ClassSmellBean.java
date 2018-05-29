package it.codeSmell.aDoctor.beans;

import java.util.Map;

/**
 * Author: MaoMorn
 * Date: 2018/5/29
 * Time: 12:08
 * Description:
 */
public class ClassSmellBean {
    private final String className;
    private final Map<String,String> smellData;

    public ClassSmellBean(String className,Map<String,String> smellData){
        this.className=className;
        this.smellData=smellData;
    }

    public String getClassName() {
        return className;
    }

    public Map<String, String> getSmellData() {
        return smellData;
    }
}
