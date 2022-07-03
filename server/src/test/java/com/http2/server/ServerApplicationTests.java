package com.http2.server;

import io.netty.handler.logging.LogLevel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.Http2AllocationStrategy;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServerApplicationTests {

    private WebClient client;

    @BeforeAll
    public void setClient() {
        HttpClient httpClient = HttpClient.create(ConnectionProvider.builder("test")
                                                                    .maxConnections(1)
                                                                    .allocationStrategy(Http2AllocationStrategy.builder().maxConnections(1).build())
                                                                    .build())
                                          .protocol(HttpProtocol.H2C, HttpProtocol.HTTP11)
                                          .compress(true)
                                          .wiretap("reactor.netty.http.client.HttpClient",
                                                  LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        this.client = WebClient.builder()
                               .baseUrl("http://localhost:8080/")
                               .clientConnector(new ReactorClientHttpConnector(httpClient))
                               .build();
    }

    @Test
    void contextLoads() {
		this.client
				.post()
				.uri("/products")
				.bodyValue(new Request("request"))
				.retrieve()
				.bodyToMono(Response.class)
				.doOnNext(System.out::println)
				.as(StepVerifier::create)
				.expectNextCount(1)
				.verifyComplete();
    }

}
