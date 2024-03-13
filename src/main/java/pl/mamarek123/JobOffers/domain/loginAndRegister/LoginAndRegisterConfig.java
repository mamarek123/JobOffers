package pl.mamarek123.JobOffers.domain.loginAndRegister;

import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginAndRegisterConfig {

    @Bean
    LoginAndRegisterFacade loginAndRegisterFacade(LoginAndRegisterRepostiory loginAndRegisterRepostiory){
        return new LoginAndRegisterFacade(loginAndRegisterRepostiory);
    }
}
