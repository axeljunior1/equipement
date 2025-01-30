package com.projet.equipement.security;

import com.projet.equipement.dto.employe.EmployeGetDto;

public class JwtResponse {
    private String token;
    private EmployeGetDto employeGetDto;

    public JwtResponse(String token, EmployeGetDto employeGetDto) {
        this.token = token;
        this.employeGetDto = employeGetDto;
    }

    public EmployeGetDto getEmployeGetDto() {
        return employeGetDto;
    }
    public void setEmployeGetDto(EmployeGetDto employeGetDto) {
        this.employeGetDto = employeGetDto;
    }

    public JwtResponse() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}