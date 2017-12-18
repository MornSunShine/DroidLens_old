package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.MethodBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description:
 */
public class InefficientDataFormatAndParserRule implements CodeSmellRule {

    public boolean isInefficientDataFormatAndParser(ClassBean pClass) {
        for (String importedResource : pClass.getImports()) {
            if (importedResource.equals("javax.xml.parsers.DocumentBuilder")) {
                return true;
            }
        }

        Pattern regex = Pattern.compile("(DocumentBuilderFactory|DocumentBuilder|NodeList)", Pattern.MULTILINE);
        for (MethodBean methodBean : pClass.getMethods()) {
            Matcher regexMatcher = regex.matcher(methodBean.getTextContent());
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isInefficientDataFormatAndParser(pClass);
    }
}