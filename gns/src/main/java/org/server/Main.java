package org.server;

import java.util.ArrayList;



public class Main {

    static String PROCESS_NAME = "Converter Ver1.0";
    static ArrayList<gthread> g_thread_list = new ArrayList<gthread>();

    public static void main(String[] args) {

        System.out.println(PROCESS_NAME +" Process Started.");

        procmgmt pmt = procmgmt.getInstance();

        // IpcThread orthopaedic = new IpcThread("IPC_thread");
        // g_thread_list.add(orthopaedic);
        convertfilethread cov = new convertfilethread("convertfilethread");
        g_thread_list.add(cov);

        for (gthread th : g_thread_list) {
            th.run();
        }

        while(0 < pmt.get_count()){
            System.out.println("thread count (" + pmt.get_count() + ")");
        }

        System.out.println(PROCESS_NAME + " Process Stopped.");
    }
}
