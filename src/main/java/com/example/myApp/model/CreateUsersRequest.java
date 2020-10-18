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

    @NotBlank(message = "must not be empty")
    private String userName;

    @NotBlank(message = "must not be empty")
    private String firstName;

    @NotBlank(message = "must not be empty")
    private String lastName;

    @NotBlank(message = "must not be empty")
    private String email;

    @NotBlank(message = "must not be empty")
    private String password;

    @NotBlank(message = "must not be empty")
    private String phone;

    private String image_url;

//    @NotBlank(message = "must not be empty")
//    @Pattern(regexp = "[0-9\\-]{9,16}[0-9X]", message = "is incorrect")
//    private String isbn;
//
//    @NotBlank(message = "must not be empty")
//    @Size(min = 1, max = 30, message = "must be 1 to 30 characters")
//    private String title;
//
//    @NotNull(message = "must not be empty")
//    @Pattern(regexp="[0-9]{1,10}", message="is not proper number")
//    private String price;
}