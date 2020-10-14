package xyz.ly11.config.shiro;

import xyz.ly11.constants.Constants;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author by 29794
 * @date 2020/10/9 20:10
 */
@Configuration
public class TokenWebSessionManager extends DefaultWebSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //从头里面得到请求TOKEN 如果不存在就生成一个
        String header = WebUtils.toHttp(request).getHeader(Constants.TOKEN);
        if(StringUtils.hasText(header)){
            return  header;
        }
        return  UUID.randomUUID().toString() ;
    }
}

