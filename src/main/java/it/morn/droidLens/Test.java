package it.morn.droidLens;

import it.morn.droidLens.process.FolderToJavaProjectConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Test {
    public static void test(String[] args) throws IOException {
        long start,end;
        File project=new File("C:\\Users\\MaoMorn\\Documents\\detectApp\\guitar");
        start=System.currentTimeMillis();

        Collection<File> list=FileUtils.listFiles(project, FileFilterUtils.suffixFileFilter("xml"), TrueFileFilter.INSTANCE);
        for(File file:list){
            if (file.getName().equals("AndroidManifest.xml")) {
                System.out.println(file.getAbsolutePath());
            }
        }
        end=System.currentTimeMillis();
        System.out.println(end-start);
//对比测试样例**************************二二二
        start=System.currentTimeMillis();
//        try {
//            FileUtils.directoryContains(project,new File("AndroidManifest.xml"));
//            project.
//            list=FileUtils.listFilesAndDirs(project,null,TrueFileFilter.INSTANCE);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(new File("C:\\Users\\MaoMorn\\Documents\\detectApp\\aCal\\AndroidManifest.xml").exists());
        end=System.currentTimeMillis();
        System.out.println(end-start);
//对比测试样例**************************三三三
        start=System.currentTimeMillis();
        System.out.println(FileUtils.directoryContains(new File("C:\\Users\\MaoMorn\\Documents\\detectApp\\aCal"),new File("C:\\Users\\MaoMorn\\Documents\\detectApp\\aCal\\AndroidManifest.xml")));
        end=System.currentTimeMillis();
        System.out.println(end-start);
    }

    public static void main(String[] args) throws IOException {
//初始化区*****************************************
        ArrayList<File> list=new ArrayList<>();
        LinkedList<File> link=new LinkedList<>();
        String fileName="AndroidManifest.xml";
        File dir=new File("C:\\Users\\MaoMorn\\Documents\\detectApp\\guitar");
        long start,end;
        start=System.currentTimeMillis();
        list= FolderToJavaProjectConverter.getAndroidManifests(list,dir,fileName);
        System.out.println(list.size());
        for(File file:list){
            System.out.println(file.getAbsolutePath());
        }
        end=System.currentTimeMillis();
        System.out.println(end-start);
//对比测试样例**************************二二二
        start=System.currentTimeMillis();
        list= FolderToJavaProjectConverter.getAndroidManifests(dir,fileName);
        System.out.println(list.size());
        for(File file:list){
            System.out.println(file.getAbsolutePath());
        }

        end=System.currentTimeMillis();
        System.out.println(end-start);
//对比测试样例**************************三三三
        start=System.currentTimeMillis();
//        link=(LinkedList<File>) FolderToJavaProjectConverter.getAndroidManifests(dir);
//        System.out.println(link.size());
//        for(File file:link){
//            System.out.println(file.getAbsolutePath());
//        }

        end=System.currentTimeMillis();
        System.out.println(end-start);
    }
}