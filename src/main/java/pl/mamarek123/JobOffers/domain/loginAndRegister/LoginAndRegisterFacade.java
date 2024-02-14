package pl.mamarek123.JobOffers.domain.loginAndRegister;

import lombok.AllArgsConstructor;
import pl.mamarek123.JobOffers.domain.ResultStatus;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserRequestDTO;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDTO;

import java.util.Optional;

@AllArgsConstructor
public class LoginAndRegisterFacade {
    private LoginAndRegisterRepostiory registerRepostiory;
    private RegisterRepositoryCheckerIfExists registerRepostioryCheckerIfExists;

    public UserResultDTO findByUsername(String username){
        Optional<User> user = registerRepostiory.getUserByUsername(username);

        if(user.isEmpty()){
            UserResultDTO emptyUserResult = UserMapper.userToUserResultDTOP(null, ResultStatus.FAILURE, "User Not Found");
        }

        UserResultDTO userResult = UserMapper.userToUserResultDTOP(user.get(), ResultStatus.SUCCESS, "Success");
        return userResult;
    }

    public UserResultDTO register(UserRequestDTO userRequestDTO){
        User user = UserMapper.userRequestDTOToUser(userRequestDTO);

        if (registerRepostioryCheckerIfExists.checkIfUserWithGivenEmailExists(user.email())){
            UserResultDTO existingUserResult = UserMapper.userToUserResultDTOP(null, ResultStatus.FAILURE, "User with given email already exists");
            return existingUserResult;
        }

        if (registerRepostioryCheckerIfExists.checkIfUserWithGivenUsernameExists(user.username())){
            UserResultDTO existingUserResult = UserMapper.userToUserResultDTOP(null, ResultStatus.FAILURE, "User with given username already exists");
            return existingUserResult;
        }

        Optional<UserResultDTO> optionalUserResultDTO = registerRepostiory.RegisterUser(user);
        if(optionalUserResultDTO.isEmpty()){
            return UserMapper.userToUserResultDTOP(null,ResultStatus.FAILURE, "Failed to register in database");
        }
        return optionalUserResultDTO.get();
    }
}
