package org.oril.entities.role;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Table(name = "role")
@ToString
public class Role implements GrantedAuthority {
    @Id
    private Long id;

    @NonNull
    @Column(length = 20)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}