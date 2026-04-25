package storeapp.services;

import storeapp.domain.Admin;
import storeapp.domain.enums.AdminPermission;

import java.util.Optional;

public class AuthContext {

    private Admin currentAdmin;

    public void login(Admin admin) {
        this.currentAdmin = admin;
    }

    public void logout() {
        this.currentAdmin = null;
    }

    public Optional<Admin> getCurrentAdmin() {
        return Optional.ofNullable(currentAdmin);
    }

    public boolean isAuthenticated() {
        return currentAdmin != null;
    }

    public boolean hasPermission(AdminPermission permission) {
        return currentAdmin != null && currentAdmin.hasPermission(permission);
    }
}

