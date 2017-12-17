package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description:
 */
public class DurableWakeLockRule {
    public DurableWakeLockRule() {
    }

    public boolean isDurableWakeLock(ClassBean pClass) {
        Pattern regex = Pattern.compile("(.*)acquire(\\s*)()", 8);
        Iterator var3 = pClass.getMethods().iterator();

        Matcher regexMatcher;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            MethodBean method = (MethodBean)var3.next();
            regexMatcher = regex.matcher(method.getTextContent());
        } while(!regexMatcher.find());

        return true;
    }
}