package project1.repository.security;

import project1.model.Right;
import project1.model.Role;
import project1.model.User;

import java.util.List;

public interface RightsRolesRepository {
    void addRole(String role);

    void addRight(String right);

    Role findRoleByTitle(String role);

    Role findRoleById(Long roleId);

    Right findRightByTitle(String right);

    void addRolesToUser(User user, List<Role> roles);

    List<Role> findRolesForUser(Long userId);

    void addRoleRight(Long roleId, Long rightId);
}