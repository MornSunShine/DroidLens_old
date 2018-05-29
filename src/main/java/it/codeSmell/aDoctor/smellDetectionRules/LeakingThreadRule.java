package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: LT的检测规则
 */
public class LeakingThreadRule implements CodeSmellRule{
    public String isLeakingThread(ClassBean pClass) {
        return String.valueOf(pClass.getTextContent().contains("run()") && !pClass.getTextContent().contains("stop()"));
    }

    @Override
    public String parser(ClassBean pClass) {
        return isLeakingThread(pClass);
    }
}