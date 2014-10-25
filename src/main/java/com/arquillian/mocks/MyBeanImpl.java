package com.arquillian.mocks;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MyBeanImpl implements MyBean {

    private boolean alive = false;

    @PostConstruct
    public void init(){
       alive = true;
    }


    @Override
    public boolean someSlowOperation() {
        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }
}