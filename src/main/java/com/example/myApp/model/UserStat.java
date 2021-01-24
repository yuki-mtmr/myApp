package com.example.myApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserStat {
    private static final long serialVersionUID = 1L;


    private int status_id;

    private int user_id;

    private String statusName;

    private int statusVolume;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt; //'_'を使うとDBからjsonに渡す時nullが入るためキャメルケースに変更

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;
}
