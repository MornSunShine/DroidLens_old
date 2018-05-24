package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:01
 * Description: NLMR的检测规则
 */
public class NoLowMemoryResolverRule implements CodeSmellRule{
    private final List<String> onLowMemoryClassList = new ArrayList();

    public NoLowMemoryResolverRule() throws IOException {
        Reader in=new InputStreamReader(NoLowMemoryResolverRule.class.
                getResourceAsStream("/onLowMemoryClasses.txt"));
        BufferedReader br = new BufferedReader(in);
        while(br.ready()) {
            this.onLowMemoryClassList.add(br.readLine());
        }
        br.close();
    }

    public boolean isNoLowMemoryResolver(ClassBean pClass) {
        if (this.inheritsOnLowMemoryMethod(pClass)) {
            Pattern regex = Pattern.compile("onLowMemory", Pattern.MULTILINE);
            Matcher regexMatcher = regex.matcher(pClass.getTextContent());
            if (!regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

    private boolean inheritsOnLowMemoryMethod(ClassBean pClass) {
        if (pClass.getSuperclass() != null) {
            for(String className:this.onLowMemoryClassList){
                if (className.equals(pClass.getSuperclass())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isNoLowMemoryResolver(pClass);
    }
}