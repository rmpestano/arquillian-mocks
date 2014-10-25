package com.arquillian.mocks;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MyBeanImpl2 {

    private boolean alive = false;

    @PostConstruct
    public void init() {
        alive = true;
    }


    public boolean someSlowOperation() {
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isAlive() {
        return alive;
    }
}