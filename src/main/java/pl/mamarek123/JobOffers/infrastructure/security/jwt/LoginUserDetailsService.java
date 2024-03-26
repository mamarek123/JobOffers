package pl.mamarek123.JobOffers.infrastructure.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.mamarek123.JobOffers.domain.loginandregister.DTO.UserResultDto;
import pl.mamarek123.JobOffers.domain.loginandregister.LoginAndRegisterFacade;

import java.util.Collections;

@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginAndRegisterFacade loginAndRegisterFacade;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResultDto userResultDto = loginAndRegisterFacade.findByUsername(username);
        return getUser(userResultDto);
    }

    private org.springframework.security.core.userdetails.User getUser(UserResultDto user) {
        return new org.springframework.security.core.userdetails.User(
                user.username(),
                user.password(),
                Collections.emptyList());
    }

}
