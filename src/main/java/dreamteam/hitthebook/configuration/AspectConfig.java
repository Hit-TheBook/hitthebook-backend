package dreamteam.hitthebook.configuration;

import dreamteam.hitthebook.common.aop.MyAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
    @Bean
    public MyAspect myAspect() {
        return new MyAspect();
    }
}
