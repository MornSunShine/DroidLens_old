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
 * Time: 1:03
 * Description: SCC的检测规则
 */
public class SetConfigChangesRule implements CodeSmellRule{
    public boolean isSetConfigChanges(ClassBean pClass) throws IOException {
        try {
            Pattern regex = Pattern.compile("(.*)android:configChanges(\\s*)=", Pattern.MULTILINE);
            for (Iterator str = FileUtils.lineIterator(pClass.getAndroidManifest()); str.hasNext(); ) {
                String row = (String) str.next();
                Matcher regexMatcher = regex.matcher(row);
                if (regexMatcher.find()) {
                    return true;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean parser(ClassBean pClass) {
        return false;
    }
}