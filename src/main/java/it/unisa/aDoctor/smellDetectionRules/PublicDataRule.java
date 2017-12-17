package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class PublicDataRule {
    public PublicDataRule() {
    }

    public boolean isPublicData(ClassBean pClass) {
        Iterator var2 = pClass.getMethods().iterator();

        MethodBean method;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            method = (MethodBean)var2.next();
            if (method.getTextContent().contains("Context.MODE_WORLD_READABLE")) {
                return true;
            }
        } while(!method.getTextContent().contains("Context.MODE_WORLD_WRITEABLE"));

        return true;
    }
}