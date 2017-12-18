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
public class RigidAlarmManagerRule implements CodeSmellRule{
    public boolean isRigidAlarmManager(ClassBean pClass) {
        for(MethodBean method:pClass.getMethods()){
            if(method.getTextContent().contains("AlarmManager")){
                return true;
            }
            for(MethodBean call:method.getMethodCalls()){
                if(call.getName().equals("setRepeating")){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isRigidAlarmManager(pClass);
    }
}