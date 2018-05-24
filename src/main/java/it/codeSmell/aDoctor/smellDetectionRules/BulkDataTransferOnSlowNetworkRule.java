package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2018/5/23
 * Time: 15:48
 * Description: BDTOSN的检测规则
 */
public class BulkDataTransferOnSlowNetworkRule implements CodeSmellRule {
    @Override
    public boolean parser(ClassBean pClass) {
        return false;
    }
}
