package com.ascending.training.service;

import com.amazonaws.services.sqs.model.Message;
import com.ascending.training.init.AppInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class MessageServiceTest {

    @Autowired
    Logger logger;

    @Autowired
    MessageService messageService;

    private String queueName = "queue2.fifo";

    @Before
    public void setup(){

    }

    @Test
    public void createQueueTest(){
        messageService.createQueue(queueName);
    }

    @Test
    public void sendMessageTest(){
        String [] input = {"1","2","3","4","5","6"};
        for (int i=0;i<input.length;i++){
            messageService.sendMessage(queueName,input[i]);
        }
    }

    @Test
    public void getMessagesTest(){
        List<Message> messages = messageService.receiveMessages(queueName);
        System.out.println("message size is "+messages.size());
        for(Message message:messages) {
//            System.out.println(messages.get(i).getMessageId());
            System.out.println(message.getBody());
            messageService.deleteMessage(queueName,message);
        }
    }


    @Test
    public void createFIFOQueue(){
        messageService.createFIFOQueue("queue2.fifo");
    }

    @Test
    public void listingAllQueuesTest(){
        messageService.listingAllQueues();
    }

    @Test
    public void deleteQueueTest(){
        messageService.deleteQueue("queue.fifo");
    }

    @Test
    public void sendEmailTest(){
        String fromEmail = "lyueiroughfpqi4@gmu.edu";
        String toEmail = "lnsdlszsqxxx@163.com";
        String subject = "My first send grid email";
        String contentString = "this is fun!";
        messageService.sendEmail(fromEmail,toEmail, subject,contentString);
    }

}
