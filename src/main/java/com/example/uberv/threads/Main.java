package com.example.uberv.threads;

import com.example.uberv.threads.com.example.uberv.utils.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
The thread class save some information attributes that can help identify it:

ID: This attribute stores a unique identifier for each thread.

Name: This attribute stores the name of the thread.

Priority: This attribute stores the priority of the Thread objects. In Java 9, threads can
have priority between 1 and 10, where 1 is the lowest priority and 10 is the highest.
It's not recommended that you change the priority of the threads. It's only a hint to the
underlying operating system and it doesn't guarantee anything, but it's a possibility that
you can use if you want.

Status: This attribute stores the status of a thread. In Java, a thread can be present in one of the six states defined in the Thread.State enumeration: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, or TERMINATED. The following is a list specifying what each of these states means:
    NEW: The thread has been created and it has not yet started
    RUNNABLE: The thread is being executed in the JVM
    BLOCKED: The thread is blocked and it is waiting for a monitor
    WAITING: The thread is waiting for another thread
    TIMED_WAITING: The thread is waiting for another thread with a specified waiting time
    TERMINATED: The thread has finished its execution
 */

public class Main {

    public static void main(String[] args) {
        Logger.init(Logger.LogLevel.DEBUG);

        Logger.d("Minimum Priority: %s",
                Thread.MIN_PRIORITY);
        Logger.d("Normal Priority: %s",
                Thread.NORM_PRIORITY);
        Logger.d("Maximun Priority: %s",
                Thread.MAX_PRIORITY);

        Thread threads[] = new Thread[10];
        Thread.State status[] = new Thread.State[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Calculator());
            if ((i % 2) == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("My Calculator Thread " + i);
        }

        try (FileWriter file = new FileWriter("data/log.txt");
             PrintWriter pw = new PrintWriter(file)) {

            for (int i = 0; i < 10; i++) {
                pw.println("Main : Status of Thread " + i + " : " +
                        threads[i].getState());
                status[i] = threads[i].getState();
            }
            for (int i = 0; i < 10; i++) {
                threads[i].start();
            }

            boolean finish = false;
            // wait for execution to finish ( we could use Thread.join(), but then we wont be able to log)
            while (!finish) {
                for (int i = 0; i < 10; i++) {
                    if (threads[i].getState() != status[i]) {
                        writeThreadInfo(pw, threads[i], status[i]);
                        status[i] = threads[i].getState();
                    }
                }

                finish = true;
                for (int i = 0; i < 10; i++) {
                    // check whether thread execution has been finished
                    finish = finish && (threads[i].getState() == Thread.State.TERMINATED);
                }
            }
        } catch (IOException e) {
            Logger.e(e);
        }
    }

    private static void writeThreadInfo(PrintWriter pw, Thread thread, Thread.State state) {
        pw.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
        pw.printf("Main : Priority: %d\n", thread.getPriority());
        pw.printf("Main : Old State: %s\n", state);
        pw.printf("Main : New State: %s\n", thread.getState());
        pw.printf("Main : ************************************\n");
    }
}
