package org.oril.entities.user.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {

    private String email;
    private String password;
    private String name;
}
