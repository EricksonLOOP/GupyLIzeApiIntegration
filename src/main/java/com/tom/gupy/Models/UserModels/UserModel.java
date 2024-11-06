package com.tom.gupy.Models.UserModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserModel {
    private String name;
    private String enrollment_number;
    private String email;
    private String userName;
    private String password;
    public UserModel(){

    }
}
