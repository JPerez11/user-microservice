package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.actions;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.RoleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.pragma.powerup.usermicroservice.configuration.Constants.ADMIN_ROLE;
import static com.pragma.powerup.usermicroservice.configuration.Constants.EMPLOYEE_ROLE_ID;
import static com.pragma.powerup.usermicroservice.configuration.Constants.OWNER_ROLE;
import static com.pragma.powerup.usermicroservice.configuration.Constants.OWNER_ROLE_ID;

/**
 * Class to determine the role based on the logged-in user.
 */
public class RoleAuthentication {

    private RoleAuthentication() {}

    /**
     * Method to generate the user role.
     * @param roleRepository Repository to fetch the roles.
     */
    public static RoleEntity getRoleWithAuthentication(RoleRepository roleRepository) {
        //Gets the session context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //The role is extracted from the session
        String roleName = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);
        //Gets the id of the role by its name in session
        assert roleName != null;
        Long idRole = provideRoleByName(roleName);

        return roleRepository.findById(idRole).orElse(null);
    }

    public static Long provideRoleByName(String roleName) {
        Long idRole = 0L;
        if (roleName.equalsIgnoreCase(ADMIN_ROLE)) {
            idRole = OWNER_ROLE_ID;
        } else if (roleName.equalsIgnoreCase(OWNER_ROLE)) {
            idRole = EMPLOYEE_ROLE_ID;
        }
        return idRole;
    }
}
