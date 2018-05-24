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
    public boolean isInefficientSQLQuery(ClassBean pClass) {
        for(String importString:pClass.getImports()){
            if(importString.contains("java.sql")){
                return true;
            }
        }

        Pattern regex = Pattern.compile("(Connection |Statement |ResultSet )", Pattern.MULTILINE);
        for(MethodBean method:pClass.getMethods()){
            Matcher regexMatcher = regex.matcher(method.getTextContent());
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isInefficientSQLQuery(pClass);
    }
}