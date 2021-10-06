package com.digitusforum.user;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import i18.M;
import util.ThrowService;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public UserVO create(UserVO user2) {
		if (StringUtils.isBlank(user2.getUserEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(user2.getUserPassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_PASSWORD);

		Optional<UserEntity> userFromDB = userRepository.findByEmailAndDeletedIsFalse(user2.getUserEmail());
		if (userFromDB.isPresent())
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_EMAIL_ALREADY_IN_USE);

		UserEntity user = userRepository.save(new UserEntity(user2));
		user2.setUserId(user.getUserId());
		return user2;
	}

	public List<UserEntity> retrieve(String locale) {
		return userRepository.findByDeletedIsFalse();
	}

	public UserEntity retrieveById(String locale, int id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.isEmpty())
			throw ThrowService.doIt(locale, 404, M.USER_NOT_FOUND);
		return user.get();
	}

	public UserVO retrieveByEmailAndPassword(UserVO userVO) {
		if (StringUtils.isBlank(userVO.getUserEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(userVO.getUserPassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_PASSWORD);

		Optional<UserEntity> userFromDB = userRepository.findByEmailAndPasswordAndDeletedIsFalse(userVO.getUserEmail(),
				userVO.getUserPassword());
		if (!userFromDB.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.LOGIN_WRONG_LOGIN_OR_PASSWORD);

		userVO.setUserId(userFromDB.get().getUserId());
		userVO.setUserPassword(null);

		return userVO;
	}

	public UserVO update(UserVO user, String locale, int id) {
		if (StringUtils.isBlank(user.getUserEmail()))
			throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(user.getUserPassword()))
			throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_PASSWORD);

		Optional<UserEntity> userFromDB = userRepository.findById(id);
		if (userFromDB.isEmpty())
			throw ThrowService.doIt(locale, 404, M.USER_NOT_FOUND);

		userFromDB = userRepository.findByEmailAndUserIdNotAndDeletedIsFalse(user.getUserEmail(), id);
		if (userFromDB.isPresent())
			throw ThrowService.doIt(locale, 403, M.USER_EMAIL_ALREADY_IN_USE);

		user.setUserId(id);
		userRepository.save(new UserEntity(user));
		return user;
	}

	public UserEntity delete(String locale, int id) {
		Optional<UserEntity> userFromDB = userRepository.findById(id);
		if (userFromDB.isEmpty())
			throw ThrowService.doIt(locale, 404, M.USER_NOT_FOUND);

		UserEntity user = userFromDB.get();
		user.setDeleted(true);
		userRepository.save(user);
		user.setPassword("");
		return user;
	}

	public void deleteTest(String locale, int id) {
		Optional<UserEntity> userFromDB = userRepository.findById(id);
		if (userFromDB.isEmpty())
			throw ThrowService.doIt(locale, 404, M.USER_NOT_FOUND);

		userRepository.delete(userFromDB.get());
	}

}
