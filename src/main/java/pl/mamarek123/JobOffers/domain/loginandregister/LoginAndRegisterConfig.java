package pl.mamarek123.JobOffers.domain.loginandregister;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginAndRegisterConfig {

    @Bean
    LoginAndRegisterFacade loginAndRegisterFacade(LoginAndRegisterRepostiory loginAndRegisterRepostiory){
        return new LoginAndRegisterFacade(loginAndRegisterRepostiory);
    }
}
