package com.sundsvall.midalva.jms.model.message;

import com.sundsvall.midalva.jms.model.Message;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    @Autowired
    ConfigurableApplicationContext context;

    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
    private String START = "start";
    private String STOP = "stop";


    @Value("${spring.application.name}")
    private String instanceName;

    @JmsListener(destination = "message.queue", containerFactory = "queueListenerFactory", id = "message.queue")
    public void receiveMessage(Message message){
        LOG.info("instance {} received message {}",instanceName, message);
    }

    @JmsListener( destination = "status.topic", containerFactory = "topicListenerFactory")
    public void reciveStatusMessage(String message){
        LOG.info("receieved status message: {}",message);
        if(message.equals(START)) {
            handleStart();
        } else if(message.equals(STOP)) {
            handleStop();
        }
    }


    /**
     * Handles start listening by registering listener
     */
    private void handleStart(){

        JmsListenerEndpointRegistry jmsListenerEndpointRegistry = context.getBean(JmsListenerEndpointRegistry.class);
        MessageListenerContainer queueListenerFactory = jmsListenerEndpointRegistry.getListenerContainer("message.queue");
        queueListenerFactory.start();

    }

    /**
     * Handles stop listening by unregister listener
     */
    private void handleStop(){
        JmsListenerEndpointRegistry jmsListenerEndpointRegistry = context.getBean(JmsListenerEndpointRegistry.class);
        MessageListenerContainer queueListenerFactory = jmsListenerEndpointRegistry.getListenerContainer("message.queue");
        queueListenerFactory.stop();
    }
}
