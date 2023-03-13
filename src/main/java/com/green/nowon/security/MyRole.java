package com.green.nowon.security;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

//Enum
@Getter
@RequiredArgsConstructor    //final 필드를 파라미터로 구성하는 생성자
public enum MyRole {

    USER("ROLE_USER"),      //0
    ADMIN("ROLE_ADMIN");    //1

    private final String role; //getRole()을 통하여 //ROLE_USER or ROLE_ADMIN 얻어올 수 있음

}
