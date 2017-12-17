package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.InstanceVariableBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description:
 */
public class InternalGetterSetterRule {
    public InternalGetterSetterRule() {
    }

    public boolean isInternalGetterSetter(ClassBean pClass) {
        Iterator var2 = pClass.getMethods().iterator();

        while(var2.hasNext()) {
            MethodBean method = (MethodBean)var2.next();
            Iterator var4 = method.getMethodCalls().iterator();

            while(var4.hasNext()) {
                MethodBean call = (MethodBean)var4.next();
                if (this.isGetterSetterMethod(call, pClass) && this.isInternalMethod(call, pClass)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isGetterSetterMethod(MethodBean pMethod, ClassBean pClass) {
        Iterator var3 = pClass.getInstanceVariables().iterator();

        InstanceVariableBean var;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            var = (InstanceVariableBean)var3.next();
            if (pMethod.getName().toLowerCase().equals("get" + var.getName())) {
                return true;
            }
        } while(!pMethod.getName().toLowerCase().equals("set" + var.getName()));

        return true;
    }

    private boolean isInternalMethod(MethodBean pMethod, ClassBean pClass) {
        Iterator var3 = pClass.getMethods().iterator();

        MethodBean method;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            method = (MethodBean)var3.next();
        } while(!pMethod.getName().equals(method.getName()));

        return true;
    }
}