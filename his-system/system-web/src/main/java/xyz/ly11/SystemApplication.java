package xyz.ly11;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author by 29794
 * @date 2020/10/6 17:10
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"xyz.ly11.mapper"})
@EnableDubbo
@EnableHystrix // 启用 Hystrix
@EnableCircuitBreaker  // 启用 Hystrix 的断路保护
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        log.info("主系统启动成功！");
    }

}