package it.codeSmell.aDoctor.process;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.PackageBean;
import it.codeSmell.aDoctor.smellDetectionRules.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:56
 * Description:
 */
public class RunAndroidSmellDetection {
    private static final String NEW_LINE_SEPARATOR = "\n";
    public static String[] FILE_HEADER;

    public static void main(String[] args) throws IOException, CoreException {
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        System.out.println("Started at " + ft.format(new Date()));
        File experimentDirectory = FileUtils.getFile(args[0]);
        File fileName = new File(args[1]);
        String smellsNeeded = args[2];
        FILE_HEADER = new String[StringUtils.countMatches(smellsNeeded, "1") + 1];
        CodeSmellRule[] rules = new CodeSmellRule[15];
        rules[0] = new DataTransmissionWithoutCompressionRule();
        rules[1] = new DebuggableReleaseRule();
        rules[2] = new DurableWakeLockRule();
        rules[3] = new InefficientDataFormatAndParserRule();
        rules[4] = new InefficientDataStructureRule();
        rules[5] = new InefficientSQLQueryRule();
        rules[6] = new InternalGetterSetterRule();
        rules[7] = new LeakingInnerClassRule();
        rules[8] = new LeakingThreadRule();
        rules[9] = new MemberIgnoringMethodRule();
        rules[10] = new NoLowMemoryResolverRule();
        rules[11] = new PublicDataRule();
        rules[12] = new RigidAlarmManagerRule();
        rules[13] = new SlowLoopRule();
        rules[14] = new UnclosedCloseableRule();
        FILE_HEADER[0] = "Class";
        int headerCounter = 1;

        for (int i = 0; i < smellsNeeded.length(); i++) {
            if (smellsNeeded.charAt(i) == '1') {
                FILE_HEADER[headerCounter] = Utilities.CODES_MELL[i];
                headerCounter++;
            }
        }

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
        FileWriter fileWriter = new FileWriter(fileName);
        CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

        csvFilePrinter.printRecord(FILE_HEADER);
        for(File project:experimentDirectory.listFiles()){
            if (!project.isHidden()) {
                ArrayList<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
                for (PackageBean packageBean : packages) {
                    ArrayList record;
                    for (ClassBean classBean : packageBean.getClasses()) {
                        record = new ArrayList();
                        classBean = getAndroidManifest(classBean, project);
                        System.out.println("-- Analyzing class: " +
                                classBean.getBelongingPackage() + "." +
                                classBean.getName());
                        record.add(classBean.getBelongingPackage() + "." + classBean.getName());
                        for (int i = 0; i < 15; i++) {
                            if (smellsNeeded.charAt(i) == '1') {
                                if (rules[i].parser(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }
                        }
                        csvFilePrinter.printRecord(record);
                    }
                }
            }
        }

        csvFilePrinter.close();

        System.out.println("CSV file was created successfully!");
        System.out.println("Finished at " + ft.format(new Date()));
    }

    public static ClassBean getAndroidManifest(ClassBean classBean, File dir) {
        Collection<File> files = FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for (File file : files) {
            if (file.getName().equals("AndroidManifest.xml")) {
                classBean.setAndroidManifest(file);
                return classBean;
            }
        }
        return classBean;
    }
}