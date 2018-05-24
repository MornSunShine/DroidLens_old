package it.codeSmell.aDoctor.smellDetectionRules;

import it.codeSmell.aDoctor.beans.ClassBean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:00
 * Description: DR的检测规则
 */
public class DebuggableReleaseRule implements CodeSmellRule {

    public boolean isDebuggableRelease(ClassBean pClass) {
        try {
            Pattern regex = Pattern.compile("(.*)android:debuggable(\\s*)=(\\s*)\"true\"", Pattern.MULTILINE);
            for (Iterator str = FileUtils.lineIterator(pClass.getAndroidManifest()); str.hasNext(); ) {
                String row = (String) str.next();
                Matcher regexMatcher = regex.matcher(row);
                if (regexMatcher.find()) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return isDebuggableRelease(pClass);
    }
}