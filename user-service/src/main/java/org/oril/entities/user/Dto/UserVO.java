package org.oril.entities.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

    private String id;
    private String email;
    private String role;
}

