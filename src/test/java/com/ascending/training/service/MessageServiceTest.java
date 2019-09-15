package com.ascending.training.service;

import com.ascending.training.init.AppInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class MessageServiceTest {

    @Autowired
    Logger logger;

    @Autowired
    MessageService messageService;

    @Before
    public void setup(){

    }

    @Test
    public void createQueueTest(){
        messageService.createQueue("Liang");
    }
}
