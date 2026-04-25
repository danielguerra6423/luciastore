package storeapp.userinterface;

import storeapp.domain.Admin;
import storeapp.domain.enums.AdminPermission;
import storeapp.repository.AdminRepository;
import storeapp.services.AdminServiceImpl;
import storeapp.utils.ProductFormValidation;
import storeapp.view.AdminView;
import storeapp.view.CategoryView;
import storeapp.view.CustomerView;
import storeapp.view.ProductView;

import java.util.Optional;

public class MenuApp {

    private final CustomerView customerView;
    private final AdminView adminView;
    private final ProductView productView;
    private final CategoryView categoryView;
    private final AdminServiceImpl adminService;
    private final AdminRepository adminRepository;

    public MenuApp(CustomerView customerView, AdminView adminView, ProductView productView, 
                   CategoryView categoryView, AdminServiceImpl adminService, AdminRepository adminRepository) {
        this.customerView = customerView;
        this.adminView = adminView;
        this.productView = productView;
        this.categoryView = categoryView;
        this.adminService = adminService;
        this.adminRepository = adminRepository;
    }

    public void showMainMenu() {
        System.out.println("--- BIENVENIDO A LA TIENDA ONLINE ---");
        int init = ProductFormValidation.validateInt("Presione 1 para iniciar (0 para salir):");

        while (init != 0) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Registrar Usuario Cliente");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Crear Administrador (Requiere credenciales de admin)");
            System.out.println("4. Salir");

            int option = ProductFormValidation.validateInt("Seleccione una opción:");

            switch (option) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    iniciarSesion();
                    break;
                case 3:
                    crearAdminDesdeMenuPrincipal();
                    break;
                case 4:
                    System.out.println("Saliendo de la aplicación...");
                    init = 0;
                    break;
                default:
                    System.out.println("❎ Opción no válida.");
                    break;
            }
        }
    }

    private void crearAdminDesdeMenuPrincipal() {
        System.out.println("\n--- Crear Nuevo Administrador ---");
        System.out.println("⚠️ Esta operación requiere autenticación de un admin existente con permiso ADMIN_MANAGE.");

        String email = ProductFormValidation.validateString("Email del admin autenticado:");
        String password = ProductFormValidation.validateString("Password del admin autenticado:");

        Optional<Admin> authAdmin = adminService.authenticate(email, password);

        if (!authAdmin.isPresent()) {
            System.out.println("❎ Credenciales inválidas o cuenta inactiva.");
            return;
        }

        if (!adminService.hasPermission(AdminPermission.ADMIN_MANAGE)) {
            System.out.println("❎ No tiene permiso para crear administradores.");
            adminService.logout();
            return;
        }

        System.out.println("✅ Autenticación exitosa. Procediendo a crear nuevo admin...");
        adminView.createNewAdmin();
        adminService.logout();
        System.out.println("✅ Sesión cerrada.");
    }

    private void registrarUsuario() {
        System.out.println("\n--- Registro Cliente ---");
        customerView.createCustomer();
        System.out.println("ℹ️ Las cuentas de administrador solo se gestionan desde el módulo de Admin.");
    }

    private void iniciarSesion() {
        System.out.println("\n--- Iniciar Sesión ---");
        System.out.println("1. Perfil Administrador");
        System.out.println("2. Perfil Cliente");

        int profileType = ProductFormValidation.validateInt("Seleccione perfil:");

        switch (profileType) {
            case 1:
                if (adminView.loginAdmin()) {
                    try {
                        showMenuAdmin();
                    } finally {
                        adminView.logoutAdmin();
                    }
                }
                break;
            case 2:
                showMenuCustomer();
                break;
            default:
                System.out.println("❎ Perfil no reconocido.");
                break;
        }
    }

    public void showMenuAdmin() {
        while (true) {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Gestionar Productos");
            System.out.println("2. Gestionar Categorías");
            System.out.println("3. Gestionar Clientes");
            System.out.println("4. Gestionar Administradores");
            System.out.println("5. Cerrar Sesión");

            int option = ProductFormValidation.validateInt("Seleccione:");

            switch (option) {
                case 1:
                    if (requirePermission(AdminPermission.PRODUCT_MANAGE)) {
                        gestionarProductos();
                    }
                    break;
                case 2:
                    if (requirePermission(AdminPermission.CATEGORY_MANAGE)) {
                        gestionarCategorias();
                    }
                    break;
                case 3:
                    if (requirePermission(AdminPermission.CUSTOMER_MANAGE)) {
                        customerMenuAdmin();
                    }
                    break;
                case 4:
                    if (requirePermission(AdminPermission.ADMIN_MANAGE)) {
                        adminMenuAdmin();
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("❎ Opción no válida.");
                    break;
            }
        }
    }

    private boolean requirePermission(AdminPermission permission) {
        if (!adminView.hasPermission(permission)) {
            System.out.println("❎ Acceso denegado. No tiene permiso: " + permission);
            return false;
        }
        return true;
    }

    private void gestionarProductos() {
        System.out.println("\n--- Módulo Productos (Admin) ---");
        System.out.println("1. Registrar\n2. Actualizar\n3. Eliminar\n4. Listar Todo");

        int opt = ProductFormValidation.validateInt("Seleccione:");

        switch (opt) {
            case 1:
                productView.createProduct();
                break;
            case 2:
                productView.updateProduct();
                break;
            case 3:
                productView.deleteProduct();
                break;
            case 4:
                productView.getAllProducts();
                break;
            default:
                System.out.println("❎ Opción inválida.");
                break;
        }
    }

    private void gestionarCategorias() {
        System.out.println("\n--- Módulo Categorías (Admin) ---");
        System.out.println("1. Registrar\n2. Eliminar\n3. Listar Todo");

        int opt = ProductFormValidation.validateInt("Seleccione:");

        switch (opt) {
            case 1:
                categoryView.createCategory();
                break;
            case 2:
                categoryView.deleteCategory();
                break;
            case 3:
                categoryView.getAllCategories();
                break;
            default:
                System.out.println("❎ Opción inválida.");
                break;
        }
    }

    private void adminMenuAdmin() {
        while (true) {
            System.out.println("\n--- Módulo Administradores (Admin) ---");
            System.out.println("1. Crear nuevo admin");
            System.out.println("2. Buscar admin por ID");
            System.out.println("3. Actualizar admin");
            System.out.println("4. Listar todos los admins");
            System.out.println("5. Eliminar admin");
            System.out.println("6. Volver al menú anterior");

            int option = ProductFormValidation.validateInt("Seleccione:");

            switch (option) {
                case 1:
                    adminView.createNewAdmin();
                    break;
                case 2:
                    adminView.getAdminById();
                    break;
                case 3:
                    adminView.updateAdmin();
                    break;
                case 4:
                    adminView.getAllAdmins();
                    break;
                case 5:
                    adminView.deleteAdmin();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("❎ Opción inválida.");
                    break;
            }
        }
    }

    public void showMenuCustomer() {
        while (true) {
            System.out.println("\n--- MENU CLIENTE ---");
            System.out.println("1. VER CATÁLOGO DE PRODUCTOS (Todos)");
            System.out.println("2. BUSCAR PRODUCTO POR ID");
            System.out.println("3. Ver mi Perfil");
            System.out.println("4. Modificar mi Perfil");
            System.out.println("5. Cerrar Sesión");

            int option = ProductFormValidation.validateInt("Seleccione una opción:");

            switch (option) {
                case 1:
                    System.out.println("\n--- Catálogo de Productos ---");
                    productView.getAllProducts();
                    break;
                case 2:
                    int id = ProductFormValidation.validateInt("Ingrese el ID del producto:");
                    productView.getProductById(id);
                    break;
                case 3:
                    int myId = ProductFormValidation.validateInt("Ingrese su ID para validar:");
                    customerView.getCustumerById(myId);
                    break;
                case 4:
                    customerView.updateCustumer();
                    break;
                case 5:
                    System.out.println("Cerrando sesión de cliente...");
                    return;
                default:
                    System.out.println("❎ Opción no válida.");
                    break;
            }
        }
    }

    public void customerMenuAdmin() {
        while (true) {
            System.out.println("\n--- Módulo Clientes (Admin) ---");
            System.out.println("1. Crear\n2. Buscar por ID\n3. Modificar\n4. Ver Todos\n5. Eliminar\n6. Volver");

            int option = ProductFormValidation.validateInt("Seleccione:");

            switch (option) {
                case 1:
                    customerView.createCustomer();
                    break;
                case 2:
                    customerView.getCustumerById(ProductFormValidation.validateInt("ID:"));
                    break;
                case 3:
                    customerView.updateCustumer();
                    break;
                case 4:
                    adminView.getAllCustomers();
                    break;
                case 5:
                    customerView.deleteCustomer();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("❎ Opción inválida.");
                    break;
            }
        }
    }
}

