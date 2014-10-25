package com.arquillian.mocks;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MyBean2 {


    public boolean someSlowOperation() {
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}