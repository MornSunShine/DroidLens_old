package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class LeakingInnerClassRule implements CodeSmellRule{
    public boolean isLeakingInnerClass(ClassBean pClass) {
        for(ClassBean inner:pClass.getInnerClasses()){
            if(!inner.isStatic()){
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isLeakingInnerClass(pClass);
    }
}