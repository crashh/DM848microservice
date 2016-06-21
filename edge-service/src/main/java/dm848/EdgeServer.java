package dm848;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;


@SpringBootApplication
@Controller
@EnableZuulProxy
public class EdgeServer {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "edge-server");
        new SpringApplicationBuilder(EdgeServer.class).web(true).run(args);
    }

}