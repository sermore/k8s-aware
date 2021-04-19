package net.reliqs.k8saware;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableScheduling
public class K8sAwareApplication {

    @Value("${msg}")
    private String msg;

    public static void main(String[] args) {
        SpringApplication.run(K8sAwareApplication.class, args);
    }

    @GetMapping
    public String hello() {
        return "Hello World, please note that " + msg;
    }

}
