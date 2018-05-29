package it.codeSmell.aDoctor.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Author: MaoMorn
 * Date: 2017/12/23
 * Time: 22:39
 * Description:
 */
public class Utilities {
    public static final String[] CODES_SMELL = {"DTWC", "DR", "DW", "IDFP", "IDS",
            "ISQLQ", "IGS", "LIC", "LT", "MIM",
            "NLMR", "PD", "RAM", "SL", "UC"};
    public static final String[] DESCRIPTION = {"Data Transmission Without Compression", "Debuggable Release", "Durable Wakelock",
            "Inefficient Data Format and Parser", "Inefficient Data Structure", "Inefficient SQL Query",
            "Internal Getter and Setter", "Leaking Inner Class", "Leaking Thread",
            "Member Ignoring Method", "No Low Memory Resolver", "Public Data",
            "Rigid Alarm Manager", "Slow Loop", "Unclosed Closable"};

    public static boolean createJsonFile(String jsonString, String filePath, String fileName){
        // 标记文件生成是否成功
        boolean flag = true;

        // 拼接文件完整路径
        String fullPath = filePath + File.separator + fileName + ".json";

        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

//            // 格式化json字符串
//            jsonString = JsonFormatTool.formatJson(jsonString);

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag;
    }
}
