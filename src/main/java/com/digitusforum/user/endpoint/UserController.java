package com.digitusforum.user.endpoint;

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

    @RequestMapping(value = "/user/create")
    public Object create() {

        return "hi daddy";
    }

    @RequestMapping(value = "/user/retrieve")
    public Object retrieve(@PathVariable Optional<String> id) {
        return "hi dad";
    }

    @RequestMapping(value = "/user/retrieve/by/emailAndPassword")
    public Object retrieve(@RequestHeader(defaultValue = "en_us") String locale,
                           @RequestBody UserVO user) {

        return userService.findByEmailAndPassword(user, locale);
    }

    @RequestMapping(value = "/user/{id}/retrieve")
    public Object retrieve() {
        return "hi dad";
    }

    @RequestMapping(value = "/user/{id}/update")
    public Object update() {
        return "hi dad";
    }

    @RequestMapping(value = "/user/{id}/delete")
    public Object delete() {
        return "hi dad";
    }

    @RequestMapping(value = "/user/{id}/validate")
    public Object validate() {
        return "hi dad";
    }
}