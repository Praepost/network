package org.oril.services;

import lombok.AllArgsConstructor;
import org.oril.entities.role.Role;
import org.oril.entities.role.repository.RoleRepo;
import org.oril.entities.user.Dto.AuthRequest;
import org.oril.entities.user.Dto.UserVO;
import org.oril.entities.user.User;
import org.oril.entities.user.repository.UserRepo;
import org.oril.exception.UserFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserVO save(AuthRequest authRequest) throws UserFoundException {
        Role role = roleRepo.findByName("ROLE_USER").get();
        Set roles = new HashSet();
        roles.add(role);

        User user = new User();
        String userId = String.valueOf(new Date().getTime());
        user.setId(Long.valueOf(userId));
        user.setRoles(roles);

        UserVO userVO = new UserVO(
                user.getId().toString(),
                user.getEmail(),
                user.getRoles().stream().findFirst().get().toString());

        return userVO;

    }

    public boolean notExists(AuthRequest userVO) {
        Optional<User> user = userRepo.findByEmail(userVO.getEmail());

        if (user.isPresent()){
            return false;
        }

        return true;
    }
}
