package org.example.authservice.entity;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;


public enum Roles {
    USER, ADMIN;

    public List<SimpleGrantedAuthority> grantedAuthorityList(){
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Roles value : Roles.values()) {
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + value.name()));
        }
        return grantedAuthorityList;
    }

}
