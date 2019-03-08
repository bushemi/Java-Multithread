package com.da.lect2.wait.notify;

public class SharedObject {
    private volatile String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
