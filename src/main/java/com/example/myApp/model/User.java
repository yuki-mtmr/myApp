package com.example.myApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private String imageUrl;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt; //'_'を使うとDBからjsonに渡す時nullが入るためキャメルケースに変更

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;
}