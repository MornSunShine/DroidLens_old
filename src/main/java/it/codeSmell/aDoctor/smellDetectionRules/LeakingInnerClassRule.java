package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: LIC的检测规则
 */
public class LeakingInnerClassRule implements CodeSmellRule{
    public String isLeakingInnerClass(ClassBean pClass) {
        for(ClassBean inner:pClass.getInnerClasses()){
            if(!inner.isStatic()){
                return "1";
            }
        }
        return "null";
    }

    @Override
    public String parser(ClassBean pClass) {
        return isLeakingInnerClass(pClass);
    }
}