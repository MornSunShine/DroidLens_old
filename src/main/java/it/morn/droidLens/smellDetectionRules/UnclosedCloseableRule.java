package it.morn.droidLens.smellDetectionRules;

import it.morn.droidLens.beans.ClassBean;
import it.morn.droidLens.beans.MethodBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:02
 * Description: UC的检测规则
 */
public class UnclosedCloseableRule implements CodeSmellRule {
    private final List<String> closeableClassList = new ArrayList();

    public UnclosedCloseableRule() throws IOException {
        Reader in = new InputStreamReader(NoLowMemoryResolverRule.class.
                getResourceAsStream("/unclosedCloseableClasses.txt"));
        BufferedReader br = new BufferedReader(in);
        while (br.ready()) {
            this.closeableClassList.add(br.readLine());
        }
        br.close();
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

    private boolean hasCloseableObject(ClassBean pClass) {
        for (MethodBean method : pClass.getMethods()) {
            for (String closeableObject : this.closeableClassList) {
                if (method.getTextContent().contains(closeableObject + " ")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String parser(ClassBean pClass) {
        return null;
    }
}