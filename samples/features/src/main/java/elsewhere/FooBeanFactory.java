package elsewhere;

import com.example.commandlinerunner.FooBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooBeanFactory {
    @Bean
    public FooBean fooBeanFactory() {
        return new FooBean(); 
    }
}