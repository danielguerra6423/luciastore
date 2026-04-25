package storeapp.view;

import storeapp.domain.Admin;
import storeapp.domain.enums.AdminPermission;
import storeapp.services.AdminServiceImpl;
import storeapp.utils.ProductFormValidation;

import java.util.Optional;

public class AdminView {

    private final AdminServiceImpl adminService;

    public AdminView(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    public boolean loginAdmin() {
        String email = ProductFormValidation.validateString("Email admin:");
        String password = ProductFormValidation.validateString("Password admin:");

        Optional<Admin> admin = adminService.authenticate(email, password);
        if (admin.isPresent()) {
            System.out.println("✅ Bienvenido " + admin.get().getName() + " (" + admin.get().getRol() + ")");
            return true;
        }

        System.out.println("❎ Credenciales inválidas o cuenta inactiva.");
        return false;
    }

    public void logoutAdmin() {
        adminService.logout();
    }

    public boolean hasPermission(AdminPermission permission) {
        return adminService.hasPermission(permission);
    }

    public void createNewAdmin() {
        adminService.createAdmin(new Admin());
    }

    public void getAllAdmins() {
        System.out.println("\n=== Lista de Administradores ===");
        adminService.getAllAdmins().forEach(System.out::println);
    }

    public void getAdminById() {
        int id = ProductFormValidation.validateInt("Ingrese el ID del admin a buscar:");
        adminService.getAdminById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("❎ Admin no encontrado.")
        );
    }

    public void updateAdmin() {
        int id = ProductFormValidation.validateInt("Ingrese el ID del admin a actualizar:");
        Admin adminToUpdate = new Admin();
        adminToUpdate.setId(id);
        adminService.updateAdmin(adminToUpdate);
    }

    public void deleteAdmin() {
        int id = ProductFormValidation.validateInt("Ingrese el ID del admin a eliminar:");
        adminService.deleteAdmin(id);
    }

    public void getAllCustomers() {
        System.out.println("\n=== Lista de Clientes ===");
        adminService.getAllCustomers().forEach(System.out::println);
    }
}


