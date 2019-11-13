package com.ascending.training.controller;


import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.ascending.training.model.User;
import com.ascending.training.service.UserService;
import com.ascending.training.util.JwtUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/auth")
//@CrossOrigin(value = "*", origins = "*", allowedHeaders = "*")
public class Authentication {

    Logger logger = LoggerFactory.getLogger((this.getClass()));
    @Autowired private UserService userService;

    private String errorMsg = "The email or password is not correct.";
    private String tokenKeyWord = "Authorization";
    private String tokenType = "Bearer";

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity authenticate(@RequestBody User user) {
        String token = "";

        try {
            logger.info("This is from Authentication Controller");
            logger.debug(user.toString());
            User u = userService.getUserByCredentials(user.getEmail(), user.getPassword());
            if (u == null) return ResponseEntity.status(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION).body(errorMsg);
            logger.info(u.toString());
            token = JwtUtil.generateToken(u);
        }
        catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            if (msg == null) msg = "BAD REQUEST!";
            logger.error(msg);
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(tokenKeyWord + ":" + tokenType + " " + token);
    }

    @RequestMapping(value = "/frontend", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity authenticate(@RequestParam(value = "userName", required = true) String userName,
                                       @RequestParam(value = "userEmail", required = true) String userEmail) {
        String token = "";

        try {
            logger.info("This is from Authentication Controller");
            logger.info("--------------- your input:"+userName+", "+userEmail);
            User u = userService.getUserByUserNameEmail(userName, userEmail);
            logger.info("Authentication Controller:"+u.toString());

            if (u == null) return ResponseEntity.status(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION).body(errorMsg);

            token = JwtUtil.generateToken(u);
        }
        catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            if (msg == null) msg = "BAD REQUEST!";
            logger.error(msg);
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(msg);
        }
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(tokenKeyWord + ":" + tokenType + " " + token);
    }


}
