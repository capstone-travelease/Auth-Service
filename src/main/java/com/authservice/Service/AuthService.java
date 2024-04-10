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

    @Autowired
    private Cloudinary cloudinary;
    private final Integer MAX_LENGTH_URL = 48;


    public UserLoginDTO Login(LoginRequestDTO user){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
            if(authenticate.isAuthenticated()){
                HashMap<String,Object> map = new HashMap<>();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                UserLoginDTO userData = userRepository.generateUser(user.getEmail(),2);
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

    public Object signUp(SignUpDTO user){
        try {
            Optional<Users> users =  userRepository.findByEmail(user.getEmail());
            if(users.isEmpty()){
                String passEncode = new BCryptPasswordEncoder().encode(user.getPassword());
                Integer userId =  userRepository.addUser(user.isGender(),2,user.getEmail(),user.getName(),passEncode,user.getPhonenumber(),user.getBirthday());
                return userId;
            }
            return null;
        }catch (Exception err){
            System.err.println(err);
            return null;
        }
    }
    public boolean upLoadImage(MultipartFile front,MultipartFile back, Integer userId){
        try {
            String imageFront = getPathImage(front);
            String imageBack = getPathImage(back);
            userRepository.addIndentifyCard(userId,imageFront,imageBack);
            return false;
        }catch (Exception err){
            return true;
        }
    }

    private String getPathImage(MultipartFile image){
        try {
            Map<String,String> params = new HashMap<>();
            params.put("folder","IndentifyCard");
            var dataFront = cloudinary.uploader().upload(image.getBytes(),params);
            String urlImage = (String) dataFront.get("url");
            String pathImage = urlImage.substring(MAX_LENGTH_URL);
            return pathImage;
        }catch (Exception ex){
            return null;
        }
    }



}
