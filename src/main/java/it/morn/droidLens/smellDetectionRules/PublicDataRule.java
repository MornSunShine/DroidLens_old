package it.morn.droidLens.smellDetectionRules;

import it.morn.droidLens.beans.ClassBean;
import it.morn.droidLens.beans.MethodBean;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: PD的检测规则
 */
public class PublicDataRule implements CodeSmellRule {
    public String isPublicData(ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            String textContent = method.getTextContent();
            if (textContent.contains("Context.MODE_WORLD_WRITEABLE") ||
                    textContent.contains("Context.MODE_WORLD_READABLE")) {
                return "1";
            }
        }
        return null;
    }

    @Override
    public String parser(ClassBean pClass) {
        return isPublicData(pClass);
    }
}