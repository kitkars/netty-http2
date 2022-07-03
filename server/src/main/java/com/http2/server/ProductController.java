package com.http2.server;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ProductController {

    @PostMapping("products")
    public Mono<Response> products(@RequestBody Mono<Request> mono){
        return mono
                .doOnNext(System.out::println)
                .then(Mono.fromSupplier(() -> new Response("success")));
    }

}
