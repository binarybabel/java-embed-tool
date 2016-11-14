package org.binarybabel.embedtool;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String slug = args[0];
        String bc1 = args[1];
        String bc2 = args[2];

        String embedSourceClass = bc1;
        String embedTargetClass = bc2 + ".embed." + slug;

        File embedJavaBaseDir = new File("java");
        File embedTargetJavaDir = new File(embedJavaBaseDir.getPath() + "/" + embedTargetClass.replace(".", "/"));

        File embedSourceDir = new File("repo/" + slug);
        File embedSourceMainJavaDir = new File(embedSourceDir.getPath() + "/src/main/java/" + embedSourceClass.replace(".", "/"));
        File embedSourceEmbedJavaDir = new File(embedSourceDir.getPath() + "/src/embed/java/" + embedSourceClass.replace(".", "/"));

        if (!embedSourceMainJavaDir.exists()) {
            throw new RuntimeException("Source java directory doesn't exist.");
        }

        if (!embedJavaBaseDir.exists()) {
            throw new RuntimeException("Embed java directory doesn't exist.");
        }

        logInfo("Embedding from:");
        logInfo("  " + embedSourceClass);
        logInfo("Embedding to:");
        logInfo("  " + embedTargetClass);

        try {

            logInfo("Prepping workspace...");

            if (embedJavaBaseDir.exists()) {
                FileUtils.deleteDirectory(embedTargetJavaDir);
            }
            embedTargetJavaDir.mkdir();

            logInfo("Copying text files...");
            for (File f : embedSourceDir.listFiles()) {
                if (f.getName().matches(".*(\\.txt|\\.md)")) {
                    File d = new File(embedTargetJavaDir.getPath() + "/" + f.getName());
                    logFile(d);
                    FileUtils.copyFile(f, d, true);
                }
            }

            logInfo("Embedding java files...");
            embedSourceDir(embedSourceMainJavaDir, embedTargetJavaDir, embedSourceClass, embedTargetClass);
            if (embedSourceEmbedJavaDir.exists()) {
                embedSourceDir(embedSourceEmbedJavaDir, embedTargetJavaDir, embedSourceClass, embedTargetClass);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void embedSourceDir(File sourceDir, File targetDir, CharSequence fromNamespace, CharSequence toNamespace) {
        targetDir.mkdirs();

        for (File f : sourceDir.listFiles()) {
            if (f.getName().matches(".*(\\.java)")) {
                File d = new File(targetDir.getPath() + "/" + f.getName());
                logFile(d);
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(d));
                    try {
                        String line = br.readLine();
                        while (line != null) {
                            bw.write(line.replace(fromNamespace, toNamespace) + "\n");
                            line = br.readLine();
                        }
                    } finally {
                        br.close();
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (f.isDirectory()) {
                embedSourceDir(
                        new File(sourceDir.getPath() + "/" + f.getName()),
                        new File(targetDir.getPath() + "/" + f.getName()),
                        fromNamespace,
                        toNamespace
                );
            }
        }
    }

    private static void logInfo(String info) {
        System.out.println(info);
    }

    private static void logFile(File file) {
        System.out.println(" + " + file.getPath());
    }
}
