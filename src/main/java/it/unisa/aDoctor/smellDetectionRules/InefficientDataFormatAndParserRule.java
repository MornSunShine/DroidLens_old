package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description:
 */
public class InefficientDataFormatAndParserRule {
    public InefficientDataFormatAndParserRule() {
    }

    public boolean isInefficientDataFormatAndParser(ClassBean pClass) {
        Iterator var2 = pClass.getImports().iterator();

        while(var2.hasNext()) {
            String importedResource = (String)var2.next();
            if (importedResource.equals("javax.xml.parsers.DocumentBuilder")) {
                return true;
            }
        }

        var2 = pClass.getMethods().iterator();

        MethodBean methodBean;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            methodBean = (MethodBean)var2.next();
        } while(!methodBean.getTextContent().contains("DocumentBuilderFactory") && !methodBean.getTextContent().contains("DocumentBuilder") && !methodBean.getTextContent().contains("NodeList"));

        return true;
    }
}