package it.morn.droidLens.smellDetectionRules;

import it.morn.droidLens.beans.ClassBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description: IDS的检测规则
 */
public class InefficientDataStructureRule implements CodeSmellRule {
    public String isInefficientDataStructure(ClassBean pClass) {
        Pattern regex = Pattern.compile("(.*)HashMap<(\\s*)(Integer|Long)(\\s*),(\\s*)(.+)(\\s*)>", Pattern.MULTILINE);
        Matcher regexMatcher = regex.matcher(pClass.getTextContent());
        if(regexMatcher.find())
            return "1";
        else
            return null;
    }

    @Override
    public String parser(ClassBean pClass) {
        return isInefficientDataStructure(pClass);
    }
}