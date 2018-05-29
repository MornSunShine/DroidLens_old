package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.MethodBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: MIM的检测规则
 */
public class MemberIgnoringMethodRule implements CodeSmellRule {
    public String isMemberIgnoringMethod(ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            if (!method.isStatic() &&
                    pClass.getInstanceVariables().size() > 0 &&
                    method.getUsedInstanceVariables().isEmpty()) {
                return "1";
            }
        }
        return "null";
    }

    @Override
    public String parser(ClassBean pClass) {
        return isMemberIgnoringMethod(pClass);
    }
}