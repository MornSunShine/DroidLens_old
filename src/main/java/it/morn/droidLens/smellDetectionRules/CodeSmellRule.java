package it.morn.droidLens.smellDetectionRules;

import it.morn.droidLens.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:03
 * Description: CodeSmellRule定义接口
 */
public interface CodeSmellRule {
    String parser(ClassBean pClass);
}
