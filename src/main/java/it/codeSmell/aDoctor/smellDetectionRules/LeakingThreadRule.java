package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: LT的检测规则
 */
public class LeakingThreadRule implements CodeSmellRule{
    public boolean isLeakingThread(ClassBean pClass) {
        return pClass.getTextContent().contains("run()") && !pClass.getTextContent().contains("stop()");
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isLeakingThread(pClass);
    }
}