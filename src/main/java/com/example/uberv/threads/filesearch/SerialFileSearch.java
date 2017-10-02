package com.example.uberv.threads.filesearch;

import java.io.File;

public class SerialFileSearch {

    public static void searchFiles(File file, String fileName, Result result) {
        File[] contents;
        contents = file.listFiles();

        if (contents == null || contents.length == 0) {
            return;
        }

        for (File content : contents) {
            if (content.isDirectory()) {
                searchFiles(content, fileName, result);
            } else {
                if (content.getName().equals(fileName)) {
                    result.path = content.getAbsolutePath();
                    result.isFound = true;
                    System.out.println("File " + fileName + " has been found!");
                    return;
                }
            }
            if (result.isFound) {
                return;
            }
        }
    }
}
