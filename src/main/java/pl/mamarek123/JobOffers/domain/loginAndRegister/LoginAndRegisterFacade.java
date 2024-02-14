package pl.mamarek123.JobOffers.domain.loginAndRegister;

import pl.mamarek123.JobOffers.domain.ResultStatus;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserRequestDTO;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDTO;

import java.util.Optional;

public class LoginAndRegisterFacade {
    public LoginAndRegisterFacade(LoginAndRegisterRepostiory registerRepostiory) {
        this.registerRepostiory = registerRepostiory;
        registerRepostioryCheckerIfExists = new RegisterRepositoryCheckerIfExists(registerRepostiory);
    }

    private final LoginAndRegisterRepostiory registerRepostiory;
    private final RegisterRepositoryCheckerIfExists registerRepostioryCheckerIfExists;

    public UserResultDTO findByUsername(String username){
        Optional<User> user = registerRepostiory.getUserByUsername(username);

        if(user.isEmpty()){
            UserResultDTO emptyUserResult = UserMapper.userToUserResultDTO(null, ResultStatus.FAILURE, "User Not Found");
            return emptyUserResult;
        }

        UserResultDTO userResult = UserMapper.userToUserResultDTO(user.get(), ResultStatus.SUCCESS, "Success");
        return userResult;
    }

    public UserResultDTO register(UserRequestDTO userRequestDTO){
        User user = UserMapper.userRequestDTOToUser(userRequestDTO);

        if (registerRepostioryCheckerIfExists.checkIfUserWithGivenEmailExists(user.email())){
            UserResultDTO existingUserResult = UserMapper.userToUserResultDTO(null, ResultStatus.FAILURE, "User with given email already exists");
            return existingUserResult;
        }

        if (registerRepostioryCheckerIfExists.checkIfUserWithGivenUsernameExists(user.username())){
            UserResultDTO existingUserResult = UserMapper.userToUserResultDTO(null, ResultStatus.FAILURE, "User with given username already exists");
            return existingUserResult;
        }

        Optional<UserResultDTO> optionalUserResultDTO = registerRepostiory.registerUser(user);
        if(optionalUserResultDTO.isEmpty()){
            return UserMapper.userToUserResultDTO(null,ResultStatus.FAILURE, "Failed to register in database");
        }
        return optionalUserResultDTO.get();
    }
}
