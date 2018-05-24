package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2018/5/23
 * Time: 16:15
 * Description: ERB的检测规则
 */
public class EarlyResourceBindingRule implements CodeSmellRule {
    @Override
    public boolean parser(ClassBean pClass) {
        return false;
    }
}
