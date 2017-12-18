package it.codeSmell.aDoctor.process;

import it.codeSmell.aDoctor.beans.ClassBean;
import it.codeSmell.aDoctor.beans.PackageBean;
import it.codeSmell.aDoctor.smellDetectionRules.DataTransmissionWithoutCompressionRule;
import it.codeSmell.aDoctor.smellDetectionRules.DebuggableReleaseRule;
import it.codeSmell.aDoctor.smellDetectionRules.DurableWakeLockRule;
import it.codeSmell.aDoctor.smellDetectionRules.InefficientDataFormatAndParserRule;
import it.codeSmell.aDoctor.smellDetectionRules.InefficientDataStructureRule;
import it.codeSmell.aDoctor.smellDetectionRules.InefficientSQLQueryRule;
import it.codeSmell.aDoctor.smellDetectionRules.InternalGetterSetterRule;
import it.codeSmell.aDoctor.smellDetectionRules.LeakingInnerClassRule;
import it.codeSmell.aDoctor.smellDetectionRules.LeakingThreadRule;
import it.codeSmell.aDoctor.smellDetectionRules.MemberIgnoringMethodRule;
import it.codeSmell.aDoctor.smellDetectionRules.NoLowMemoryResolverRule;
import it.codeSmell.aDoctor.smellDetectionRules.PublicDataRule;
import it.codeSmell.aDoctor.smellDetectionRules.RigidAlarmManagerRule;
import it.codeSmell.aDoctor.smellDetectionRules.SlowLoopRule;
import it.codeSmell.aDoctor.smellDetectionRules.UnclosedCloseableRule;
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

    public RunAndroidSmellDetection() {
    }

    public static void main(String[] args) throws IOException, CoreException {
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        System.out.println("Started at " + ft.format(new Date()));
        File experimentDirectory = FileUtils.getFile(args[0]);
        File fileName = new File(args[1]);
        String smellsNeeded = args[2];
        FILE_HEADER = new String[StringUtils.countMatches(smellsNeeded, "1") + 1];
        DataTransmissionWithoutCompressionRule DTWC = new DataTransmissionWithoutCompressionRule();
        DebuggableReleaseRule DR = new DebuggableReleaseRule();
        DurableWakeLockRule DW = new DurableWakeLockRule();
        InefficientDataFormatAndParserRule IDFP = new InefficientDataFormatAndParserRule();
        InefficientDataStructureRule IDS = new InefficientDataStructureRule();
        InefficientSQLQueryRule ISQLQ = new InefficientSQLQueryRule();
        InternalGetterSetterRule IGS = new InternalGetterSetterRule();
        LeakingInnerClassRule LIC = new LeakingInnerClassRule();
        LeakingThreadRule LT = new LeakingThreadRule();
        MemberIgnoringMethodRule MIM = new MemberIgnoringMethodRule();
        NoLowMemoryResolverRule NLMR = new NoLowMemoryResolverRule();
        PublicDataRule PD = new PublicDataRule();
        RigidAlarmManagerRule RAM = new RigidAlarmManagerRule();
        SlowLoopRule SL = new SlowLoopRule();
        UnclosedCloseableRule UC = new UnclosedCloseableRule();
        String[] smellsType = new String[]{"DTWC", "DR", "DW", "IDFP", "IDS",
                "ISQLQ", "IGS", "LIC", "LT", "MIM",
                "NLMR", "PD", "RAM", "SL", "UC"};
        FILE_HEADER[0] = "Class";
        int headerCounter = 1;

        for (int i = 0; i < smellsNeeded.length(); ++i) {
            if (smellsNeeded.charAt(i) == '1') {
                FILE_HEADER[headerCounter] = smellsType[i];
                ++headerCounter;
            }
        }

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
        FileWriter fileWriter = new FileWriter(fileName);
        CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
        Throwable var25 = null;

        try {
            csvFilePrinter.printRecord((Object[]) FILE_HEADER);
            File[] var26 = experimentDirectory.listFiles();
            int var27 = var26.length;

            for (int var28 = 0; var28 < var27; ++var28) {
                File project = var26[var28];
                if (!project.isHidden()) {
                    ArrayList<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
                    Iterator var31 = packages.iterator();

                    while (var31.hasNext()) {
                        PackageBean packageBean = (PackageBean) var31.next();

                        ArrayList record;
                        for (Iterator var33 = packageBean.getClasses().iterator(); var33.hasNext(); csvFilePrinter.printRecord(record)) {
                            ClassBean classBean = (ClassBean) var33.next();
                            record = new ArrayList();
                            System.out.println("-- Analyzing class: " + classBean.getBelongingPackage() + "." + classBean.getName());
                            record.add(classBean.getBelongingPackage() + "." + classBean.getName());
                            if (smellsNeeded.charAt(0) == '1') {
                                if (DTWC.isDataTransmissionWithoutCompression(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(1) == '1') {
                                if (DR.isDebuggableRelease(getAndroidManifest(project))) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(2) == '1') {
                                if (DW.isDurableWakeLock(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(3) == '1') {
                                if (IDFP.isInefficientDataFormatAndParser(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(4) == '1') {
                                if (IDS.isInefficientDataStructure(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(5) == '1') {
                                if (ISQLQ.isInefficientSQLQuery(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(6) == '1') {
                                if (IGS.isInternalGetterSetter(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(7) == '1') {
                                if (LIC.isLeakingInnerClass(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(8) == '1') {
                                if (LT.isLeakingThread(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(9) == '1') {
                                if (MIM.isMemberIgnoringMethod(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(10) == '1') {
                                if (NLMR.isNoLowMemoryResolver(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(11) == '1') {
                                if (PD.isPublicData(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(12) == '1') {
                                if (RAM.isRigidAlarmManager(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(13) == '1') {
                                if (SL.isSlowLoop(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }

                            if (smellsNeeded.charAt(14) == '1') {
                                if (UC.isUnclosedCloseable(classBean)) {
                                    record.add("1");
                                } else {
                                    record.add("0");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable var43) {
            var25 = var43;
            throw var43;
        } finally {
            if (csvFilePrinter != null) {
                if (var25 != null) {
                    try {
                        csvFilePrinter.close();
                    } catch (Throwable var42) {
                        var25.addSuppressed(var42);
                    }
                } else {
                    csvFilePrinter.close();
                }
            }

        }

        System.out.println("CSV file was created successfully!");
        System.out.println("Finished at " + ft.format(new Date()));
    }

    public static ClassBean getAndroidManifest(File dir) {
        Collection<File> files = FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for(File file:files){
            if (file.getName().equals("AndroidManifest.xml")) {
                ClassBean androidManifest=new ClassBean();
                androidManifest.setAndroidManifest(file);
                return androidManifest;
            }
        }
        return null;
    }
}