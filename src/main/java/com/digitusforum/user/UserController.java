package com.digitusforum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/v1/create")
	public Object create(@RequestBody UserVO user) {
		return userService.create(user);
	}

	@RequestMapping(value = "/user/v1/retrieve")
	public Object retrieve(@RequestHeader(defaultValue = "en_us") String locale) {
		return userService.retrieve();
	}

	@RequestMapping(value = "/user/v1/{id}/retrieve")
	public Object retrieveById(@PathVariable String id) {
		return userService.retrieveById(id);
	}

	@RequestMapping(value = "/user/v1/retrieve/byEmailAndPassword")
	public UserVO retrieve(@RequestBody UserVO user) {
		return userService.retrieveByEmailAndPassword(user);
	}

	@RequestMapping(value = "/user/v1/{id}/update")
	public Object update(@PathVariable String id, @RequestBody UserVO user) {
		return userService.update(user, id);
	}

	@RequestMapping(value = "/user/v1/{id}/delete")
	public Object delete(@PathVariable String id) {
		return userService.delete(id);
	}
}