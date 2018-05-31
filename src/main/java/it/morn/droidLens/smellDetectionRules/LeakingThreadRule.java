package it.morn.droidLens.smellDetectionRules;

import it.morn.droidLens.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: LT的检测规则
 */
public class LeakingThreadRule implements CodeSmellRule{
    public String isLeakingThread(ClassBean pClass) {
        if(pClass.getTextContent().contains("run()") && !pClass.getTextContent().contains("stop()"))
            return "1";
        else
            return null;
    }

    @Override
    public String parser(ClassBean pClass) {
        return isLeakingThread(pClass);
    }
}