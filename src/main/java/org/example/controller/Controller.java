package org.example.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequestMapping("/api")
public class Controller {
    private final Counter myCounter;
    private final Timer myTimer;
    private final AtomicInteger myGauge;
    private final DistributionSummary summary;

    public Controller(MeterRegistry registry) {
        myCounter = Counter.builder("my.counter")
                .description("Counts something")
                .tags("region", "us-east")
                .register(registry);

        myTimer = Timer.builder("my.timer")
                .description("Times something")
                .tags("region", "us-east")
                .register(registry);

        myGauge = new AtomicInteger();
        Gauge.builder("my.gauge", myGauge, AtomicInteger::get)
                .description("Gauges something")
                .tags("region", "us-east")
                .register(registry);

        summary = DistributionSummary.builder("custom.distribution.summary")
                .description("A custom distribution summary")
                .tags("region", "us-west")
                .register(registry);
    }


    @PostMapping("/hello/{text}")
    public String counter(@PathVariable("text") String text) {
        log.debug("Received a request for greeting with name: {}", text);
        myCounter.increment();

        return "Hello " + text;
    }

    @GetMapping("/hello/{text}")
    public String timer(@PathVariable("text") String text) {
        log.debug("Received a request for greeting with name: {}", text);
        myTimer.record(() -> {
            // perform task to be timed
        });

        return "Hello " + text;
    }

    @PutMapping("/hello/{text}")
    public String gauge(@PathVariable("text") int text) {
        log.debug("Received a request for greeting with name: {}", text);
        myGauge.set(text);

        return "Hello " + text;
    }

    @PatchMapping("/hello/{text}")
    public String record(@PathVariable("text") String text) {
        log.debug("Received a request for greeting with name: {}", text);
        myTimer.record(() -> {
            int i = new Random().nextInt();
        });

        return "Hello " + text;
    }

    @DeleteMapping("/hello/{text}")
    public void record(@PathVariable("text") int text) {
        summary.record(text);
    }
}