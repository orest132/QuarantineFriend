package me.kollcaku.QuarantineFriends.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kollcaku.QuarantineFriends.enumeration.RoleEnum;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_role_name")
    private String name;


    public UserRoleEntity(RoleEnum roleEnum){
        this.name = roleEnum.name();
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
