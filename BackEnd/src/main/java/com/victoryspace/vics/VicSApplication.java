package com.victoryspace.vics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.victoryspace.vics.**.mapper")  // Mapper 패키지 경로
public class VicSApplication {
    public static void main(String[] args) {
        SpringApplication.run(VicSApplication.class, args);
    }
}
