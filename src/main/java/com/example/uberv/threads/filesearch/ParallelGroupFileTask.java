package com.example.uberv.threads.filesearch;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelGroupFileTask implements Runnable {

    private final String fileName;
    private final ConcurrentLinkedQueue<File> directories;
    private final Result parallelResult;
    private boolean found;

    public ParallelGroupFileTask(String fileName, Result parallelResult,
                                 ConcurrentLinkedQueue<File> directories) {
        this.fileName = fileName;
        this.parallelResult = parallelResult;
        this.directories = directories;
        this.found = false;
    }

    @Override
    public void run() {
        while (directories.size() > 0) {
            File file = directories.poll();
            try {
                processDirectory(file, fileName, parallelResult);
                if (found) {
                    System.out.printf("%s has found the file%n",
                            Thread.currentThread().getName());
                    System.out.printf("Parallel Search: Path: %s%n",
                            parallelResult.path);
                    parallelResult.isFound = true;
                    return;
                }
            } catch (InterruptedException e) {
                System.out.printf("%s has been interrupted%n",
                        Thread.currentThread().getName());
            }
        }
    }

    private void processDirectory(File file, String fileName, Result parallelResult) throws InterruptedException {
        System.out.println("[Thread " + Thread.currentThread().getName() + "] " + "Processing directory " + file.toString());
        File[] contents;
        contents = file.listFiles();

        if ((contents == null) || (contents.length == 0)) {
            return;
        }

        for (File content : contents) {
            if (content.isDirectory()) {
                processDirectory(content, fileName, parallelResult);
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                if (found) {
                    return;
                }
            } else {
                processFile(content, fileName, parallelResult);
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                if (found) {
                    return;
                }
            }
        }
    }

    private void processFile(File content, String fileName,
                             Result parallelResult) {
        System.out.println("[Thread " + Thread.currentThread().getName() + "] " + "Processing file " + content.toString());
        if (content.getName().equals(fileName)) {
            parallelResult.path = (content.getAbsolutePath());
            this.found = true;
        }
    }

    public boolean getFound() {
        return found;
    }
}
