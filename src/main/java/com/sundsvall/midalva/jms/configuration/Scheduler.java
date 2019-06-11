package com.sundsvall.midalva.jms.configuration;

import com.sundsvall.midalva.jms.model.Message;
import com.sundsvall.midalva.jms.model.message.MessageSender;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Configuration
public class Scheduler {



    @Value("${spring.application.name}")
    private String instanceName;

    private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

    @Autowired
    MessageSender messageSender;


    @Scheduled(fixedDelay = 100000)
//    @SchedulerLock(name="taskLock")
    public void startProcess() {

        for (int i = 0; i < 10000; i++) {

            Message message = new Message();
            message.setId((long) i);
            message.setMessage("Message no: " + i);
            message.setInstanceName(instanceName);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageSender.send(message);
        }
    }


    @Scheduled(fixedDelay = 1000000,initialDelay = 30000)
    public void stopProcess(){
        LOG.info("Sending stop process entries");
        messageSender.sendStopMessage();
    }


    @Scheduled(fixedDelay = 1000000,initialDelay = 40000)
    public void sendStart(){
        LOG.info("Sending stop process entries");
        messageSender.sendStartMessage();
    }

}
