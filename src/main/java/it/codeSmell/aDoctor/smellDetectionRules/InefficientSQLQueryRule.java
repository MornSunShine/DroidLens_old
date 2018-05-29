package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.MethodBean;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description: ISQL的检测规则
 */
public class InefficientSQLQueryRule implements CodeSmellRule{
    public String isInefficientSQLQuery(ClassBean pClass) {
        for(String importString:pClass.getImports()){
            if(importString.contains("java.sql")){
                return "1";
            }
        }

        Pattern regex = Pattern.compile("(Connection |Statement |ResultSet )", Pattern.MULTILINE);
        for(MethodBean method:pClass.getMethods()){
            Matcher regexMatcher = regex.matcher(method.getTextContent());
            if (regexMatcher.find()) {
                return "1";
            }
        }
        return "null";
    }

    @Override
    public String parser(ClassBean pClass) {
        return isInefficientSQLQuery(pClass);
    }
}