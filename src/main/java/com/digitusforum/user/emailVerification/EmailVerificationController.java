package com.digitusforum.user.emailVerification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailVerificationController {
	@Autowired
	EmailVerificationService emailVerificationService;

	@RequestMapping(value = "/emailVerification/v1/sendValidationEmail")
	public EmailVerificationVO sendEmailValidation(@RequestBody EmailVerificationVO emailVerificationVO) {
		return emailVerificationService.sendEmailValidation(emailVerificationVO);
	}

	@RequestMapping(value = "/emailVerification/v1/validateEmail")
	public EmailVerificationVO validateEmail(@RequestBody EmailVerificationVO emailVerificationVO) {
		return emailVerificationService.validateEmail(emailVerificationVO);
	}

	@RequestMapping(value = "/emailVerification/v1/sendResetPasswordEmail")
	public EmailVerificationVO sendResetPasswordEmail(@RequestBody EmailVerificationVO emailVerificationVO) {
		return emailVerificationService.sendResetPasswordEmail(emailVerificationVO);
	}

	@RequestMapping(value = "/emailVerification/v1/resetPassword")
	public EmailVerificationVO resetPassword(@RequestBody EmailVerificationVO emailVerificationVO) {
		return emailVerificationService.resetPassword(emailVerificationVO);
	}

}