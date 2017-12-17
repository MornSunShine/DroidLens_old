package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class LeakingThreadRule {
    public LeakingThreadRule() {
    }

    public boolean isLeakingThread(ClassBean pClass) {
        return pClass.getTextContent().contains("run()") && !pClass.getTextContent().contains("stop()");
    }
}