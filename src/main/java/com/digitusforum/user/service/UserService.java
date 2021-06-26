package com.digitusforum.user.service;

import com.digitusforum.user.model.entity.User;
import com.digitusforum.user.model.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Microservices;
import model.Headers;
import model.M;
import model.Timeouts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import service.ThrowService;
import vo.InternationalizationVO;
import vo.TokenVO;
import vo.UserVO;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findByEmailAndPassword(UserVO user, String locale) {
        if (StringUtils.isBlank(user.getEmail()))
            throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_EMAIL);
        if (StringUtils.isBlank(user.getPassword()))
            throw ThrowService.doIt(locale, 403, M.LOGIN_MISSING_PASSWORD);

        Optional<User> userFromDB = userRepository.
                findByEmailAndPasswordAndDeletedIsFalse(user.getEmail(), user.getPassword());
        if (!userFromDB.isPresent())
            throw ThrowService.doIt(locale, 404, M.LOGIN_WRONG_LOGIN_OR_PASSWORD);

        return userFromDB.get();
//        return new User();
    }

    /*public String validateEmailAndCreateUser(Optional<String> email,
                                                Optional<String> code,
                                                HttpServletRequest request) {
        if (!email.isPresent() || StringUtils.isBlank(email.get()))
            throw UserException.EMAIL_MISSING;
        if (!code.isPresent() || StringUtils.isBlank(code.get()))
            throw UserException.MISSING_VERIFICATION_CODE;
        int verificationCode = 0;
        try {
            verificationCode = Integer.valueOf(code.get());
        } catch (Exception e) {
            throw UserException.INVALID_VERIFICATION_CODE;
        }
        EmailVerification emailVerification =
                emailVerificationRepository.findByEmailAndReadableNumber(
                        email.get(),
                        verificationCode);
        if (emailVerification == null)
            throw UserException.VERIFICATION_CODE_NOT_FOUND;
        if (emailVerification.getUsed())
            throw UserException.VERIFICATION_CODE_ALREADY_USED;
        if (userRepository.findByEmailAndDeletedIsFalse(email.get()).isPresent())
            throw UserException.EMAIL_ALREADY_IN_USE;
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(emailVerification.getName());
        user.setEmail(emailVerification.getEmail());
        user.setPassword(emailVerification.getPassword());
        user.setDeleted(false);
        user.setCreatedIn(ZonedDateTime.now());
        userRepository.save(user);
        emailVerificationRepository.delete(emailVerification);
        user = userRepository.findByEmailAndPasswordAndDeletedIsFalse(user.getEmail(), user.getPassword()).get();
        return tokenService.createJWTToken(ZonedDateTime.now().plusHours(8), user);
    }*/

    /*public String sendCreationEmail(User user,
                                    HttpServletRequest request) {
        if (StringUtils.isBlank(user.getEmail()))
            throw UserException.EMAIL_MISSING;
        if (StringUtils.isBlank(user.getFirstName()))
            throw UserException.MISSING_FIRST_NAME;
        if (StringUtils.isBlank(user.getPassword()))
            throw UserException.MISSING_PASSWORD;
        if (userRepository.findByEmailAndProjectIdAndActiveIsTrue(user.getEmail(), project.getProjectId()).isPresent())
            throw UserException.EMAIL_ALREADY_IN_USE;

        return emailService.sendEmailToCreateAccountInVendele(emailVerificationRepository, project, user);
    }*/



    /*public JSONObject delete(Optional<String> authorization, Optional<String> baseUrl, User user, HttpServletRequest request) {
        String userId = tokenService.validateToken(authorization);
        String projectId = generalService.getBaseProjectId(request, baseUrl, projectRepository);

        if (StringUtils.isBlank(user.getUserId()))
            throw UserException.MISSING_USER_ID;
        Optional<User> userFromDB = userRepository.findByUserIdAndProjectIdAndActiveIsTrue(user.getUserId(), projectId);
        if (!userFromDB.isPresent())
            throw UserException.USER_NOT_FOUND;
        userFromDB.get().setActive(false);
        userRepository.save(userFromDB.get());
        return new JSONObject().put("response", "user deactivated  " + userFromDB.get().getUserId());
    }*/

    /*private void generalFlux(User user) {
        if (StringUtils.isBlank(user.getEmail()))
            throw UserException.EMAIL_MISSING;
        if (userRepository.findByEmailAndProjectIdAndActiveIsTrue(user.getEmail(), user.getProjectId()).isPresent())
            throw UserException.EMAIL_ALREADY_IN_USE;
        if (StringUtils.isBlank(user.getPlanId()))
            throw PlanException.PLAN_ID_MISSING;
        if (!planRepository.findByProjectIdAndPlanId(user.getProjectId(), user.getPlanId()).isPresent())
            throw PlanException.PLAN_NOT_FOUND;
    }*/


}
