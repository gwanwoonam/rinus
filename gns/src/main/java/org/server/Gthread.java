package org.server;

public class gthread implements Runnable{
    private boolean m_is_running = false;
    private int m_thread_id= 0;
    private String m_thread_name = "default_thread";

    public gthread(String name ){
        m_thread_name = name;
        System.out.println(m_thread_name + "Thread Created");
    }
    procmgmt procmanger = procmgmt.getInstance();

    public String get_thread_name() {
        return m_thread_name;
    }

    public void set_thread_name(String name){
        m_thread_name = name;
    }

    public void set_thread_id(int thid){
        m_thread_id = thid;
    }

    public int get_thread_id(){
        return m_thread_id;
    }

    public void set_is_running(boolean isrun){
        m_is_running = isrun;
    }

    public boolean get_is_running(){
        return m_is_running;
    }

    protected boolean on_thread_loop() {
        return true;
    }

    public void run(){
        System.out.println(m_thread_name + "Thread Started");
        procmanger.add_count();
        while( procmanger.is_running() ){
            if(!on_thread_loop()){
                break;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        procmanger.remove_count();
        System.out.println(m_thread_name + "Thread Stopped");
    }
}
