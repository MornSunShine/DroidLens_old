package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2018/5/23
 * Time: 16:25
 * Description: PDT的检测规则
 */
public class ProhibitedDataTransferRule implements CodeSmellRule {
    @Override
    public String parser(ClassBean pClass) {
        return "null";
    }
}
