package com.dans.model.dto;

import java.io.Serializable;

public class JwtDTO implements Serializable {
    private final String jwttoken;

    public JwtDTO(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
