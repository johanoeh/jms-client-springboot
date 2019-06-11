package com.sundsvall.midalva.jms;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableSchedulerLock(defaultLockAtMostFor = "PT30M")
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(){
//
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                LOG.info("Starting scheduler .....");
//                scheduler.startPopulateDB();
//
//            }
//        };
//    }


}
