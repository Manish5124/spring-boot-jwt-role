package com.manish.springrolejwt.controller;

import com.manish.springrolejwt.config.TokenProvider;
import com.manish.springrolejwt.model.*;
import com.manish.springrolejwt.service.UserService;
import com.manish.springrolejwt.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "Login and Validation endpoints for Authorization Service")
@RestController
@Slf4j
//@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserServiceImpl userService;

    private VaildatingDTO vaildatingDTO = new VaildatingDTO();

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {


        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()

                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        vaildatingDTO.setValidStatus(true);
        return ResponseEntity.ok(new AuthToken(token));
    }

//    @RequestMapping(value="/registration", method = RequestMethod.POST)
//    public String saveUser(@RequestBody UserDto user){
//         userService.save(user);
//        return "redirect:/registration?success";
//    }


    @GetMapping(path = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "tokenValidation", notes = "returns boolean after validating JWT", httpMethod = "GET", response = ResponseEntity.class)
    public ResponseEntity<VaildatingDTO> validatingAuthorizationToken(
            @ApiParam(name = "Authorization", value = "JWT generated for current customer present") @RequestHeader(name = "Authorization") String tokenDup) {

        log.info("BEGIN - [validatingAuthorizationToken(JWT-token)]");
        String token = tokenDup.substring(7);
        try {
            UserDetails user = userService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token));
            if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(token, user))) {
                log.debug("Token matched is Valid");
                log.info("Token matched is Valid");
                log.info("END - validate()");
                vaildatingDTO.setValidStatus(true);
                return new ResponseEntity<>(vaildatingDTO, HttpStatus.OK);
            } else {
                throw new LoginException("Invalid Token");
            }
        } catch (Exception e) {
            log.debug("Invalid token - Bad Credentials Exception");
            log.info("END Exception - validatingAuthorizationToken()");
            vaildatingDTO.setValidStatus(false);
            return new ResponseEntity<>(vaildatingDTO, HttpStatus.BAD_REQUEST);
        }

    }



    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/adminping", method = RequestMethod.GET)
    public String adminPing(){
        return "Only Admins Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/userping", method = RequestMethod.GET)
    public String userPing(){
        return "Any User Can Read This";
    }

}
