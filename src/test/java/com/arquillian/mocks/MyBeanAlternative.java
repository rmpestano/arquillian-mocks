package com.arquillian.mocks;

import javax.enterprise.inject.Alternative;

/**
 * Created by rmpestano on 10/25/14.
 */
@Alternative
public class MyBeanAlternative implements MyBean {


    @Override
    public boolean someSlowOperation() {
         return true;
    }

    @Override
    public boolean isAlive() {
        return true;
    }
}
