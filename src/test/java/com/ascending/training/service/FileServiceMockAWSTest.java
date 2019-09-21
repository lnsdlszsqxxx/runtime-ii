package com.ascending.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.ascending.training.init.AppInitializer;
import io.jsonwebtoken.lang.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.omg.CORBA.Any;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class FileServiceMockAWSTest {

//    @Autowired @Spy
//    Logger logger;

    //Spy and Mock difference:
    //https://blog.csdn.net/Aeroleo/article/details/49946999
    @Autowired @Spy
    Logger logger;

//    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    @Mock
    AmazonS3 amazonS3;



    @InjectMocks
    FileService fileService;

    private String bucketName = "aBucketName";
    private String fileName = "aFileName";
    private URL URLName;
    private MultipartFile multipartFile = new MockMultipartFile("aName",new byte[1]);

    @Before
    public void setup() throws MalformedURLException, FileNotFoundException, IOException {
        logger.info("-----------begin test--------------");

        //Mocks are initialized before each test method.
        MockitoAnnotations.initMocks(this);

        URLName = new URL("http://www.google.com");

        when(amazonS3.generatePresignedUrl(any())).thenReturn(URLName);
        when(amazonS3.doesObjectExist(anyString(), anyString())).thenReturn(false);
        
    }

    @After
    public void teardown(){
        logger.info("----------test end------------");
    }

    @Test
    public void createBucketTest(){
        fileService.createBucket(bucketName);
        verify(amazonS3, times(1)).createBucket(anyString());
    }

    @Test
    public void getURLTest(){
        String fileURL = fileService.getFileUrl(bucketName,fileName);

        Assertions.assertEquals(fileURL,URLName.toString());
        verify(amazonS3,times(1)).generatePresignedUrl(any());
    }

    @Test
    public void uploadFileTest() throws IOException{
        String result = fileService.uploadFile(bucketName,multipartFile);
        Assertions.assertEquals(result,URLName.toString());
        logger.info(result);
        System.out.println(result);

        verify(amazonS3,times(1)).putObject(any(),any(),any(),any());
    }

}
