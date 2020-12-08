package com.roderick.api;

import com.roderick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserApi {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 判断用户名是否已被使用
     */
    @GetMapping("/check/{username}")
    public ResponseEntity<Map<String,String>> checkUsername(@PathVariable String username) {
        Boolean allow = userService.checkUsername(username);
        HashMap<String, String> result = new HashMap<>();
        if (allow){
            result.put("msg","allow");
        }else {
            result.put("msg","refuse");
        }
        return ResponseEntity.ok(result);
    }
}
