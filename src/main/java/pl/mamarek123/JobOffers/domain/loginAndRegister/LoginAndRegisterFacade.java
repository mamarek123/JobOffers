package pl.mamarek123.JobOffers.domain.loginAndRegister;

import org.springframework.security.authentication.BadCredentialsException;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserRequestDto;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDto;
import pl.mamarek123.JobOffers.domain.loginAndRegister.error.UserAlreadyExistsException;

import java.util.Optional;

public class LoginAndRegisterFacade {
    LoginAndRegisterFacade(LoginAndRegisterRepostiory registerRepostiory) {
        this.registerRepostiory = registerRepostiory;
    }

    private final LoginAndRegisterRepostiory registerRepostiory;

    public UserResultDto findByUsername(String username){
        User user = registerRepostiory.findByUsername(username).orElseThrow(() -> new BadCredentialsException("User not found"));
        return UserMapper.userToUserResultDto(user);
    }

    public UserResultDto register(UserRequestDto userRequestDto) {
        // Check if a user with the given username already exists
        if (registerRepostiory.existsByUsername(userRequestDto.username())) {
            throw new UserAlreadyExistsException("User with username " + userRequestDto.username() + " already exists");
        }

        // Check if a user with the given email already exists
        if (registerRepostiory.existsByEmail(userRequestDto.email())) {
            throw new UserAlreadyExistsException("User with email " + userRequestDto.email() + " already exists");
        }

        User user = UserMapper.userRequestDtoToUser(userRequestDto);
        User savedUser = registerRepostiory.save(user);
        return UserMapper.userToUserResultDto(savedUser);
    }

}
