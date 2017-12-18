package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

import java.io.IOException;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:59
 * Description:
 */
public class DataTransmissionWithoutCompressionRule implements CodeSmellRule {
    public boolean isDataTransmissionWithoutCompression(ClassBean pClassBean) {
        return pClassBean.getTextContent().contains("File ") && !pClassBean.getTextContent().contains("zip");
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isDataTransmissionWithoutCompression(pClass);
    }
}