package it.codeSmell.aDoctor.process;

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

            String var6 = sb.toString();
            return var6;
        } finally {
            if (isr != null) {
                isr.close();
            }

        }
    }

    public static String getClassFromSrcMLstring(String srcMLstring, String start, String end) {
        int countClass = 0;
        String toReturn = "";
        Pattern newLine = Pattern.compile("\n");
        String[] lines = newLine.split(srcMLstring);
        String[] var7 = lines;
        int var8 = lines.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            String line = var7[var9];
            if (line.contains(start)) {
                ++countClass;
                toReturn = toReturn + line;
            }

            if (line.contains(end)) {
                --countClass;
                toReturn = toReturn + line;
                if (countClass == 0) {
                    return toReturn;
                }
            } else {
                toReturn = toReturn + line;
            }
        }

        return null;
    }

    public void copyDirectory(File srcPath, File dstPath) throws IOException {
        if (srcPath.isDirectory()) {
            if (!dstPath.exists()) {
                dstPath.mkdir();
            }

            String[] files = srcPath.list();
            String[] var4 = files;
            int var5 = files.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                String file = var4[var6];
                this.copyDirectory(new File(srcPath, file), new File(dstPath, file));
            }
        } else if (!srcPath.exists()) {
            System.out.println("File or directory does not exist.");
            System.exit(0);
        } else {
            InputStream in = new FileInputStream(srcPath);
            Throwable var19 = null;

            FileOutputStream out;
            try {
                out = new FileOutputStream(dstPath);
                byte[] buf = new byte[1024];

                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Throwable var15) {
                var19 = var15;
                throw var15;
            } finally {
                if (in != null) {
                    if (var19 != null) {
                        try {
                            in.close();
                        } catch (Throwable var14) {
                            var19.addSuppressed(var14);
                        }
                    } else {
                        in.close();
                    }
                }

            }

            out.close();
        }

        System.out.println("Directory copied.");
    }

    public static boolean DelDir(File dir) {
        if (dir.isDirectory()) {
            String[] contenuto = dir.list();
            String[] var2 = contenuto;
            int var3 = contenuto.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String contenuto1 = var2[var4];
                boolean success = DelDir(new File(dir, contenuto1));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static void writeFile(String pContent, String pPath) {
        File file = new File(pPath);

        try {
            FileWriter fstream = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fstream);
            Throwable var5 = null;

            try {
                out.write(pContent);
            } catch (Throwable var15) {
                var5 = var15;
                throw var15;
            } finally {
                if (out != null) {
                    if (var5 != null) {
                        try {
                            out.close();
                        } catch (Throwable var14) {
                            var5.addSuppressed(var14);
                        }
                    } else {
                        out.close();
                    }
                }

            }
        } catch (IOException var17) {
            System.err.println("Error: " + var17.getMessage());
        }

    }

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

    public static ArrayList<File> listRepositoryDataFiles(File pDirectory) {
        ArrayList<File> gitRepoDataFiles = new ArrayList();
        File[] fList = pDirectory.listFiles();
        if (fList != null) {
            File[] var3 = fList;
            int var4 = fList.length;
            for (int var5 = 0; var5 < var4; ++var5) {
                File file = var3[var5];
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

    public static ArrayList<File> listIssueFiles(File pDirectory) {
        ArrayList<File> gitRepoDataFiles = new ArrayList();
        File[] fList = pDirectory.listFiles();
        if (fList != null) {
            File[] var3 = fList;
            int var4 = fList.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                File file = var3[var5];
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