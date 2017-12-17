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
public class InefficientSQLQueryRule {
    public InefficientSQLQueryRule() {
    }

    public boolean isInefficientSQLQuery(ClassBean pClass) {
        Iterator var2 = pClass.getImports().iterator();

        while(true) {
            String importString;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                importString = (String)var2.next();
            } while(!importString.contains("java.sql"));

            Iterator var4 = pClass.getMethods().iterator();

            while(var4.hasNext()) {
                MethodBean method = (MethodBean)var4.next();
                if (method.getTextContent().contains("Connection ") || method.getTextContent().contains("Statement ") || method.getTextContent().contains("ResultSet ")) {
                    return true;
                }
            }
        }
    }
}