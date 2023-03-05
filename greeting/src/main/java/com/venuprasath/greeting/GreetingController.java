package com.venuprasath.greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello %s!";
    private final AtomicLong id = new AtomicLong();


    private final static Random RANDOMIZER = new Random();

    private ArrayList<String> quotes = new ArrayList<>();

    GreetingController() {
        quotes.add("Quote 1");
        quotes.add("Quote 2");
        quotes.add("Quote 3");
        quotes.add("Quote 4");
        quotes.add("Quote 5");
        quotes.add("Quote 6");
        quotes.add("Quote 7");
        quotes.add("Quote 8");
        quotes.add("Quote 9");
        quotes.add("Quote 10");
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(id.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/api/{id}")
    public Quote quote(@PathVariable long id) {
        return new Quote("success", new Value(id, quotes.get((int)id)));
    }

    @GetMapping("/api/random")
    public Quote random() {
        return quote(nextLong(1, quotes.size() - 1));
    }

    private long nextLong(long lowerRange, long upperRange) {
        return (long) (RANDOMIZER.nextDouble() * (upperRange - lowerRange)) + lowerRange;
    }
}
