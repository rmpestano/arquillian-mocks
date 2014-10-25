package com.arquillian.mocks;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
public class MyBeanTest {

    @Inject
    SimpleBean myAlternativebean;

    @Inject
    MyBean2 mySpecializedBean;

    @Inject
    MyBean realBean;

    MyBean realMockedBean;

    @Before
    public void setupMock() {
        realMockedBean = Mockito.mock(MyBean.class);//to use @Mock see auto discover extension: https://github.com/arquillian/arquillian-showcase/blob/master/extensions/autodiscover
        Mockito.when(realMockedBean.someSlowOperation()).thenReturn(true);
    }


    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, MyBean.class.getPackage())
                .deleteClass(MyBeanTest.class)
               // .deleteClass(MyBeanSpecialization.class) uncomment to see second test fail
                .addAsWebInfResource("test-beans.xml", "beans.xml");
        MavenResolverSystem resolver = Maven.resolver();
        war.addAsLibraries(resolver.loadPomFromFile("pom.xml").resolve("org.mockito:mockito-all:1.10.8").withTransitivity().asSingleFile());
        System.out.println(war.toString(true));
        return war;

    }

    @Test
    @InSequence(1)
    public void shouldRunFastWithAlternativeBean() {
        long start = System.currentTimeMillis();
        assertTrue(myAlternativebean.someSlowOperation());
        assertTrue((System.currentTimeMillis() - start) < 5000);
    }

    @Test
    @InSequence(2)
    public void shouldRunFastWithSpecializedBean() {
        long start = System.currentTimeMillis();
        assertTrue(mySpecializedBean.someSlowOperation());
        assertTrue((System.currentTimeMillis() - start) < 5000);
    }

    @Test
    @InSequence(3)
    public void shouldRunFastWithMockedBean() {
        long start = System.currentTimeMillis();
        assertTrue(realMockedBean.someSlowOperation());
        assertTrue((System.currentTimeMillis() - start) < 5000);
    }

    @Test
    @InSequence(4)
    public void shouldRunSlowWithRealBean() {
        long start = System.currentTimeMillis();
        assertTrue(realBean.someSlowOperation());
        long time = (System.currentTimeMillis() - start);
        assertTrue( time > 9999);
    }


}
