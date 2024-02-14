package pl.mamarek123.JobOffers.domain.loginAndRegister;

import org.junit.jupiter.api.Test;
import pl.mamarek123.JobOffers.domain.ResultStatus;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserRequestDTO;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDTO;

import static org.junit.jupiter.api.Assertions.*;

class LoginAndRegisterFacadeTest {
    LoginAndRegisterTestInMemoryRepository registerRepository = new LoginAndRegisterTestInMemoryRepository();
    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(registerRepository);
    @Test
    public void should_properly_register(){
        //given
        User user = new User(1L,"bartek","haslo","bartek@email.pl");
        UserResultDTO expectedUserResultDto = UserMapper.userToUserResultDTO(user, ResultStatus.SUCCESS,"Success");
        //when
        UserResultDTO userRegisterResultDTO = loginAndRegisterFacade.register(new UserRequestDTO(user.username(), user.email(), user.password()));
        //then
        assertEquals(userRegisterResultDTO,expectedUserResultDto);
    }

    @Test
    public void should_properly_find_registered_user_by_username(){
        //given
        String username  = "bartek";
        User user = new User(1L,username,"haslo","bartek@email.pl");
        registerRepository.registerUser(user);
        UserResultDTO expectedUserResultDto = UserMapper.userToUserResultDTO(user, ResultStatus.SUCCESS,"Success");
        //when
        UserResultDTO byUsername = loginAndRegisterFacade.findByUsername(username);
        //then
        assertEquals(expectedUserResultDto,byUsername);
    }

    @Test
    public void should_return_empty_result_with_status_failure_and_message_user_not_found_when_searching_with_not_existing_username(){
        String username  = "bartek";
        User user = new User(1L,username,"haslo","bartek@email.pl");
        registerRepository.registerUser(user);
        UserResultDTO emptyUser = UserMapper.userToUserResultDTO(null,ResultStatus.FAILURE,"User Not Found");
        //when
        UserResultDTO byWrongUsername = loginAndRegisterFacade.findByUsername("kamil");
        //then
        assertEquals(emptyUser,byWrongUsername);
    }

    @Test
    public void should_return_empty_result_with_status_failure_and_message_user_with_given_email_already_exists_when_registering_with_taken_email_whether_username_taken_or_not(){
        //given
        User user1 = new User(1L,"bartek","haslo","bartek@email.com");
        registerRepository.registerUser(user1);
        UserRequestDTO userWithSameEmailSameUsername = new UserRequestDTO("bartek","bartek@email.com","password");
        UserRequestDTO userWithSameEmailNewUsername = new UserRequestDTO("bartek2","bartek@email.com","password");
        UserResultDTO expectedResult = UserMapper.userToUserResultDTO(null,ResultStatus.FAILURE,"User with given email already exists");
        //when
        UserResultDTO userResultDTO1 = loginAndRegisterFacade.register(userWithSameEmailNewUsername);
        UserResultDTO userResultDTO2 = loginAndRegisterFacade.register(userWithSameEmailSameUsername);
        //then
        assertEquals(expectedResult,userResultDTO1);
        assertEquals(expectedResult,userResultDTO2);
    }

    @Test
    public void should_return_empty_result_with_status_failure_and_message_user_with_given_username_already_exists_when_registering_with_taken_username_and_not_taken_email(){
        //given
        User user1 = new User(1L,"bartek","haslo","bartek@email.pl");
        registerRepository.registerUser(user1);
        UserRequestDTO userWithNewEmailSameUsername = new UserRequestDTO("bartek","bartek2@email.com","password");
        UserResultDTO expectedResult = UserMapper.userToUserResultDTO(null,ResultStatus.FAILURE,"User with given username already exists");
        //when
        UserResultDTO registerResultDTO = loginAndRegisterFacade.register(userWithNewEmailSameUsername);
        //then
        assertEquals(expectedResult,registerResultDTO);
    }
}