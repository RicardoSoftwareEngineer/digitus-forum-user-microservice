package com.digitusforum.user;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.digitusforum.chat.ChatMessageVO;
import com.digitusforum.user.util.M;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	

	public UserVO create(UserVO userVO) {
		if (StringUtils.isBlank(userVO.getEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(userVO.getPassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_PASSWORD);

		Optional<UserEntity> userFromDB = userRepository.findByEmailAndDeletedIsFalse(userVO.getEmail());
		if (userFromDB.isPresent())
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_EMAIL_ALREADY_IN_USE);

		UserEntity user = userRepository.save(new UserEntity(userVO));
		userVO.setId(user.getId().toString());
		return userVO;
	}

	public List<UserEntity> retrieve() {
		return userRepository.findByDeletedIsFalse();
	}

	public UserEntity retrieveById(String id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.USER_NOT_FOUND);
		return user.get();
	}

	public UserVO retrieveByEmailAndPassword(UserVO userVO) {
		if (StringUtils.isBlank(userVO.getEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(userVO.getPassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_PASSWORD);

        //String decryptedPassword = Encryptors.text("password goes here", "8618d57d94674a78").decrypt(userVO.getPassword());
		
		UserEntity userFromDB = userRepository.findByEmailAndDeletedIsFalse(userVO.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, M.USER_NOT_FOUND));
		
		String decryptedPasswordFromDB = Encryptors.text("password goes here", "8618d57d94674a78").decrypt(userFromDB.getPassword());
		
		if(!userVO.getPassword().equals(decryptedPasswordFromDB))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_WRONG_LOGIN_OR_PASSWORD);

		userVO.setId(userFromDB.getId().toString());
		userVO.setEmail(userFromDB.getEmail());
		userVO.setUserType(userFromDB.getType());
		userVO.setPassword(null);
		return userVO;
	}

	public UserVO update(UserVO user, String id) {
		if (StringUtils.isBlank(user.getEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_EMAIL);
		if (StringUtils.isBlank(user.getPassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.LOGIN_MISSING_PASSWORD);

		Optional<UserEntity> userFromDB = userRepository.findById(id);
		if (userFromDB.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.USER_NOT_FOUND);

		userFromDB = userRepository.findByEmailAndIdNotAndDeletedIsFalse(user.getEmail(), id);
		if (userFromDB.isPresent())
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_EMAIL_ALREADY_IN_USE);

		user.setId(id);
		userRepository.save(new UserEntity(user));
		return user;
	}

	public UserEntity delete(String id) {
		Optional<UserEntity> userFromDB = userRepository.findById(id);
		if (userFromDB.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.USER_NOT_FOUND);

		UserEntity user = userFromDB.get();
		user.setDeleted(true);
		userRepository.save(user);
		user.setPassword("");
		return user;
	}

	public void deleteTest(String locale, String id) {
		Optional<UserEntity> userFromDB = userRepository.findById(id);
		if (userFromDB.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.USER_NOT_FOUND);

		userRepository.delete(userFromDB.get());
	}

}
