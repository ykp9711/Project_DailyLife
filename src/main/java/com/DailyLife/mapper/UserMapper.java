package com.DailyLife.mapper;

import com.DailyLife.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {

    List <User> findAll();

    int addUser(User user);

    int login(User user);
    Optional<String> findByEmail(@Param("userEmail") String email);

    int deleteUser(@Param("userId")String userId);

    int CheckById(@Param("userId")String userId); // userId 중복확인

    int CheckByUserNickName(String userNickName); // userNickName 중복확인

}
