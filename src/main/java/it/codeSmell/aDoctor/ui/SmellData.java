package it.codeSmell.aDoctor.ui;

import java.util.List;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:07
 * Description:
 */
public class SmellData {
    private final String className;
    private final List<String> values;

    public SmellData(String className, List<String> values) {
        this.className = className;
        this.values = values;
    }

    public String getClassName() {
        return this.className;
    }

    public List<String> getValues() {
        return this.values;
    }
}