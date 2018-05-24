package it.codeSmell.aDoctor;

import it.codeSmell.aDoctor.parser.CodeParser;
import it.codeSmell.aDoctor.process.FileUtilities;
import it.codeSmell.aDoctor.process.RunAndroidSmellDetection;
import it.codeSmell.aDoctor.ui.SmellData;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2117/12/17
 * Time: 21:14
 * Description:
 */
public class Test {
//    public static void main(String[] args) {
//        Integer[] smellTypesNeeded = {1, 1, 1, 1, 1,
//                1, 1, 1, 1, 1,
//                1, 1, 1, 1, 1};
////        C:\Users\MaoMorn\Documents\detectApp\aCal\src\com\morphoss\acal\aCal.java
////        C:\Users\MaoMorn\Documents\detectApp\aCal\src\com\morphoss\acal\database\alarmmanager\ALARM_STATE.java
//        String smellTypesString = StringUtils.join(smellTypesNeeded),
//                inputPath = "C:\\Users\\MaoMorn\\Documents\\detectApp\\aCal",
//                outputPath = "C:\\Users\\MaoMorn\\Desktop\\a";
//        String[] paras = new String[]{inputPath, outputPath, smellTypesString};
////        try {
////            RunAndroidSmellDetection.main(paras);
////        } catch (CoreException | IOException e) {
////            System.out.println("Error!");
////        }
//
//
//        CodeParser codeParser = new CodeParser();
//        File javaFile=new File("C:\\Users\\MaoMorn\\Documents\\detectApp\\aCal\\src\\com\\morphoss\\acal\\aCal.java");
//        try {
//            CompilationUnit parsed = codeParser.createParser(FileUtilities.readFile(javaFile.getAbsolutePath()));
////            TypeDeclaration typeDeclaration = (TypeDeclaration) parsed.types().get(0);
//            TypeDeclaration type=(TypeDeclaration)parsed.types().get(0);
//            MethodDeclaration method=type.getMethods()[0];
//            Block block=method.getBody();
//            System.out.println(method.getBody());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static void main(String[] args){
        int i=10;
        System.out.println(String.valueOf(i));
    }
}
