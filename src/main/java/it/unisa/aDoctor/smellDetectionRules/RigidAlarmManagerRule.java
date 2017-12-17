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
public class RigidAlarmManagerRule {
    public RigidAlarmManagerRule() {
    }

    public boolean isRigidAlarmManager(ClassBean pClass) {
        Iterator var2 = pClass.getMethods().iterator();

        while(true) {
            MethodBean method;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                method = (MethodBean)var2.next();
            } while(!method.getTextContent().contains("AlarmManager"));

            Iterator var4 = method.getMethodCalls().iterator();

            while(var4.hasNext()) {
                MethodBean call = (MethodBean)var4.next();
                if (call.getName().equals("setRepeating")) {
                    return true;
                }
            }
        }
    }
}