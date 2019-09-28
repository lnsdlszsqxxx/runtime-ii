package com.ascending.training.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private Logger logger;

    @Autowired private AmazonS3 amazonS3;

    public String mockitoDoReturnWhenTest(String t){
        logger.info("from mockitoDoReturnWhenTest");
        return t;
    }

    public void createBucket(String bucketName){
        if(amazonS3.doesBucketExistV2(bucketName)) {logger.info("bucketName already exists"); return;}

        logger.info("creating a bucket named as "+bucketName);
        amazonS3.createBucket(bucketName);
    }

    public void deleteBucket(String bucketName){
        if(!amazonS3.doesBucketExistV2(bucketName)) {logger.info("bucketName does not exists"); return;}
        if(!isBucketEmpty(bucketName)) {logger.info(String.format("bucket %s is not empty",bucketName)); return;}
        amazonS3.deleteBucket(bucketName);
    }

    public boolean isBucketEmpty(String bucketName){
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        if(objectListing.getObjectSummaries().size()==0) return true;
        else return false;
    }

    public void listBucket(){
        List<Bucket> buckets = amazonS3.listBuckets();
        System.out.println("Your buckets are:");
        for (Bucket bucket:buckets){
            System.out.println(bucket.getName());
        }
    }


    public void listObjects(String bucketName){
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
            logger.info(os.getKey());
        }
    }

    public void deleteObjects(String bucketName, String[] objectKeys){
        DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketName)
                .withKeys(objectKeys);
        amazonS3.deleteObjects(delObjReq);
    }

    public void deleteAllObjects(String bucketName){
        ObjectListing objectListing = amazonS3.listObjects(bucketName);
        for (S3ObjectSummary objectSummary:objectListing.getObjectSummaries()){
            amazonS3.deleteObject(bucketName,objectSummary.getKey());
        }
    }


    public String getFileUrl(String bucketName, String fileName){
        LocalDateTime expiration = LocalDateTime.now().plusDays(1);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,fileName);
        generatePresignedUrlRequest.withMethod(HttpMethod.GET);
        generatePresignedUrlRequest.withExpiration(Date.from(expiration.toInstant(ZoneOffset.UTC)));

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
    }

//    //put files on S3 from David Wang
    public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        try {
            if (amazonS3.doesObjectExist(bucketName, file.getOriginalFilename())) {
                logger.info(String.format("The file '%s' exists in the bucket %s", file.getOriginalFilename(), bucketName));
                return null;
            }
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), objectMetadata);
            logger.info(String.format("The file name=%s, size=%d was uploaded to bucket %s", file.getOriginalFilename(), file.getSize(), bucketName));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return getFileUrl(bucketName, file.getOriginalFilename());
    }

    public void myUploadFile(String bucketName,String fileDir, String fileName){
        if(amazonS3.doesObjectExist(bucketName,fileName)) {
            logger.info(String.format("File %s already exists",fileName));
            return;
        }
        amazonS3.putObject(bucketName,fileName,new File(fileDir+fileName));
        logger.info(String.format("The file name=%s was uploaded to bucket %s", fileName, bucketName));
    }

    public void myUploadMultipartFile(String bucketName, MultipartFile multipartFile){

        try {
            System.out.println(multipartFile.getOriginalFilename());
            System.out.println(multipartFile.getName());
            System.out.println(multipartFile.getResource());

            if (amazonS3.doesObjectExist(bucketName, multipartFile.getOriginalFilename())) {
                logger.info(String.format("File %s already exists", multipartFile.getOriginalFilename()));
                return;
            }
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());
            amazonS3.putObject(bucketName, multipartFile.getOriginalFilename(), multipartFile.getInputStream(),objectMetadata);
        }
        catch (IOException e){
            logger.error(e.getMessage());
            return;
        }

        return;

    }

    public void myUploadMultipartFiles(String bucketName, List<MultipartFile> multipartFiles){

            try {

                for(MultipartFile multipartFile:multipartFiles) {
                    System.out.println(multipartFile.getOriginalFilename());
                    System.out.println(multipartFile.getName());
                    System.out.println(multipartFile.getResource());

                    if (amazonS3.doesObjectExist(bucketName, multipartFile.getOriginalFilename())) {
                        logger.info(String.format("File %s already exists", multipartFile.getOriginalFilename()));
                        return;
                    }

                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentType(multipartFile.getContentType());
                    objectMetadata.setContentLength(multipartFile.getSize());
                    amazonS3.putObject(bucketName, multipartFile.getOriginalFilename(), multipartFile.getInputStream(), objectMetadata);
                }

            } catch (IOException e) {
                logger.error(e.getMessage());
                return;
            }

        return;

    }

    public void downloadObject(String bucketName, String objectKey){
        File localFile = new File("/Users/liangyu/Desktop/"+objectKey);
        ObjectMetadata object = amazonS3.getObject(new GetObjectRequest(bucketName, objectKey), localFile);
    }

    public void readObject(String bucketName, String objectKey)  throws IOException  {
        // Get an object and print its contents.
        System.out.println("Downloading an object");
        S3Object fullObject = amazonS3.getObject(new GetObjectRequest(bucketName, objectKey));
        System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
        System.out.println("Content: ");
        displayTextInputStream(fullObject.getObjectContent());
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }

    //it has nothing to do with S3, it saves file on local disk.
    public boolean saveFile(MultipartFile multipartFile, String filePath){
        boolean isSuccess = false;

        try {
            File directory = new File(filePath);
            if (!directory.exists()) directory.mkdir();
            Path filepath = Paths.get(filePath, multipartFile.getOriginalFilename());
            multipartFile.transferTo(filepath);
            isSuccess = true;
            logger.info(String.format("The file %s is saved in the folder %s", multipartFile.getOriginalFilename(), filePath));
        }

        catch (Exception e){
            logger.error(e.getMessage());
        }

        return isSuccess;
    }

}
