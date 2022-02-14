package com.digitusforum.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;


@SpringBootTest
public class CrudTest {
	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
		assertThat(userService).isNotNull();
	}

	@Test
	void crudTest() {
		/*
		 * String userEmail = UUID.randomUUID().toString(); UserVO userVO = new
		 * UserVO(userEmail, "password"); final UserVO USER_VO = userVO; UserEntity user
		 * = null; ResponseStatusException thrown = null;
		 * 
		 * thrown = assertThrows(ResponseStatusException.class, () -> { //
		 * userService.retrieveByEmailAndPassword(USER_VO, "en_us"); });
		 * assertEquals(thrown.getRawStatusCode(), 404);
		 * assertThat(userVO.getUserId()).isZero(); // userVO =
		 * userService.create(userVO, "en_us");
		 * assertThat(userVO.getUserId()).isNotZero(); List<UserEntity> users =
		 * userService.retrieve("en_us"); assertThat(users.size()).isGreaterThan(0);
		 * user = null; user = userService.retrieveById("en_us", userVO.getUserId());
		 * assertThat(user.getUserId()).isNotNull(); user = null; // user =
		 * userService.retrieveByEmailAndPassword(userVO, "en_us");
		 * assertThat(user.getUserId()).isNotNull(); userVO.setPassword("new password");
		 * final UserVO USER_VO2 = userVO; thrown =
		 * assertThrows(ResponseStatusException.class, () -> { //
		 * userService.retrieveByEmailAndPassword(USER_VO2, "en_us"); });
		 * assertEquals(thrown.getRawStatusCode(), 404); // userService.update(userVO,
		 * "en_us", userVO.getUserId()); user = null; // user =
		 * userService.retrieveByEmailAndPassword(userVO, "en_us");
		 * assertThat(user.getUserId()).isNotNull(); userService.delete("en_us",
		 * user.getUserId()); thrown = assertThrows(ResponseStatusException.class, () ->
		 * { // userService.retrieveByEmailAndPassword(USER_VO2, "en_us"); });
		 * assertEquals(thrown.getRawStatusCode(), 404); userService.deleteTest("en_us",
		 * userVO.getUserId());
		 */
	}

}
