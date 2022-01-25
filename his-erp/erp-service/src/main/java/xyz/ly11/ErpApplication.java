package xyz.ly11;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author by 29794
 * @date 2020/10/7 1:05
 */
@SpringBootApplication
@MapperScan(basePackages = {"xyz.ly11.mapper"})
@EnableDubbo
@Slf4j
public class ErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class,args);
        log.info("ERP子系统启动成功！");
    }

}