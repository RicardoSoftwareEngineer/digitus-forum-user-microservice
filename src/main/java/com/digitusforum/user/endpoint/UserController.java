package com.digitusforum.user.endpoint;

import com.digitusforum.user.model.repository.UserRepository;
import com.digitusforum.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import vo.UserVO;

import java.util.Optional;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	

	@RequestMapping(value = "/user/v1/create")
	public Object create(@RequestHeader(defaultValue = "en_us") String locale, @RequestBody UserVO user) {
		return userService.create(user, locale);
	}

	@RequestMapping(value = "/user/v1/retrieve")
	public Object retrieve(@RequestHeader(defaultValue = "en_us") String locale) {
		return userService.retrieve(locale);
	}

	@RequestMapping(value = "/user/v1/{id}/retrieve")
	public Object retrieveById(@RequestHeader(defaultValue = "en_us") String locale,
			@PathVariable Optional<Integer> id) {

		return userService.retrieveById(locale, id.get());
	}

	@RequestMapping(value = "/user/v1/retrieve/byEmailAndPassword")
	public Object retrieve(@RequestHeader(defaultValue = "en_us") String locale, @RequestBody UserVO user) {

		return userService.retrieveByEmailAndPassword(user, locale);
	}
	
	@RequestMapping(value = "/user/v1/{id}/update")
	public Object update(@RequestHeader(defaultValue = "en_us") String locale,
			@PathVariable Optional<Integer> id, @RequestBody UserVO user) {

		return userService.update(user, locale, id.get());
	}

	@RequestMapping(value = "/user/v1/{id}/delete")
	public Object delete(@RequestHeader(defaultValue = "en_us") String locale,
			@PathVariable Optional<Integer> id) {
		return userService.delete(locale, id.get());
	}

}