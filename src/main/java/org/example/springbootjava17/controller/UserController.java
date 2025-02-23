package org.example.springbootjava17.controller;


import org.example.springbootjava17.config.AppConfig;
import org.example.springbootjava17.dto.UserAddress;
import org.example.springbootjava17.dto.UserRecord;
import org.example.springbootjava17.dto.UserSummary;
import org.example.springbootjava17.service.UserService;
import org.example.springbootjava17.result.NetworkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.SocketTimeoutException;

@RestController
public class UserController {

    private UserService userService;
    private AppConfig config;

    @Autowired
    public UserController(UserService service, AppConfig env) {
        userService = service;
        config = env;
    }


    @RequestMapping(value="/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRecord createUser(@Valid @RequestBody UserRecord newUser) {

        // control nullability at the boundaries and prevent NPE

        return userService.createUser(newUser);
    }


    @RequestMapping(value="/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<UserSummary> getUsers(Pageable page) {

        var boundedPage = withMaxSize(page);
        var summaries = userService.getUserSummaries(boundedPage);

        return summaries;
    }


    /**
     * Validates the given address.
     *
     * @param address the address to validate
     * @return a ResponseEntity indicating whether the address is valid
     * @since 17 (Uses a preview feature: pattern matching in switch)
     */
    @RequestMapping(value = "/address/validate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Boolean> validateAddress(@RequestBody UserAddress address) {

        // This switch statement uses pattern matching, which is a preview feature in JDK 17.
        return switch (validateAddressWithService(address)) {
            case NetworkResult.Success<Boolean> success -> new ResponseEntity<>(success.result(), HttpStatus.OK);
            case NetworkResult.Failure<Boolean> failure -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            case NetworkResult.Timeout<Boolean> timeout -> new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        };
    }

    private NetworkResult<Boolean> validateAddressWithService(UserAddress address) {

        try {
            return new NetworkResult.Success<>(userService.validateAddressApiCall(address));
        }
        catch(SocketTimeoutException ste) {
            return new NetworkResult.Timeout<>();
        }
        catch(Exception ce) {
            return new NetworkResult.Failure<>(ce);
        }
    }


    private Pageable withMaxSize(Pageable page) {
        return page.getPageSize() > config.page().maxSize()
                ? PageRequest.of(page.getPageSize(), config.page().maxSize(), page.getSort())
                : page;
    }
}
