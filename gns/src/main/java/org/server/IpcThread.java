package org.server;

public class IpcThread extends Gthread {

    @Override
    protected boolean on_thread_loop(){
        System.out.println("start Ipc Thread..!");
        Thread.sleep(1);
        return true;
    }
}
