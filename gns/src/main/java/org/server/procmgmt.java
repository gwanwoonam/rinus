package org.server;

public class procmgmt {
    // 정적 참조 변수(싱글톤 객체를 담을 변수)
    private static procmgmt obj;
    private volatile int live_count = 0;
    private volatile boolean is_running = true;

    // private 생성자
    private procmgmt() {
    }

    // getInstance()
    public static procmgmt getInstance() {
        if (obj == null) {
            obj = new procmgmt();
        }
        return obj;
    }

    void set_status(boolean status) {
        is_running = status;
    }

    boolean is_running() {
        return is_running;
    }

    void add_count() {
        live_count++;
    }

    void remove_count(){
        live_count--;
    }

    int get_count(){
        return live_count;
    }

}
