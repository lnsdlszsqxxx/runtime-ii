package com.ascending.training.service;

import com.amazonaws.services.amplify.model.App;
import com.amazonaws.util.IOUtils;
import com.ascending.training.init.AppInitializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class FileServiceTest {

    @Autowired
    Logger logger;

    @Autowired
    FileService fileService;

    private String bucketName ="mybucketxxxxasfdwef";
    private String fileDir ="/Users/liangyu/Pictures/";

    @Before
    public void settup(){
        logger.info("test begin-------------------------------");
    }

    @After
    public void teardown(){
        logger.info("test finished----------------------------");
    }

    @Test
    public void createBucketTest(){
        fileService.createBucket(bucketName);
    }

    @Test
    public void deleteBucketTest(){
        fileService.deleteBucket(bucketName);
    }

    @Test
    public void listBucketTest(){
        fileService.listBucket();
    }

    @Test
    public void listObjectsTest(){
        fileService.listObjects(bucketName);
    }

    @Test
    public void deleteObjects(){
        String[] objects = {"Abc.class","test.f90"};
        fileService.deleteObjects(bucketName,objects);
    }

    @Test
    public void deleteAllObjectsTest(){
        fileService.deleteAllObjects(bucketName);
    }

    @Test
    public void myUploadFileTest(){
        fileService.myUploadFile(bucketName,fileDir,"DSC_4971.JPG");
    }

    @Test
    public void myUploadMultipartFileTest() {
        try {
            //convert file to multipart file
        File file = new File(fileDir + "LJgmu.jpeg");
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));
        fileService.myUploadMultipartFile(bucketName, multipartFile);
        }
        catch (FileNotFoundException e){
            logger.error(e.getMessage());
        }
        catch (IOException e){
            logger.error(e.getMessage());
        }
    }

    @Test
    public void readObjectTest() throws IOException {
        fileService.readObject(bucketName,"test.f90");
    }

    @Test
    public void downloadObjectTest(){
        fileService.downloadObject(bucketName,"LJgmu.jpeg");
    }

}
