package com.http2.server;

import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.HttpProtocol;

@Configuration
public class ServerConfig {

    @Bean
    public NettyServerCustomizer customizer() {
        return httpServer -> httpServer.protocol(HttpProtocol.H2C, HttpProtocol.HTTP11)
                                       .httpRequestDecoder(s -> s.h2cMaxContentLength(10 * 1024 * 1024));
    }

}
