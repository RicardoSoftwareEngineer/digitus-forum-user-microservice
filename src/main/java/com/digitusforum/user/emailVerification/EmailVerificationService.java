package com.digitusforum.user.emailVerification;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.digitusforum.user.UserEntity;
import com.digitusforum.user.UserRepository;
import com.digitusforum.user.UserService;
import com.digitusforum.user.util.M;
import com.digitusforum.user.util.Util;

@Service
public class EmailVerificationService {

	@Autowired
	EmailVerificationRepository emailVerificationRepository;
	
	@Autowired
	UserRepository userRepository;
	

	public EmailVerificationVO sendEmailValidation(EmailVerificationVO emailVerificationVO) {
		if (StringUtils.isBlank(emailVerificationVO.getEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_EMAIL);

		Optional<UserEntity> userFromDB = userRepository.findByEmailAndDeletedIsFalse(emailVerificationVO.getEmail());
		if (userFromDB.isPresent())
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_EMAIL_ALREADY_IN_USE);

		EmailVerificationEntity emailVerificationFromDB = emailVerificationRepository
				.findByEmail(emailVerificationVO.getEmail());
		if (emailVerificationFromDB == null) {
			EmailVerificationEntity emailVerification = new EmailVerificationEntity(emailVerificationVO.getEmail(),
					emailVerificationRepository);
			emailVerificationRepository.save(emailVerification);
			EmailVerificationSender.sendValidationEmailAsinc(emailVerification.getEmail(), emailVerification.getReadableNumber());
		} else {
			if (pastOneMinute(emailVerificationFromDB.getLastEmailSent())) {
				EmailVerificationSender.sendValidationEmailAsinc(emailVerificationFromDB.getEmail(),
						emailVerificationFromDB.getReadableNumber());
				emailVerificationFromDB.setLastEmailSent(ZonedDateTime.now());
				emailVerificationRepository.save(emailVerificationFromDB);
			}
		}
		emailVerificationVO.setResponse(M.EMAIL_SENT);
		return emailVerificationVO;
	}

	private boolean pastOneMinute(ZonedDateTime createdIn) {
		return ZonedDateTime.now().isAfter(createdIn.plusMinutes(1));
	}

	private void assertCodeStillValid(EmailVerificationEntity emailVerificationFromDB) {
		if (emailVerificationFromDB.getLastEmailSent() == null
				|| ZonedDateTime.now().isAfter(emailVerificationFromDB.getLastEmailSent().plusMinutes(15)))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.VALIDATION_NUMBER_NOT_FOUND);
	}

	public EmailVerificationVO validateEmail(EmailVerificationVO emailVerificationVO) {
		if (StringUtils.isBlank(emailVerificationVO.getEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_EMAIL);
		
		if (StringUtils.isBlank(emailVerificationVO.getPassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_PASSWORD);
		
		if (StringUtils.isBlank(emailVerificationVO.getRetypePassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_PASSWORD);
		
		if(!emailVerificationVO.getPassword().equals(emailVerificationVO.getRetypePassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.DIFERENT_PASSWORD);

		if (emailVerificationVO.getReadableNumber() == null || emailVerificationVO.getReadableNumber() == 0)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_READABLE_NUMBER);

		EmailVerificationEntity emailVerificationFromDB = emailVerificationRepository
				.findByEmailAndReadableNumber(emailVerificationVO.getEmail(), emailVerificationVO.getReadableNumber());
		if (emailVerificationFromDB == null) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.VALIDATION_NUMBER_NOT_FOUND);
		
		if(!emailVerificationVO.getReadableNumber().equals(emailVerificationFromDB.getReadableNumber()))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.VALIDATION_NUMBER_NOT_FOUND);

		assertCodeStillValid(emailVerificationFromDB);
		
		UserEntity user = new UserEntity();
		user.setEmail(emailVerificationVO.getEmail());
		user.setPassword(Util.encrypt(emailVerificationVO.getPassword()));
		userRepository.save(user);
		
		emailVerificationRepository.deleteById(emailVerificationFromDB.getEmailVerificationId());
		
		emailVerificationVO.setResponse(M.EMAIL_VALIDATED);
		return emailVerificationVO;
	}
	
	public EmailVerificationVO sendResetPasswordEmail(EmailVerificationVO emailVerificationVO) {
		if (StringUtils.isBlank(emailVerificationVO.getEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_EMAIL);
		
		UserEntity user = userRepository.findByEmailAndDeletedIsFalse(emailVerificationVO.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, M.USER_NOT_FOUND));
		
		EmailVerificationEntity emailVerification = new EmailVerificationEntity(emailVerificationVO.getEmail(),
				emailVerificationRepository);
		emailVerificationRepository.save(emailVerification);
		EmailVerificationSender.sendResetPasswordEmailAsinc(emailVerification.getEmail(), emailVerification.getReadableNumber());
		
		emailVerificationVO.setResponse(M.EMAIL_SENT);
		return emailVerificationVO;
	}
	
	public EmailVerificationVO resetPassword(EmailVerificationVO emailVerificationVO) {
		if (StringUtils.isBlank(emailVerificationVO.getEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_EMAIL);
		
		if (StringUtils.isBlank(emailVerificationVO.getPassword()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_PASSWORD);

		if (emailVerificationVO.getReadableNumber() == null || emailVerificationVO.getReadableNumber().equals(0))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.USER_MISSING_READABLE_NUMBER);
		
		EmailVerificationEntity emailVerificationFromDB = emailVerificationRepository
				.findByEmailAndReadableNumber(emailVerificationVO.getEmail(), emailVerificationVO.getReadableNumber());
		if (emailVerificationFromDB == null) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.VALIDATION_NUMBER_NOT_FOUND);
		
		if(!emailVerificationVO.getReadableNumber().equals(emailVerificationFromDB.getReadableNumber()))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, M.VALIDATION_NUMBER_NOT_FOUND);

		assertCodeStillValid(emailVerificationFromDB);
		
		UserEntity user = userRepository.findByEmailAndDeletedIsFalse(emailVerificationVO.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, M.USER_NOT_FOUND));
		
		user.setPassword(Util.encrypt(emailVerificationVO.getPassword()));
		userRepository.save(user);
		emailVerificationRepository.deleteById(emailVerificationFromDB.getEmailVerificationId());
		emailVerificationVO.setResponse(M.PASSWORD_RESETED);
		
		
		return emailVerificationVO;
	}
	
	

	

}
