package com.sekator.todo.rmq;

import com.sekator.todo.domain.ToDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ToDoProducer {

    private final RabbitTemplate template;

    public ToDoProducer(RabbitTemplate template) {
        this.template = template;
    }

    public void sendTo(String queue, ToDo toDo) {
        this.template.convertAndSend(queue, toDo);
        log.info("Producer >>>> Message Sent");
    }
}
