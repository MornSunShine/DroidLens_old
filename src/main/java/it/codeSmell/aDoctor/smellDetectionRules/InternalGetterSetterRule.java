package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.InstanceVariableBean;
import it.codeSmell.aDoctor.beans.MethodBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description:
 */
public class InternalGetterSetterRule implements CodeSmellRule {
    public boolean isInternalGetterSetter(ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            for (MethodBean call : method.getMethodCalls()) {
                if (this.isGetterSetterMethod(call, pClass) &&
                        this.isInternalMethod(call, pClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isGetterSetterMethod(MethodBean pMethod, ClassBean pClass) {
        for (InstanceVariableBean var : pClass.getInstanceVariables()) {
            if (pMethod.getName().toLowerCase().equals("get" + var.getName()) ||
                    pMethod.getName().toLowerCase().equals("set" + var.getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isInternalMethod(MethodBean pMethod, ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            if (pMethod.getName().equals(method.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isInternalGetterSetter(pClass);
    }
}