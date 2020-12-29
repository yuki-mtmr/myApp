package com.example.myApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class UserSkill implements Serializable {
    private static final long serialVersionUID = 1L;


    private int skill_id;

    private int user_id;

    private String skillName;

    private int skillLevel;

    private String skillDetail;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt; //'_'を使うとDBからjsonに渡す時nullが入るためキャメルケースに変更

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;
}
