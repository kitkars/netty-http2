package com.http2.server;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ProductController {

    @PostMapping("products")
    public Mono<String> products(@RequestBody Mono<Object> mono){
        return mono
                .then(Mono.fromSupplier(() -> "Success"));
    }

}
