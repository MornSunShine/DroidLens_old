package it.unisa.aDoctor.smellDetectionRules;

import it.unisa.aDoctor.beans.ClassBean;
import it.unisa.aDoctor.beans.MethodBean;

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
 * Time: 1:02
 * Description:
 */
public class UnclosedCloseableRule {
    private final List<String> closeableClassList = new ArrayList();

    public UnclosedCloseableRule() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(NoLowMemoryResolverRule.class.getResourceAsStream("/unclosedCloseableClasses.txt")));
        Throwable var2 = null;

        try {
            while(br.ready()) {
                this.closeableClassList.add(br.readLine());
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

    public boolean isUnclosedCloseable(ClassBean pClass) {
        if (this.hasCloseableObject(pClass)) {
            Pattern regex = Pattern.compile("(.*)close(\\s*)()", 8);
            Matcher regexMatcher = regex.matcher(pClass.getTextContent());
            if (!regexMatcher.find()) {
                return true;
            }
        }

        return false;
    }

    public boolean hasCloseableObject(ClassBean pClass) {
        Iterator var2 = pClass.getMethods().iterator();

        while(var2.hasNext()) {
            MethodBean method = (MethodBean)var2.next();
            Iterator var4 = this.closeableClassList.iterator();

            while(var4.hasNext()) {
                String closeableObject = (String)var4.next();
                if (method.getTextContent().contains(closeableObject + " ")) {
                    return true;
                }
            }
        }

        return false;
    }
}