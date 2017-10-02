package com.example.uberv.threads.filesearch;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Result result=new Result();
        ParallelFileSearch.searchFiles(new File("D:\\"),"Executing Tasks in an Executor that Returns a Result-30277.mp4",result);
        int a=4;
    }
}
