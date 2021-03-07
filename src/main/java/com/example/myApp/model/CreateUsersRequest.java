package com.example.myApp.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
public class CreateUsersRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "must not be empty")
    @Size(max = 20, message = "must be within 20 characters")
    private String userName;

    @NotNull(message = "must not be empty")
    @Size(max = 20, message = "must be within 20 characters")
    private String firstName;

    @NotNull(message = "must not be empty")
    @Size(max = 20, message = "must be within 20 characters")
    private String lastName;

    @NotNull(message = "must not be empty")
    @Pattern(regexp = "[\\w\\-\\._]+@[\\w\\-\\._]+\\.[A-Za-z]+", message = "is incorrect")
    private String email;

    @NotNull(message = "must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "is incorrect")
    @Size(min = 8, max = 20, message = "must be 1 to 20 characters")
    private String password;

    @NotNull(message = "must not be empty")
    @Pattern(regexp = "^[0-9]+$", message = "is incorrect")
    @Size(max = 12, message = "must be within 12 characters")
    private String phone;

    @NotNull(message = "must not be empty")
    private String imageUrl;

}