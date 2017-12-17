package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description:
 */
public class InefficientDataStructureRule {
    public InefficientDataStructureRule() {
    }

    public boolean isInefficientDataStructure(ClassBean pClass) {
        Pattern regex = Pattern.compile("(.*)HashMap<(\\s*)(Integer|Long)(\\s*),(\\s*)(.+)(\\s*)>", 8);
        Matcher regexMatcher = regex.matcher(pClass.getTextContent());
        return regexMatcher.find();
    }
}