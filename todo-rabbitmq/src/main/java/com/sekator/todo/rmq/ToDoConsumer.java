package com.sekator.todo.rmq;


import com.sekator.todo.domain.ToDo;
import com.sekator.todo.repository.ToDoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ToDoConsumer {

    private final ToDoRepository repository;

    public ToDoConsumer(ToDoRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = "${todo.amqp.queue}")
    public void processToDo(ToDo todo) {
        log.info("Consumer >>>> " + todo);
        log.info("ToDo created >>>> " + this.repository.save(todo));
        log.info("Вероятно тут че то делается уже с тодо из очереди %)");
        log.info("####################################################");
    }
}
