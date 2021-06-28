package com.digitusforum.user.service;

import java.util.List;
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
	
	public UserVO create(UserVO user, String locale) {
		if (StringUtils.isBlank(user.getEmail()))
			throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(user.getPassword()))
			throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_PASSWORD);

		Optional<User> userFromDB = userRepository.findByEmailAndDeletedIsFalse(user.getEmail());
		if (userFromDB.isPresent())
			throw ThrowService.doIt(locale, 403, M.USER_EMAIL_ALREADY_IN_USE);
		
		userRepository.save(new User(user));

		return user;
	}
	
	public List<User> retrieve(String locale){
		return userRepository.findByDeletedIsFalse();
	}
	
	public User retrieveById(String locale, int id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) throw ThrowService.doIt(locale, 404, M.USER_NOT_FOUND);
		return user.get();
	}

	public User retrieveByEmailAndPassword(UserVO user, String locale) {
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
	
	public UserVO update(UserVO user, String locale, int id) {
		if (StringUtils.isBlank(user.getEmail()))
			throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(user.getPassword()))
			throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_PASSWORD);

		Optional<User> userFromDB = userRepository.findById(id);
		if (userFromDB.isEmpty())
			throw ThrowService.doIt(locale, 404, M.USER_NOT_FOUND);
		
		userFromDB = userRepository.findByEmailAndUserIdNotAndDeletedIsFalse(user.getEmail(), id);
		if (userFromDB.isPresent())
			throw ThrowService.doIt(locale, 403, M.USER_EMAIL_ALREADY_IN_USE);
		
		user.setUserId(id);
		userRepository.save(new User(user));
		return user;
	}
	
	public User delete(String locale, int id) {
		Optional<User> userFromDB = userRepository.findById(id);
		if (userFromDB.isEmpty())
			throw ThrowService.doIt(locale, 404, M.USER_NOT_FOUND);
		
		User user = userFromDB.get();
		user.setDeleted(true);
		userRepository.save(user);
		user.setPassword("");
		return user;
	}
	

}
