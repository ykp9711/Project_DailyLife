package com.DailyLife.dao;

import com.DailyLife.dto.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    public static List<User> users;

    public List<User> getAllUsers() {
        return users;
    }
    // Select one user by userId
    public User getUserByUserId(String userId) {
        return users
                .stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .orElse(new User());
    }

    // Insert User
    public User insertUser(User user) {
        users.add(user);

        return user;
    }

    // Modify User
    public void updateUser(String userId,User user) {
        users.stream()
                .filter(curUser -> curUser.getUserId().equals(userId))
                .findAny()
                .orElse(new User())
                .setUserNickName((user.getUserNickName()));
    }

    // Delete User
    public void deleteUser(String userId) {
        users.removeIf(user -> user.getUserId().equals(userId));
    }
}
