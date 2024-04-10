package com.authservice.Configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary(){
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "dys8o1q1q");
        config.put("api_key", "767611143629384");
        config.put("api_secret", "ZcQq80WafstoayTgDA4jQAY4vg0");
        config.put("secure", true);
        return new Cloudinary(config);
    }
}
