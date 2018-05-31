package it.morn.droidLens.smellDetectionRules;

import it.morn.droidLens.beans.ClassBean;
import it.morn.droidLens.beans.MethodBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: RAM的检测规则
 */
public class RigidAlarmManagerRule implements CodeSmellRule{
    public String isRigidAlarmManager(ClassBean pClass) {
        for(MethodBean method:pClass.getMethods()){
            if(method.getTextContent().contains("AlarmManager")){
                return "1";
            }
            for(MethodBean call:method.getMethodCalls()){
                if(call.getName().equals("setRepeating")){
                    return "1";
                }
            }
        }
        return null;
    }

    @Override
    public String parser(ClassBean pClass) {
        return isRigidAlarmManager(pClass);
    }
}