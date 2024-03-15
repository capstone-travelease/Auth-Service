package com.authservice.Controller;


import com.authservice.DTO.LoginRequestDTO;
import com.authservice.DTO.ResponeStatusDTO;
import com.authservice.DTO.SignUpDTO;
import com.authservice.Service.AuthService;
import com.authservice.Service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(path = "/login")
    public ResponeStatusDTO signIn(@RequestBody @Valid LoginRequestDTO request, HttpServletResponse response) {
        HashMap<String,Object> userData = new HashMap<>();
        var isCheckRespone = authService.Login(request);
        if(isCheckRespone == null){
            response.setStatus(401);
            return new ResponeStatusDTO(response.getStatus(),null,"UNAUTHENTICED");
        }
        userData.put("userId",isCheckRespone.getUserId());
        userData.put("fullName", isCheckRespone.getFullName());
        userData.put("token",jwtService.createToken(userData,isCheckRespone.getEmail()));
        return new ResponeStatusDTO(response.getStatus(),userData,"OK");
    }

    @PostMapping(path = "/sign")
    public ResponeStatusDTO signUp(@RequestBody @Valid SignUpDTO request, HttpServletResponse response){
        var isCheckRespone = authService.signUp(request);
        if(!isCheckRespone){
            response.setStatus(500);
            return new ResponeStatusDTO(response.getStatus(),null,"INTERNAL_SERVER_ERROR");
        }
        return new ResponeStatusDTO(response.getStatus(),null,"OK");
    }

    @GetMapping(path = "/test")
    public boolean tokenTest(@RequestParam(name = "token",required = true) String token){
        return jwtService.isTokenValid(token);
    }
}
