package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description:
 */
public class InefficientDataStructureRule implements CodeSmellRule {
    public boolean isInefficientDataStructure(ClassBean pClass) {
        Pattern regex = Pattern.compile("(.*)HashMap<(\\s*)(Integer|Long)(\\s*),(\\s*)(.+)(\\s*)>", Pattern.MULTILINE);
        Matcher regexMatcher = regex.matcher(pClass.getTextContent());
        return regexMatcher.find();
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isInefficientDataStructure(pClass);
    }
}