package storeapp.services;

import storeapp.domain.Admin;
import storeapp.domain.Customer;
import storeapp.domain.enums.AdminPermission;
import storeapp.domain.enums.AdminRole;
import storeapp.domain.enums.AdminState;
import storeapp.repository.AdminRepository;
import storeapp.repository.CustomerRepository;
import storeapp.utils.AdminFormValidation;
import storeapp.utils.ProductFormValidation;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class AdminServiceImpl implements AdminService, CustumerAdminService {

    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final AuthContext authContext;

    public AdminServiceImpl(CustomerRepository customerRepository,
                            AdminRepository adminRepository,
                            AuthContext authContext) {
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
        this.authContext = authContext;
    }

    @Override
    public Admin createAdmin(Admin admin) {
        if (!hasPermission(AdminPermission.ADMIN_MANAGE)) {
            System.out.println("❎ Acceso denegado: no tiene permiso para crear administradores.");
            return null;
        }

        Admin newAdmin = new Admin();

        while (true) {
            int id = ProductFormValidation.validateInt("Ingrese el ID del administrador:");
            if (adminRepository.findAdminById(id) == null) {
                newAdmin.setId(id);
                break;
            }
            System.out.println("❎ Error: Ya existe un admin con ID " + id);
        }

        newAdmin.setName(ProductFormValidation.validateString("Ingrese el nombre:"));
        newAdmin.setLastName(ProductFormValidation.validateString("Ingrese el apellido:"));

        while (true) {
            String email = ProductFormValidation.validateString("Ingrese el email:");
            try {
                AdminFormValidation.validateAdminEmail(email);
                if (!adminRepository.existsByEmail(email)) {
                    newAdmin.setEmail(email);
                    break;
                }
                System.out.println("❎ Error: Email ya registrado.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            String password = ProductFormValidation.validateString("Ingrese la contraseña:");
            try {
                AdminFormValidation.validatePassword(password);
                newAdmin.setPassword(password);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        newAdmin.setStatus(AdminFormValidation.selectAdminState().getValue());

        AdminRole role = AdminFormValidation.selectAdminRole();
        newAdmin.setRol(role);

        Set<AdminPermission> permissions = AdminFormValidation.selectAdminPermissions(role);
        newAdmin.setPermissions(permissions);

        System.out.println("✅ Administrador creado exitosamente.");
        return adminRepository.saveAdmin(newAdmin);
    }

    @Override
    public Optional<Admin> getAdminById(int id) {
        return Optional.ofNullable(adminRepository.findAdminById(id));
    }

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return Optional.ofNullable(adminRepository.findAdminByEmail(email));
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAllAdmins();
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        if (!hasPermission(AdminPermission.ADMIN_MANAGE)) {
            System.out.println("❎ Acceso denegado: no tiene permiso para actualizar administradores.");
            return null;
        }

        Admin existing = adminRepository.findAdminById(admin.getId());
        if (existing == null) {
            System.out.println("❎ Admin no encontrado.");
            return null;
        }

        System.out.println("\n=== Actualizar Admin ===");
        System.out.println("1. Nombre");
        System.out.println("2. Apellido");
        System.out.println("3. Email");
        System.out.println("4. Contraseña");
        System.out.println("5. Rol");
        System.out.println("6. Permisos");
        System.out.println("7. Estado (Activo/Inactivo)");

        int option = ProductFormValidation.validateInt("Seleccione qué desea actualizar:");

        switch (option) {
            case 1:
                existing.setName(ProductFormValidation.validateString("Ingrese nuevo nombre:"));
                break;
            case 2:
                existing.setLastName(ProductFormValidation.validateString("Ingrese nuevo apellido:"));
                break;
            case 3:
                String newEmail = ProductFormValidation.validateString("Ingrese nuevo email:");
                try {
                    AdminFormValidation.validateAdminEmail(newEmail);
                    Admin byEmail = adminRepository.findAdminByEmail(newEmail);
                    if (byEmail == null || byEmail.getId() == existing.getId()) {
                        existing.setEmail(newEmail);
                    } else {
                        System.out.println("❎ El email ya está en uso.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                String newPassword = ProductFormValidation.validateString("Ingrese nueva contraseña:");
                try {
                    AdminFormValidation.validatePassword(newPassword);
                    existing.setPassword(newPassword);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 5:
                AdminRole newRole = AdminFormValidation.selectAdminRole();
                existing.setRol(newRole);
                existing.setPermissions(AdminFormValidation.selectAdminPermissions(newRole));
                break;
            case 6:
                existing.setPermissions(AdminFormValidation.selectAdminPermissions(existing.getRol()));
                break;
            case 7:
                existing.setStatus(AdminFormValidation.selectAdminState().getValue());
                break;
            default:
                System.out.println("❎ Opción no válida.");
                break;
        }

        System.out.println("✅ Admin actualizado exitosamente.");
        return adminRepository.updateAdmin(existing.getId());
    }

    @Override
    public void deleteAdmin(int id) {
        if (!hasPermission(AdminPermission.ADMIN_MANAGE)) {
            System.out.println("❎ Acceso denegado: no tiene permiso para eliminar administradores.");
            return;
        }

        Admin current = authContext.getCurrentAdmin().orElse(null);
        if (current == null) {
            System.out.println("❎ No hay sesión de administrador activa.");
            return;
        }

        Admin target = adminRepository.findAdminById(id);
        if (target == null) {
            System.out.println("❎ Admin no encontrado.");
            return;
        }

        if (current.getId() == id) {
            System.out.println("❎ No puede eliminar su propia cuenta mientras está autenticado.");
            return;
        }

        if (target.getRol() == AdminRole.SUPERADMIN
                && adminRepository.countActiveByRole(AdminRole.SUPERADMIN) <= 1) {
            System.out.println("❎ No puede eliminar el último SUPERADMIN activo.");
            return;
        }

        boolean deleted = adminRepository.deleteAdmin(id);
        if (deleted) {
            System.out.println("🗑️ Admin eliminado. Ha perdido acceso al sistema.");
        } else {
            System.out.println("❎ No se pudo eliminar el admin.");
        }
    }

    @Override
    public Optional<Admin> authenticate(String email, String password) {
        Admin admin = adminRepository.findAdminByEmail(email);
        if (admin == null) {
            return Optional.empty();
        }

        if (!admin.isStatus()) {
            return Optional.empty();
        }

        if (!admin.getPassword().equals(password)) {
            return Optional.empty();
        }

        authContext.login(admin);
        return Optional.of(admin);
    }

    @Override
    public boolean hasPermission(AdminPermission permission) {
        return authContext.hasPermission(permission);
    }

    @Override
    public Optional<Admin> getCurrentAdmin() {
        return authContext.getCurrentAdmin();
    }

    @Override
    public void logout() {
        authContext.logout();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAllCustomers();
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteCustomer(id);
    }
}




