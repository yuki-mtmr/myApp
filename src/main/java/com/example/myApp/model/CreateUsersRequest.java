package com.example.myApp.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
public class CreateUsersRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "must not be empty")
    @Size(min = 1, max = 20, message = "must be 1 to 20 characters")
    private String userName;

    @NotNull(message = "must not be empty")
    @Size(min = 1, max = 20, message = "must be 1 to 20 characters")
    private String firstName;

    @NotNull(message = "must not be empty")
    @Size(min = 1, max = 20, message = "must be 1 to 20 characters")
    private String lastName;

    @NotNull(message = "must not be empty")
    private String email;

    @NotNull(message = "must not be empty")
    private String password;

    @NotNull(message = "must not be empty")
    private String phone;

    @NotNull(message = "must not be empty")
    private String imageUrl;

}