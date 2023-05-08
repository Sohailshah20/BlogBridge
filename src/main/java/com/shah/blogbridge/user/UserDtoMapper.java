package com.shah.blogbridge.user;

public class UserDtoMapper {

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getBio(),
                user.getAvatar()
        );
    }
}
