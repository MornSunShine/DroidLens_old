package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class LeakingInnerClassRule {
    public LeakingInnerClassRule() {
    }

    public boolean isLeakingInnerClass(ClassBean pClass) {
        Iterator var2 = pClass.getInnerClasses().iterator();

        ClassBean inner;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            inner = (ClassBean)var2.next();
        } while(inner.isStatic());

        return true;
    }
}