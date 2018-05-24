package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.MethodBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: PD的检测规则
 */
public class PublicDataRule implements CodeSmellRule {
    public boolean isPublicData(ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            String textContent = method.getTextContent();
            if (textContent.contains("Context.MODE_WORLD_WRITEABLE") ||
                    textContent.contains("Context.MODE_WORLD_READABLE")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isPublicData(pClass);
    }
}