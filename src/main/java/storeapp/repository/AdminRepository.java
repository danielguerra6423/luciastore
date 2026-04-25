package storeapp.repository;

import storeapp.domain.Admin;
import storeapp.domain.enums.AdminPermission;
import storeapp.domain.enums.AdminRole;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class AdminRepository {

    private final List<Admin> admins = new ArrayList<>(List.of(
            new Admin(
                    1, "Admin", "Principal", "admin@mail.com", "admin123", true,
                    AdminRole.SUPERADMIN, EnumSet.allOf(AdminPermission.class)
            ),
            new Admin(
                    2, "Moderador", "Sistema", "mod@mail.com", "mod123", true,
                    AdminRole.MODERADOR, EnumSet.of(
                    AdminPermission.PRODUCT_MANAGE,
                    AdminPermission.CATEGORY_MANAGE,
                    AdminPermission.CUSTOMER_MANAGE,
                    AdminPermission.PRODUCT_READ,
                    AdminPermission.CATEGORY_READ
            )
            )
    ));

    public Admin saveAdmin(Admin admin) {
        admins.add(admin);
        return admin;
    }

    public List<Admin> findAllAdmins() {
        return admins;
    }

    public Admin findAdminById(int id) {
        for (Admin admin : admins) {
            if (admin.getId() == id) {
                return admin;
            }
        }
        return null;
    }

    public Admin findAdminByEmail(String email) {
        for (Admin admin : admins) {
            if (admin.getEmail().equalsIgnoreCase(email)) {
                return admin;
            }
        }
        return null;
    }

    public boolean existsByEmail(String email) {
        return findAdminByEmail(email) != null;
    }

    public long countActiveByRole(AdminRole role) {
        return admins.stream()
                .filter(Admin::isStatus)
                .filter(a -> a.getRol() == role)
                .count();
    }

    public Admin updateAdmin(int id) {
        return findAdminById(id);
    }

    public boolean deleteAdmin(int id) {
        return admins.removeIf(admin -> admin.getId() == id);
    }
}


