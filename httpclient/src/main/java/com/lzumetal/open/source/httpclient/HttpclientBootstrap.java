package com.lzumetal.open.source.httpclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author liaosi
 * @date 2020-08-08
 */
@SpringBootApplication
public class HttpclientBootstrap {


    public static void main(String[] args) {
        new SpringApplicationBuilder(HttpclientBootstrap.class)
                .registerShutdownHook(true)
                .build(args)
                .run();
    }

}
