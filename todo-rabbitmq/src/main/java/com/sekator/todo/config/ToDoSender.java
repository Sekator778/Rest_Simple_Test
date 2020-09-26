package com.sekator.todo.config;

import com.sekator.todo.domain.ToDo;
import com.sekator.todo.rmq.ToDoProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@Configuration
@Slf4j
public class ToDoSender {


//    @Bean
//    public CommandLineRunner sendToDos(@Value("${todo.amqp.queue}") String destination, ToDoProducer producer) {
//        log.info("Let's go . . . . .");
//        return args -> {
//            producer.sendTo(destination, new ToDo("workout tomorrow morning!"));
//        };
//    }


    //More throughput?
    private final ToDoProducer producer;

    public ToDoSender(ToDoProducer producer) {
        this.producer = producer;
    }

    @Value("${todo.amqp.queue}")
    private String destination;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000L)
    private void sendToDos() {
        String format = dateFormat.format(new Date());
        log.info("Work >>>> time now: " + format);
        producer.sendTo(destination, new ToDo("Thinking on Spring Boot at "));
    }

}
