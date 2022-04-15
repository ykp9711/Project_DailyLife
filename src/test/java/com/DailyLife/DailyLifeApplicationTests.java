package com.DailyLife;

import com.DailyLife.dto.User;
import com.DailyLife.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class DailyLifeApplicationTests {

	@Autowired
	private UserMapper userMapper;


	@Test
	void contextLoads() {
	}

	@Test
	void findAllTest() {
	}

	@Test
	void addUserTest() {
		log.info("테스트 시작");

		User ad = new User();
		ad.setUserId("woa0313");
		ad.setUserPassword("1234");
		ad.setUserEmail("ddd");
		ad.setEmailAuthor(123);

		int result = userMapper.addUser(ad);

		assertThat(1).isEqualTo(result);

	}

}
