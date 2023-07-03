package com.example.Controller;

import com.example.config.RestEndpoints;
import com.example.request.PasswordResetRequest;
import com.example.response.TaskResponse;
import com.example.service.PasswordResetService;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping(RestEndpoints.USER)
public class UserController {
    private final UserService userService;
    private final PasswordResetService passwordResetService;

    @Autowired
    public UserController(UserService userService, PasswordResetService passwordResetService) {
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    @GetMapping(RestEndpoints.GET_BY_USERID)
    public ResponseEntity<?> getAllTasksForUser(@PathVariable long userId,
                                                @SortDefault(sort = "name",caseSensitive = false)
                                                @PageableDefault(size = 20) Pageable pageable) {
        List<TaskResponse> taskResponseList = userService.getAllTasks(userId);
        return ResponseEntity.ok().body(taskResponseList);
    }

    @GetMapping(RestEndpoints.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword( @Valid @RequestBody PasswordResetRequest resetPassword,
                                           HttpServletRequest request, HttpServletResponse response) {
        String token = request.getServletPath().split("/")[4];
        if (token == null) {
            throw new RuntimeException("invalid url");
        }
        passwordResetService.resetPassword(resetPassword, token);
        return ResponseEntity.ok().body("password has been reset successfully");
    }
}
