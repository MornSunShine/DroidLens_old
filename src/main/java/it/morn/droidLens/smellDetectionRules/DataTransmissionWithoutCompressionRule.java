package it.morn.droidLens.smellDetectionRules;

import it.morn.droidLens.beans.ClassBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:59
 * Description: DTWC的检测规则
 */
public class DataTransmissionWithoutCompressionRule implements CodeSmellRule {
    public String isDataTransmissionWithoutCompression(ClassBean pClassBean) {
        if(pClassBean.getTextContent().contains("File ") && !pClassBean.getTextContent().contains("zip"))
            return "1";
        else
            return null;
    }

    @Override
    public String parser(ClassBean pClass) {
        return isDataTransmissionWithoutCompression(pClass);
    }
}