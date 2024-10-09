package org.server;

public class Gthread implements Runnable{
    private boolean m_is_running = false;
    private int m_thread_id= 0;
    private String m_thread_name = "default_thread";


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

    protected boolean on_thread_loop(){
        return true;
    }

    public void run(){
        while(m_is_running){
            if(!on_thread_loop()){
                System.out.println(m_thread_name + "Thread Stooped");
                return;
            }
            Thread.sleep(1000);
        }
    }


}
