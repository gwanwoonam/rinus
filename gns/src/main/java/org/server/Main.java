package org.server;

import org.server.Gthread;
import org.server.IpcThread;

import java.util.ArrayList;



public class Main {
    static ArrayList<Gthread> g_thread_list = new ArrayList<Gthread>();

    public static void main(String[] args) {

        System.out.println("start Deamon..!");

        int thread_num = 0;

        IpcThread orthopaedic = new IpcThread();
        g_thread_list.add(orthopaedic);
        thread_num++;


        for(int idx = 0; idx <thread_num; idx++){
            Gthread th = g_thread_list.get(idx);
            th.run();
        }

        while(true){
            for (Gthread th : g_thread_list) {
                if ( th.get_is_running() == true){
                    continue;
                }
                System.out.println("All thread Stopped.");
                break;
            }
        }

        System.out.println("Process Stopped.");


    }
}
