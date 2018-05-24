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
 * Description: DWL的检测规则
 */
public class DurableWakeLockRule implements CodeSmellRule{
    public boolean isDurableWakeLock(ClassBean pClass) {
        Pattern regex = Pattern.compile("(.*)acquire(\\s*)()", Pattern.MULTILINE);
        for (MethodBean method : pClass.getMethods()) {
            Matcher regexMatcher = regex.matcher(method.getTextContent());
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isDurableWakeLock(pClass);
    }
}