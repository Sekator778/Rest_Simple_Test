package com.apress.reactor.example;

import com.apress.reactor.example.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 *
 */
@Configuration
public class MonoExample {
    static private Logger LOG = LoggerFactory.getLogger(MonoExample.class);
    @Bean
    public CommandLineRunner runMonoExample() {
        return args -> {

            MonoProcessor<ToDo> promise = MonoProcessor.create();
            Mono<ToDo> result = promise
                    .doOnSuccess(p -> LOG.info("MONO >> ToDo: {}", p.getDescription()))
                    .doOnTerminate(() -> LOG.info("MONO >> DONE"))
                    .doOnError(t -> LOG.error(t.getMessage(), t))
                    .subscribeOn(Schedulers.single());

            promise.onNext(new ToDo("Buy my ticket for Spring Reactive 2020"));

            result.block(Duration.ofMillis(10000));
        };
    }
}
