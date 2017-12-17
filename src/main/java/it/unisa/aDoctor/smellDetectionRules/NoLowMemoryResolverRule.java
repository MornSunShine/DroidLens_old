package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description:
 */
public class NoLowMemoryResolverRule {
    private final List<String> onLowMemoryClassList = new ArrayList();

    public NoLowMemoryResolverRule() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(NoLowMemoryResolverRule.class.getResourceAsStream("/onLowMemoryClasses.txt")));
        Throwable var2 = null;

        try {
            while(br.ready()) {
                this.onLowMemoryClassList.add(br.readLine());
            }
        } catch (Throwable var11) {
            var2 = var11;
            throw var11;
        } finally {
            if (br != null) {
                if (var2 != null) {
                    try {
                        br.close();
                    } catch (Throwable var10) {
                        var2.addSuppressed(var10);
                    }
                } else {
                    br.close();
                }
            }

        }

    }

    public boolean isNoLowMemoryResolver(ClassBean pClass) {
        if (this.inheritsOnLowMemoryMethod(pClass)) {
            Pattern regex = Pattern.compile("onLowMemory", 8);
            Matcher regexMatcher = regex.matcher(pClass.getTextContent());
            if (!regexMatcher.find()) {
                return true;
            }
        }

        return false;
    }

    public boolean inheritsOnLowMemoryMethod(ClassBean pClass) {
        if (pClass.getSuperclass() != null) {
            Iterator var2 = this.onLowMemoryClassList.iterator();

            while(var2.hasNext()) {
                String className = (String)var2.next();
                if (className.equals(pClass.getSuperclass())) {
                    return true;
                }
            }
        }

        return false;
    }
}