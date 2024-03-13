package pl.mamarek123.JobOffers.domain.loginAndRegister;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserRequestDto;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDto;

import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterFacadeTest {
    LoginAndRegisterTestInMemoryRepository registerRepository = new LoginAndRegisterTestInMemoryRepository();
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(registerRepository);
    @Test
    public void should_properly_register(){
        //given
        User user = new User("testid","bartek","haslo","bartek@email.pl");
        UserResultDto expectedUserResultDto = UserMapper.userToUserResultDto(user);
        //when
        UserRequestDto userRequestDto = new UserRequestDto(user.username(), user.email(), user.password());
        UserResultDto userRegisterResultDTO = loginAndRegisterFacade.register(userRequestDto);
        //then
        assertEquals(userRegisterResultDTO,expectedUserResultDto);
    }

    @Test
    public void should_properly_find_registered_user_by_username(){
        //given
        String username  = "bartek";
        User user = new User("1",username,"haslo","bartek@email.pl");
        registerRepository.save(user);
        UserResultDto expectedUserResultDto = UserMapper.userToUserResultDto(user);
        //when
        UserResultDto byUsername = loginAndRegisterFacade.findByUsername(username);
        //then
        assertEquals(expectedUserResultDto,byUsername);
    }

    @Test
    public void should_throw_BadCredentialsException_when_searching_with_not_existing_username(){
        String username  = "bartek";
        User user = new User("1",username,"haslo","bartek@email.pl");
        registerRepository.save(user);
        UserResultDto emptyUser = UserMapper.userToUserResultDto(User.builder().build());
        //when && then
        assertThrows(BadCredentialsException.class, () -> loginAndRegisterFacade.findByUsername("kamil"));
    }

    @Test
    public void should_return_empty_result_with_status_failure_and_message_user_with_given_email_already_exists_when_registering_with_taken_email_whether_username_taken_or_not(){
        //given
        User user1 = new User("1","bartek","haslo","bartek@email.com");
        registerRepository.save(user1);
        UserRequestDto userWithSameEmailSameUsername = new UserRequestDto("bartek","bartek@email.com","password");
        UserRequestDto userWithSameEmailNewUsername = new UserRequestDto("bartek2","bartek@email.com","password");
        UserResultDto expectedResult = UserMapper.userToUserResultDto(User.builder().build());
        //when
        UserResultDto userResultDTO1 = loginAndRegisterFacade.register(userWithSameEmailNewUsername);
        UserResultDto userResultDTO2 = loginAndRegisterFacade.register(userWithSameEmailSameUsername);
        //then
        assertEquals(expectedResult,userResultDTO1);
        assertEquals(expectedResult,userResultDTO2);
    }

    @Test
    public void should_return_empty_result_with_status_failure_and_message_user_with_given_username_already_exists_when_registering_with_taken_username_and_not_taken_email(){
        //given
        User user1 = new User("1","bartek","haslo","bartek@email.pl");
        registerRepository.save(user1);
        UserRequestDto userWithNewEmailSameUsername = new UserRequestDto("bartek","bartek2@email.com","password");
        UserResultDto expectedResult = UserMapper.userToUserResultDto(User.builder().build());
        //when
        UserResultDto registerResultDTO = loginAndRegisterFacade.register(userWithNewEmailSameUsername);
        //then
        assertEquals(expectedResult,registerResultDTO);
    }
}