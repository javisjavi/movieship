package com.onemore.msinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class MsInfoApplication {

	@Autowired
    private IColasService colasService;

    public static void main(String[] args) {
        SpringApplication.run(MsInfoApplication.class, args);
    }

    @PostConstruct
    public void init() {
        colasService.conectarColaInfo();
    }

}
