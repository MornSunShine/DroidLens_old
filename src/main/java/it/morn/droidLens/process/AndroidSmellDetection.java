package it.morn.droidLens.process;

import com.google.gson.GsonBuilder;
import it.morn.droidLens.beans.ClassBean;
import it.morn.droidLens.beans.ClassSmellBean;
import it.morn.droidLens.beans.PackageBean;
import it.morn.droidLens.beans.ProjectSmellBean;
import it.morn.droidLens.smellDetectionRules.CodeSmellRule;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author: MaoMorn
 * Date: 2018/5/31
 * Time: 15:15
 * Description:
 */
public class AndroidSmellDetection {
    private File experimentDir;
    private File resultFile;
    private String smellsNeeded;
    private CodeSmellRule[] rules;
    private static URL url = AndroidSmellDetection.class.getResource("/rules.xml");
    private static final String NEW_LINE_SEPARATOR = "\n";
    public static String[] FILE_HEADER;

    public AndroidSmellDetection(String experimentDir, String resultFile, String smells) {
        this.experimentDir = FileUtils.getFile(experimentDir);
        this.resultFile = new File(resultFile);
        this.smellsNeeded = smells;
        initHeader();
        initRules();
    }

    private void initHeader(){
        FILE_HEADER = new String[StringUtils.countMatches(smellsNeeded, "1") + 1];
        FILE_HEADER[0] = "Class";
        int headerCounter = 1;
        for (int i = 0; i < smellsNeeded.length(); i++) {
            if (smellsNeeded.charAt(i) == '1') {
                FILE_HEADER[headerCounter] = Utilities.CODES_SMELL[i];
                headerCounter++;
            }
        }
    }

    private void initRules(){
        try {
            List rules= new SAXReader().read(url).getRootElement().element("rules").elements("rule");
            this.rules=new CodeSmellRule[rules.size()];
            int i=0;
            for (Object rule : rules) {
                this.rules[++i] = (CodeSmellRule) Class.
                        forName("it.morn.droidLens.smellDetectionRules." + ((Element) rule).
                                getText()).newInstance();
            }
        } catch (DocumentException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
        FileWriter fileWriter = new FileWriter(resultFile);
        CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
        csvFilePrinter.printRecord(FILE_HEADER);
        FolderToJavaProjectConverter.getAndroidManifests(experimentDir);
        String projectName=experimentDir.getName();
        ArrayList<ClassSmellBean> classSmellBeans=new ArrayList<>();

//        for(File project:experimentDir.listFiles()){
//            if (!project.isHidden()) {
//                ArrayList<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
//                for (PackageBean packageBean : packages) {
//                    ArrayList<String> record = new ArrayList();
//                    HashMap<String,String> classSmells;
//                    for (ClassBean classBean : packageBean.getClasses()) {
//                        record.clear();
//                        classBean.setAndroidManifest(androidManifestFile);
////                        classBean = getAndroidManifest(classBean, project);
//                        String className=classBean.getBelongingPackage() + "." + classBean.getName();
//                        classSmells= new HashMap<>();
//                        System.out.println("-- Analyzing class: " + className);
//                        record.add(className);
//                        for (int i = 0; i < 15; i++) {
//                            if (smellsNeeded.charAt(i) == '1') {
//                                String parserResult=rules[i].parser(classBean);
//                                record.add(parserResult);
//                                classSmells.put(Utilities.CODES_SMELL[i],parserResult);
//                            }
//                        }
//                        classSmellBeans.add(new ClassSmellBean(className,classSmells));
//                        csvFilePrinter.printRecord(record);
//                    }
//                }
//            }
//        }
        String jsonString=new GsonBuilder().serializeNulls().create().toJson(new ProjectSmellBean(projectName,classSmellBeans));
        Utilities.createJsonFile(jsonString,"E:\\IdeaProjects\\DroidLens\\res",projectName);

        csvFilePrinter.close();
    }
}
