package com.clawhub.config;

import com.clawhub.spring.SpringContextHelper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <Description> 配置<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/4 <br>
 */
@Component
public class ConfigApp {
    /**
     * Spring helper spring context helper.
     *
     * @return the spring context helper
     */
    @Bean
    public SpringContextHelper springHelper() {
        return new SpringContextHelper();
    }
}
