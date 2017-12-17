package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class MemberIgnoringMethodRule {
    public MemberIgnoringMethodRule() {
    }

    public boolean isMemberIgnoringMethod(ClassBean pClass) {
        Iterator var2 = pClass.getMethods().iterator();

        MethodBean methodBean;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            methodBean = (MethodBean)var2.next();
        } while(methodBean.isStatic() || pClass.getInstanceVariables().size() <= 0 || !methodBean.getUsedInstanceVariables().isEmpty());

        return true;
    }
}