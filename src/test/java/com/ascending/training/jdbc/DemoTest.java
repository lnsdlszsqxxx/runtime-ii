package com.ascending.training.jdbc;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoTest {
    private static Logger logger = LoggerFactory.getLogger(DemoTest.class);


    @BeforeClass
    public static void initAllTest(){
        logger.info("BeforeClass: stating testing");
    }

    @AfterClass
    public static void endAllTest(){
        logger.info("AfterClass: test done");
    }

    @Before
    public void initTest(){
        logger.info("Before: start");

    }

    @After
    public void endTest(){
        logger.info("After: unit test is done!");
    }


    @Test
    public void test1() {

        String str1 = new String("ABC");
        String str2 = new String("ABC");
        String str5 = str1;
        String str3 = "ABC";
        String str4 = "ABC";
        Integer x=null;

        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>equals: "+str1.equals(str2));

        Assert.assertEquals(str3,str4);
        Assert.assertNull(x);



        logger.trace("########## Test1 - Trace: test1 is done. ##########");
        logger.debug("########## Test1 - Debug: test1 is done. ##########");
        logger.info("########## Test1 - Info: test1 is done. ##########");
        logger.warn("########## Test1 - Warn: test1 is done. ##########");
        logger.error("########## Test1 - Error: test1 is done. ##########");
    }

    @Test
    public void thisisnotatest(){
        logger.info("hello");
    }

    @Test
    public void test2() {
        logger.trace("########## Test2 - Trace: test1 is done. ##########");
        logger.debug("########## Test2 - Debug: test1 is done. ##########");
        logger.info("########## Test2 - Info: test1 is done. ##########");
        logger.warn("########## Test2 - Warn: test1 is done. ##########");
        logger.error("########## Test2 - Error: test1 is done. ##########");
    }


}
