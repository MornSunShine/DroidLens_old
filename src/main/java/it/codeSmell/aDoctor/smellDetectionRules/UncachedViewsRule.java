package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2018/5/23
 * Time: 16:27
 * Description: UV的检测规则
 */
public class UncachedViewsRule implements CodeSmellRule {
    @Override
    public String parser(ClassBean pClass) {
        return "null";
    }
}
