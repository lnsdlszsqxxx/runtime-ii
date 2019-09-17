package com.ascending.training.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private Logger logger;

    @Autowired
    private AmazonSQS amazonSQS;

    public String createQueue(String queueName){
        String queueUrl = null;

        try {
            queueUrl = getQueueUrl(queueName);
        }
        catch (QueueDoesNotExistException e){
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
            queueUrl = amazonSQS.createQueue(createQueueRequest).getQueueUrl();
        }
        return queueUrl;
    }


    public String getQueueUrl(String queueName){
        return amazonSQS.getQueueUrl(queueName).getQueueUrl();
    }


    public String createFIFOQueue(String queueName){
        if(!queueName.matches(".*\\.fifo$")) {
            logger.error("The FIFO queue name must end with the .fifo suffix.");
            logger.error("Use another name.");
            return null;
        }


        System.out.println("Creat a new Amazon SQS FIFO queue called " +
                queueName);
        final Map<String, String> attributes = new HashMap<>();

        // A FIFO queue must have the FifoQueue attribute set to true.
        attributes.put("FifoQueue", "true");

        /*
         * If the user doesn't provide a MessageDeduplicationId, generate a
         * MessageDeduplicationId based on the content.
         */
        attributes.put("ContentBasedDeduplication", "true");

        // The FIFO queue name must end with the .fifo suffix.
        final CreateQueueRequest createQueueRequest =
                new CreateQueueRequest(queueName)
                        .withAttributes(attributes);
        final String myQueueUrl = amazonSQS.createQueue(createQueueRequest).getQueueUrl();
        return myQueueUrl;
    }


    public void listingAllQueues() {
        System.out.println("Listing all queues in your account.\n");
        for (final String queueUrl : amazonSQS.listQueues().getQueueUrls()) {
            System.out.println("  QueueUrl: " + queueUrl);
        }
        System.out.println();
    }

    //use this for standard queue
    public void sendMessage(String queueName, String msg) {
        System.out.println("Sending a message to a standard queue named as "+queueName);
        Map<String, MessageAttributeValue> messageAttributes = new HashMap();
        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
        messageAttributeValue.withDataType("String").withStringValue("The file URL in S3");
        messageAttributes.put("message1", messageAttributeValue);
        String queueUrl = getQueueUrl(queueName);
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.withQueueUrl(queueUrl)
                            .withMessageBody(msg)
                            .withMessageAttributes(messageAttributes);
        amazonSQS.sendMessage(sendMessageRequest);
    }

    //use this for FIFO queue
    public void sendMessage(String queueName, String msg, String messageGroupId){
        System.out.println("Sending a message to a FIFO queue named as "+queueName);
        final SendMessageRequest sendMessageRequest = new SendMessageRequest(getQueueUrl(queueName),msg);

        /*
         * When you send messages to a FIFO queue, you must provide a
         * non-empty MessageGroupId.
         */
        sendMessageRequest.setMessageGroupId(messageGroupId);

        // Uncomment the following to provide the MessageDeduplicationId
//        sendMessageRequest.setMessageDeduplicationId("1");

        final SendMessageResult sendMessageResult = amazonSQS.sendMessage(sendMessageRequest);
        final String sequenceNumber = sendMessageResult.getSequenceNumber();
        final String messageId = sendMessageResult.getMessageId();
        System.out.println("SendMessage "+msg+" succeed with messageId "
                + messageId + ", sequence number " + sequenceNumber + "\n");
    }

    public List<Message> receiveMessages(String queueName) {
        String queueUrl = getQueueUrl(queueName);
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
        receiveMessageRequest.setMaxNumberOfMessages(1);
        receiveMessageRequest.setWaitTimeSeconds(20);

        // Uncomment the following to provide the ReceiveRequestDeduplicationId
        //receiveMessageRequest.setReceiveRequestAttemptId("1");
        List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();
        return messages;
    }

    public void deleteMessage(String queueName, Message message){
        System.out.println("Deleting the message from queue "+queueName);
        final String messageReceiptHandle = message.getReceiptHandle();
        amazonSQS.deleteMessage(new DeleteMessageRequest(getQueueUrl(queueName),
                messageReceiptHandle));
    }

    public void deleteQueue(String queueName){
        System.out.println("Deleting the queue.\n");
        amazonSQS.deleteQueue(new DeleteQueueRequest(getQueueUrl(queueName)));
    }

    public void sendEmail(String fromEmail, String toEmail, String subject, String contentString){
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", contentString);
        Mail mail = new Mail(from,subject,to,content);

        SendGrid sendGrid = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);
            logger.info("Status code is "+response.getStatusCode());
            logger.info("Body is "+response.getBody());
            logger.info("Header is "+response.getHeaders());
        }catch (IOException e){
            logger.error(e.getMessage());
        }
    }
}
