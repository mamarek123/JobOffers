package pl.mamarek123.JobOffers.domain.loginandregister;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import pl.mamarek123.JobOffers.domain.loginandregister.DTO.UserRequestDto;
import pl.mamarek123.JobOffers.domain.loginandregister.DTO.UserResultDto;
import pl.mamarek123.JobOffers.domain.loginandregister.error.UserAlreadyExistsException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterFacadeTest {
    LoginAndRegisterTestInMemoryRepository registerRepository = new LoginAndRegisterTestInMemoryRepository();
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(registerRepository);


    @Test
    public void should_properly_register(){
        //given
        UserRequestDto userRequest = new UserRequestDto("bartek","haslo","bartek@email.pl");
        //when
        UserResultDto userRegisterResult = loginAndRegisterFacade.register(userRequest);
        //then
        assertTrue(Objects.equals(userRequest.username(), userRegisterResult.username()) &&
                Objects.equals(userRequest.password(), userRegisterResult.password()) &&
                Objects.equals(userRequest.email(), userRegisterResult.email())
        ,"expecting all fields to match");
    }

    @Test
    public void should_properly_find_registered_user_by_username(){
        //given
        String username  = "bartek";
        UserRequestDto userRequest = new UserRequestDto(username,"haslo","bartek@email.pl");
        loginAndRegisterFacade.register(userRequest);
        //when
        UserResultDto byUsername = loginAndRegisterFacade.findByUsername(username);
        //then
        assertTrue(Objects.equals(userRequest.username(), byUsername.username()) &&
                Objects.equals(userRequest.password(), byUsername.password()) &&
                Objects.equals(userRequest.email(), byUsername.email()),
                "Expecting all fields to match");
    }

    @Test
    public void should_throw_BadCredentialsException_when_searching_with_not_existing_username(){
        //given
        UserRequestDto userRequest = new UserRequestDto("bartek","haslo","bartek@email.pl");
        loginAndRegisterFacade.register(userRequest);
        //when && then
        assertThrows(BadCredentialsException.class, () -> loginAndRegisterFacade.findByUsername("jacek"), "expecting BadCredentialsException");
    }

    @Test
    public void should_trow_UserAlreadyExistsException_if_email_or_username_already_exists(){
        //given
        UserRequestDto userRequest = new UserRequestDto("bartek","haslo","bartek@email.pl");
        loginAndRegisterFacade.register(userRequest);
        UserRequestDto takenUsername = new UserRequestDto("bartek", "haslo", "bartek@email.pl");
        UserRequestDto takenEmail = new UserRequestDto("bartek2", "haslo", "bartek@email.pl");

        //when & then
        assertThrows(UserAlreadyExistsException.class, () -> loginAndRegisterFacade.register(takenUsername), "expecting UserAlreadyExistsException, username already taken");
        assertThrows(UserAlreadyExistsException.class, () -> loginAndRegisterFacade.register(takenEmail), "expecting BadCredentialsException");


    }


}