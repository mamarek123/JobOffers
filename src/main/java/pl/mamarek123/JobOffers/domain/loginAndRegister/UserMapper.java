package pl.mamarek123.JobOffers.domain.loginAndRegister;

import pl.mamarek123.JobOffers.domain.ResultStatus;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserRequestDTO;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDTO;

class UserMapper {
    static UserResultDTO userToUserResultDTO(User user, ResultStatus status, String message) {
        UserResultDTO userResultDTO = UserResultDTO.builder().
                email(user.email()).
                password(user.password()).
                username(user.username()).
                message(message).
                status(status).
                build();
        return userResultDTO;
    }

    static User userRequestDTOToUser(UserRequestDTO userRequestDTO) {
        User user =  User.builder()
                .username(userRequestDTO.username())
                .email(userRequestDTO.email())
                .password(userRequestDTO.password())
                .build();
        return user;
    }
}
