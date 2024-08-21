package com.example.demo.controller;



import com.example.demo.model.User;
import com.example.demo.sevice.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    private final MessageSource messageSource;

    public UserController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<User>>> getAllUsers(@RequestHeader(name = "Accept-Language", required = false)Locale locale) {
        String message = messageSource.getMessage("get_all", null, LocaleContextHolder.getLocale());
        List<User> users = service.getALl();
        ApiResponseWrapper<List<User>> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                message,
                users
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<User>> getUserById(@PathVariable Long id, @RequestHeader(name = "Accept-Language", required = false)Locale locale) {
        Optional<User> user = service.getUserById(id);
        if (user.isPresent()) {
            String message = messageSource.getMessage("user_found", null, LocaleContextHolder.getLocale());
            ApiResponseWrapper<User> response = new ApiResponseWrapper<>(
                    HttpStatus.OK.value(),
                    message,
                    user.get()
            );
            return ResponseEntity.ok(response);
        } else {
            String message = messageSource.getMessage("user_not_found", null, LocaleContextHolder.getLocale());
            ApiResponseWrapper<User> response = new ApiResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    message,
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<User>> createUser(@Valid @RequestBody User user,@RequestHeader(name = "Accept-Language", required = false)Locale locale) {
        User savedUser = service.saveUser(user);
        String message = messageSource.getMessage("user_created", null, LocaleContextHolder.getLocale());
        ApiResponseWrapper<User> response = new ApiResponseWrapper<>(
                HttpStatus.CREATED.value(),
                message,
                savedUser
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<User>> updateUser(@PathVariable Long id, @Valid @RequestBody User user, @RequestHeader(name = "Accept-Language", required = false)Locale locale) {
        try {
            User updatedUser = service.updateUser(id, user);
            String message = messageSource.getMessage("user_updated", null, LocaleContextHolder.getLocale());
            ApiResponseWrapper<User> response = new ApiResponseWrapper<>(
                    HttpStatus.OK.value(),
                    message,
                    updatedUser
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            String message = messageSource.getMessage("user_not_found", null, LocaleContextHolder.getLocale());
            ApiResponseWrapper<User> response = new ApiResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    message,
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteUser(@PathVariable Long id,@RequestHeader(name = "Accept-Language", required = false)Locale locale) {
        service.deleteUser(id);
        String message = messageSource.getMessage("user_deleted", null, LocaleContextHolder.getLocale());
        ApiResponseWrapper<Void> response = new ApiResponseWrapper<>(
                HttpStatus.NO_CONTENT.value(),
                message,
                null
        );
        return ResponseEntity.noContent().build();
    }
}
