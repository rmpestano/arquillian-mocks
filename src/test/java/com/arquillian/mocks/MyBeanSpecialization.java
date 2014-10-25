package com.arquillian.mocks;

import javax.enterprise.inject.Specializes;

/**
 * Created by rmpestano on 10/25/14.
 */
@Specializes
public class MyBeanSpecialization extends MyBeanImpl2 {

    @Override
    public boolean someSlowOperation()  {
        return true;
    }
}
