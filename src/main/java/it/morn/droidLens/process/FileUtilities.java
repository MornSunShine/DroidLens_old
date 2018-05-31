package it.morn.droidLens.process;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Author: MaoMorn
 * Date: 2017/11/4
 * Time: 0:55
 * Description:
 */
public class FileUtilities {
    /**
     * 读取文件内容
     * @param nomeFile 目标文件路径
     * @return 目标文件内容
     * @throws IOException 文件读取异常
     */
    public static String readFile(String nomeFile) throws IOException {
        InputStreamReader isr = null;
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        try {
            InputStream is = new FileInputStream(nomeFile);
            isr = new InputStreamReader(is);
            int len;
            while ((len = isr.read(buf)) > 0) {
                sb.append(buf, 0, len);
            }
            return sb.toString();
        } finally {
            if (isr != null) {
                isr.close();
            }
        }
    }

    /**
     * 从字符串块中提取Class块(通过{}进行Class块的获取)
     * @param srcMLString 多行的String字符串
     * @param start 目标块前缀字符串
     * @param end 目标块后缀字符串
     * @return 剔除'\n'的Class块
     */
    public static String getClassFromSrcMLString(String srcMLString, String start, String end) {
        int countClass = 0;
        StringBuilder toReturn = new StringBuilder();
        Pattern newLine = Pattern.compile("\n");
        String[] lines = newLine.split(srcMLString);

        for (String line : lines) {
            if (line.contains(start)) {
                countClass++;
                toReturn.append(line);
            }

            if (line.contains(end)) {
                countClass--;//该处存在疑问
                toReturn.append(line);
                if (countClass == 0) {
                    return toReturn.toString();
                }
            } else {
                toReturn.append(line);
            }
        }
        return null;
    }

    /**
     * 复制目录或文件内容到目标路径
     * @param srcPath 待复制目录原路径
     * @param dstPath 目标路径
     * @throws IOException 文件读取写入异常
     */
    public void copyDirectory(File srcPath, File dstPath) throws IOException {
        if (srcPath.isDirectory()) {
            if (!dstPath.exists()) {
                dstPath.mkdir();
            }
            String[] files = srcPath.list();
            for (String file : files) {
                this.copyDirectory(new File(srcPath, file), new File(dstPath, file));
            }
        } else if (!srcPath.exists()) {
            System.out.println("File or directory does not exist.");
            System.exit(0);
        } else {
            InputStream in = new FileInputStream(srcPath);
            FileOutputStream out = new FileOutputStream(dstPath);
            byte[] buf = new byte[1024];

            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
        System.out.println("Directory copied.");
    }

    /**
     * 删除目录
     * @param dir 目标文件
     * @return 操作成功与否
     */
    public static boolean DelDir(File dir) {
        if (dir.isDirectory()) {
            String[] dirList = dir.list();
            for (String subList : dirList) {
                boolean success = DelDir(new File(dir, subList));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 将字符串写入文件
     * @param pContent 待写入内容
     * @param pPath 目标文件位置
     */
    public static void writeFile(String pContent, String pPath) {
        File file = new File(pPath);
        try {
            FileWriter fStream = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fStream);
            out.write(pContent);
            out.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * 获取java文件列表
     * @param pDirectory 目标目录文件
     * @return java文件列表
     */
    public static ArrayList<File> listJavaFiles(File pDirectory) {
        ArrayList<File> javaFiles = new ArrayList();
        File[] fList = pDirectory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.getName().contains(".java")) {
                        javaFiles.add(file);
                    }
                } else if (file.isDirectory()) {
                    File directory = new File(file.getAbsolutePath());
                    javaFiles.addAll(listJavaFiles(directory));
                }
            }
        }
        return javaFiles;
    }

    /**
     * 获取git仓库数据文件列表
     * @param pDirectory 目标目录
     * @return git仓库数据文件列表
     */
    public static ArrayList<File> listRepositoryDataFiles(File pDirectory) {
        ArrayList<File> gitRepoDataFiles = new ArrayList();
        File[] fList = pDirectory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.getName().contains(".data")) {
                        gitRepoDataFiles.add(file);
                    }
                } else if (file.isDirectory()) {
                    File directory = new File(file.getAbsolutePath());
                    gitRepoDataFiles.addAll(listRepositoryDataFiles(directory));
                }
            }
        }
        return gitRepoDataFiles;
    }

    /**
     * 获取git仓库问题文件列表
     * @param pDirectory 目标目录
     * @return 仓库问题文件列表
     */
    public static ArrayList<File> listIssueFiles(File pDirectory) {
        ArrayList<File> gitRepoDataFiles = new ArrayList();
        File[] fList = pDirectory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.getName().contains("_issues")) {
                        gitRepoDataFiles.add(file);
                    }
                } else if (file.isDirectory()) {
                    File directory = new File(file.getAbsolutePath());
                    gitRepoDataFiles.addAll(listIssueFiles(directory));
                }
            }
        }
        return gitRepoDataFiles;
    }

    /**
     * 复制文件
     * @param sourceFile 源文件
     * @param destFile 目标文件
     * @throws IOException 读取写入异常
     */
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (sourceFile.exists()) {
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            FileChannel source = (new FileInputStream(sourceFile)).getChannel();
            FileChannel destination = (new FileOutputStream(destFile)).getChannel();
            if (source != null) {
                destination.transferFrom(source, 0L, source.size());
                source.close();
                destination.close();
            }
        }
    }
}