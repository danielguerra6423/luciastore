package storeapp.services;

import storeapp.domain.Admin;
import storeapp.domain.enums.AdminPermission;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Admin createAdmin(Admin admin);

    Optional<Admin> getAdminById(int id);

    Optional<Admin> getAdminByEmail(String email);

    List<Admin> getAllAdmins();

    Admin updateAdmin(Admin admin);

    void deleteAdmin(int id);

    Optional<Admin> authenticate(String email, String password);

    boolean hasPermission(AdminPermission permission);

    Optional<Admin> getCurrentAdmin();

    void logout();
}


