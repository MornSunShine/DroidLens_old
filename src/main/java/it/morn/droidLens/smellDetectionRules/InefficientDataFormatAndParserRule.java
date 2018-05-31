package it.morn.droidLens.smellDetectionRules;

import it.morn.droidLens.beans.ClassBean;
import it.morn.droidLens.beans.MethodBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description: IDFAP的检测规则
 */
public class InefficientDataFormatAndParserRule implements CodeSmellRule {

    public String isInefficientDataFormatAndParser(ClassBean pClass) {
        for (String importedResource : pClass.getImports()) {
            if (importedResource.equals("javax.xml.parsers.DocumentBuilder")) {
                return "1";
            }
        }

        Pattern regex = Pattern.compile("(DocumentBuilderFactory|DocumentBuilder|NodeList)", Pattern.MULTILINE);
        for (MethodBean methodBean : pClass.getMethods()) {
            Matcher regexMatcher = regex.matcher(methodBean.getTextContent());
            if (regexMatcher.find()) {
                return "1";
            }
        }
        return null;
    }

    @Override
    public String parser(ClassBean pClass) {
        return isInefficientDataFormatAndParser(pClass);
    }
}