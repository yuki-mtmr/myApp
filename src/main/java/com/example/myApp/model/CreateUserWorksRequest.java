package com.example.myApp.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CreateUserWorksRequest  implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "must not be empty")
    @Size(max = 20, message = "must be within 20 characters")
    private String workThumbnail;

    @NotNull(message = "must not be empty")
    private String workLink;

    @NotNull(message = "must not be empty")
    @Size(max = 20, message = "must be within 20 characters")
    private String workDetail;

}
