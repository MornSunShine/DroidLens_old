package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2018/5/23
 * Time: 16:30
 * Description: UT的检测规则
 */
public class UntohuchableRule implements CodeSmellRule {
    @Override
    public String parser(ClassBean pClass) {
        return "null";
    }
}
