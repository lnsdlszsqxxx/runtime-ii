package com.ascending.training.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.ascending.training.init.AppInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class MessageServiceMockAWSTest {

    @Autowired
    @Spy
    Logger logger;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    AmazonSQS amazonSQS;

    @InjectMocks
    MessageService messageService;

    @Autowired @Spy
    FileService fileService;

    private String queueName = "fakeQueue.fifo";
    private String URLstring = "fakeURL.fifo";

    @Before
    public void setup() {

        logger.info("-------begin test--------");
        MockitoAnnotations.initMocks(this);

        //doReturn doesn't call a real method
        //thenReturn will call a real method.
        doReturn("hello").when(fileService).mockitoDoReturnWhenTest(anyString());
//        when(fileService.mockitoDoReturnWhenTest(anyString())).thenReturn("smile");

        when(amazonSQS.getQueueUrl(queueName).getQueueUrl()).thenThrow(new QueueDoesNotExistException("No queue"));
        when(amazonSQS.createQueue(any(CreateQueueRequest.class)).getQueueUrl()).thenReturn(URLstring);

    }

    @After
    public void teardown() {
        logger.info("-------end test--------");
    }

    @Test
    public void createQueueTest(){

        String result = messageService.createQueue(queueName);
        logger.info(result);
        Assert.assertEquals(result,URLstring);

        System.out.println(fileService.mockitoDoReturnWhenTest("asdjfoiew"));

        verify(amazonSQS,times(1)).createQueue(new CreateQueueRequest(queueName));
    }

}
