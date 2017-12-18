package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.MethodBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class MemberIgnoringMethodRule implements CodeSmellRule {
    public boolean isMemberIgnoringMethod(ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            if (!method.isStatic() &&
                    pClass.getInstanceVariables().size() > 0 &&
                    method.getUsedInstanceVariables().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isMemberIgnoringMethod(pClass);
    }
}