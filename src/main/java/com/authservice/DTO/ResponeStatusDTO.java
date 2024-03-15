package com.authservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ResponeStatusDTO {

    private Integer code;
    private Map<String,Object> data;
    private  String message;
}
