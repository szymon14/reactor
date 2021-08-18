package org.example;

import org.reactivestreams.Publisher;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import static java.time.Duration.ofSeconds;

public class App {
    public static void main(String[] args){
       /* ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while(true) {
                fluxSink.next(System.currentTimeMillis());
            }
        })
                .sample(ofSeconds(2))
                .log()
                .publish();

        publish.subscribe(System.out::println);
        publish.subscribeOn(Schedulers.parallel());
        publish.connect();
        */
        //jedno z nich trzeba zakomentowac
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = "testowy";
            System.out.println("Hello " + threadName);
        });
        Function<String, Publisher<String>> mapper = s ->  Flux.just(s =" przedmiot " + s + " pochodzący z wątku " + Thread.currentThread().getName());
        Flux.just("1", "2", "3", "4")
                .log()
                .flatMap(mapper)
                .subscribeOn(Schedulers.fromExecutor(executor))
                .subscribe(System.out::println);
        executor.shutdown();
    }
}
