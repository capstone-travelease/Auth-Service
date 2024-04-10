package com.authservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class ResponeStatusLoginDTO {
    private Integer code;
    private Object userId;
    private  String message;
}
