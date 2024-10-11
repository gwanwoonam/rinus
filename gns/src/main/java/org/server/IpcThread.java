package org.server;

public class ipcthread extends gthread {
    public ipcthread(String name){
        super(name);
    }

    @Override
    protected boolean on_thread_loop() {
        return true;
    }
}
