package com.digitusforum.user.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitusforum.user.model.entity.User;
import com.digitusforum.user.model.repository.UserRepository;

import model.M;
import service.ThrowService;
import vo.UserVO;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public User findByEmailAndPassword(UserVO user, String locale) {
		if (StringUtils.isBlank(user.getEmail()))
			throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(user.getPassword()))
			throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_PASSWORD);

		Optional<User> userFromDB = userRepository.findByEmailAndPasswordAndDeletedIsFalse(user.getEmail(),
				user.getPassword());
		if (!userFromDB.isPresent())
			throw ThrowService.doIt(locale, 404, M.LOGIN_WRONG_LOGIN_OR_PASSWORD);

		return userFromDB.get();
	}

}
