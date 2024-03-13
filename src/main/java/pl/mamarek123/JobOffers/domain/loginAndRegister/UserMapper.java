package pl.mamarek123.JobOffers.domain.loginAndRegister;

import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserRequestDto;
import pl.mamarek123.JobOffers.domain.loginAndRegister.DTO.UserResultDto;

class UserMapper {
    static UserResultDto userToUserResultDto(User user) {
        UserResultDto userResultDto = UserResultDto.builder().
                email(user.email()).
                password(user.password()).
                username(user.username()).
                build();
        return userResultDto;
    }
    

    static User userRequestDtoToUser(UserRequestDto userRequestDTO) {
        User user =  User.builder()
                .username(userRequestDTO.username())
                .email(userRequestDTO.email())
                .password(userRequestDTO.password())
                .build();
        return user;
    }
}
