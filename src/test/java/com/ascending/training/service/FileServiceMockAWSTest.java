package com.ascending.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.ascending.training.init.AppInitializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class FileServiceMockAWSTest {

    @Autowired @Spy
    Logger logger;

    @Mock
    AmazonS3 amazonS3;


    @InjectMocks
    @Autowired
    FileService fileService;

    private String bucketName = "aBucketName";

    @Before
    public void setup() throws MalformedURLException, FileNotFoundException, IOException {
        logger.info("-----------begin test--------------");

        //Mocks are initialized before each test method.
        MockitoAnnotations.initMocks(this);

    }

    @After
    public void teardown(){
        logger.info("----------test end------------");
    }

    @Test
    public void createBucketTest(){
        fileService.createBucket(bucketName);
    }

    @Test
    public void getURLTest(){

    }

    @Test
    public void saveFileTest(){

    }


}
