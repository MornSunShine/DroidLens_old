package it.unisa.aDoctor.smellDetectionRules;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 1:03
 * Description:
 */
public class SetConfigChangesRule {
    public SetConfigChangesRule() {
    }

    public boolean isSetConfigChanges(File androidManifest) throws IOException {
        Pattern regex = Pattern.compile("(.*)android:configChanges(\\s*)=", 8);
        LineIterator iter = FileUtils.lineIterator(androidManifest);

        Matcher regexMatcher;
        do {
            if (!iter.hasNext()) {
                return false;
            }

            String row = iter.next();
            regexMatcher = regex.matcher(row);
        } while(!regexMatcher.find());

        return true;
    }
}