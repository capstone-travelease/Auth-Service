package com.authservice.Controller;


import com.authservice.DTO.LoginRequestDTO;
import com.authservice.DTO.ResponeStatusDTO;
import com.authservice.DTO.ResponeStatusLoginDTO;
import com.authservice.DTO.SignUpDTO;
import com.authservice.Service.AuthService;
import com.authservice.Service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponeStatusLoginDTO signUp(@RequestBody @Valid SignUpDTO request, HttpServletResponse response){
        Integer isCheckRespone = authService.signUp(request);
        if(isCheckRespone == HttpServletResponse.SC_BAD_REQUEST){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new ResponeStatusLoginDTO(response.getStatus(),null,"Email is existed");
        } else if (isCheckRespone == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponeStatusLoginDTO(response.getStatus(),null,"INTERNAL SERVER ERROR");
        }
        return new ResponeStatusLoginDTO(response.getStatus(),isCheckRespone,"OK");
    }

    @PostMapping(path = "/upload")
    public ResponeStatusDTO upLoad(@RequestParam("fontImage") MultipartFile  front, @RequestParam("backImage") MultipartFile back, @RequestParam("userId") Integer userId, HttpServletResponse response){
        if( front.isEmpty() || !validateFileImg(front.getContentType()) || back.isEmpty() || !validateFileImg(back.getContentType()) ){
            response.setStatus(400);
            return new ResponeStatusDTO(response.getStatus(),null,"BAD QUEST");
        }
        boolean isCheckError = authService.upLoadImage(front,back,userId);
        if(!isCheckError){
            return new ResponeStatusDTO(response.getStatus(),null,"OK");

        }
        response.setStatus(500);
        return new ResponeStatusDTO(response.getStatus(),null,"ADD Failure");
    }
    private boolean validateFileImg(String contentType){
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }
}
