package com.genuinecoder.springserver.dto;

import lombok.Getter;
import lombok.Setter;


public class ChangePasswordRequest {
    // Getters và Setters
    private String oldPassword;
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

