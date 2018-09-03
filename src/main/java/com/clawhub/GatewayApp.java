package com.clawhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <Description> 启动项<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/3 <br>
 */
@SpringBootApplication
public class GatewayApp {
    /**
     * Description: 启动springboot<br>
     *
     * @param args <br>
     * @author LiZhiming<br>
     * @taskId <br>
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext confApp = null;
        try {
            confApp = SpringApplication.run(GatewayApp.class, args);

        } finally {
            close(confApp);
        }

    }

    /**
     * Description: 优雅关闭服务 <br>
     *
     * @param confApp conf app
     * @author LiZhiming <br>
     * @taskId <br>
     */
    private static void close(ConfigurableApplicationContext confApp) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (confApp != null) {
                confApp.close();
            }
        }));
    }
}
