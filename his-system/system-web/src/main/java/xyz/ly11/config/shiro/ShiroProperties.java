package xyz.ly11.config.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * @author by 29794
 * @date 2020/10/9 20:38
 */
@ConfigurationProperties(prefix = "shiro")
@Data
public class ShiroProperties {
    /**
     * 密码加密方式
     */
    private String hashAlgorithmName="md5";
    /**
     * 密码散列次数
     */
    private Integer hashIterations=2;

    /**
     * 不拦击的路径
     */
    private String [] anonUrls;

    /**
     * 拦截的路径
     */
    private String [] authcUrls;
}

