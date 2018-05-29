package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

import java.io.IOException;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:59
 * Description: DTWC的检测规则
 */
public class DataTransmissionWithoutCompressionRule implements CodeSmellRule {
    public String isDataTransmissionWithoutCompression(ClassBean pClassBean) {
        return String.valueOf(pClassBean.getTextContent().contains("File ") && !pClassBean.getTextContent().contains("zip"));
    }

    @Override
    public String parser(ClassBean pClass) {
        return isDataTransmissionWithoutCompression(pClass);
    }
}