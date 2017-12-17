package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

import java.io.IOException;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:59
 * Description:
 */
public class DataTransmissionWithoutCompressionRule {
    public DataTransmissionWithoutCompressionRule() {
    }

    public boolean isDataTransmissionWithoutCompression(ClassBean pClassBean) throws IOException {
        return pClassBean.getTextContent().contains("File ") && !pClassBean.getTextContent().contains("zip");
    }
}