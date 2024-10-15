package org.oril.entities.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

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