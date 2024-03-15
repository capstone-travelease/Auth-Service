package com.authservice.Service;


import com.authservice.DTO.LoginRequestDTO;
import com.authservice.DTO.SignUpDTO;
import com.authservice.DTO.UserLoginDTO;
import com.authservice.Entity.Users;
import com.authservice.Repository.UserRepository;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    public UserLoginDTO Login(LoginRequestDTO user){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
            if(authenticate.isAuthenticated()){
                HashMap<String,Object> map = new HashMap<>();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                UserLoginDTO userData = userRepository.generateUser(user.getEmail(),3);
                return userData;
            }
            else{
                return null;
            }
        }catch (Exception err){
            System.err.println(err);
            return null;
        }

    }

    public boolean signUp(SignUpDTO user){
        try {
            Optional<Users> users =  userRepository.findByEmail(user.getEmail());
            if(users.isEmpty()){
                String passEncode = new BCryptPasswordEncoder().encode(user.getPassword());
                userRepository.addUser(user.isGender(),3,user.getEmail(),user.getName(),passEncode,user.getPhonenumber(),user.getBirthday());
                return true;
            }
            return false;
        }catch (Exception err){
            System.err.println(err);
            return false;
        }
    }



}
