package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:03
 * Description: CodeSmellRule定义接口
 */
public interface CodeSmellRule {
    String parser(ClassBean pClass);
}
