package storeapp.utils;

import storeapp.domain.enums.AdminPermission;
import storeapp.domain.enums.AdminRole;

import java.util.EnumSet;
import java.util.Set;

public class AdminFormValidation {

    private AdminFormValidation() {
    }

    public static AdminRole selectAdminRole() {
        System.out.println("\n=== Seleccionar Rol ===");
        System.out.println("1. SUPERADMIN (Acceso Total)");
        System.out.println("2. MODERADOR (Acceso Operativo)");
        System.out.println("3. OPERADOR (Solo Lectura)");

        int option = ProductFormValidation.validateInt("Ingrese la opción:");

        switch (option) {
            case 1:
                return AdminRole.SUPERADMIN;
            case 2:
                return AdminRole.MODERADOR;
            case 3:
                return AdminRole.OPERADOR;
            default:
                System.out.println("❎ Opción no válida. Se asignará OPERADOR.");
                return AdminRole.OPERADOR;
        }
    }

    public static Set<AdminPermission> selectAdminPermissions(AdminRole role) {
        switch (role) {
            case SUPERADMIN:
                return EnumSet.allOf(AdminPermission.class);

            case MODERADOR:
                System.out.println("\n=== Permisos para MODERADOR ===");
                System.out.println("1. Gestión de productos y categorías");
                System.out.println("2. Gestión de productos, categorías y clientes");
                System.out.println("3. Solo lectura de catálogo");

                int modOption = ProductFormValidation.validateInt("Seleccione:");
                switch (modOption) {
                    case 1:
                        return EnumSet.of(
                                AdminPermission.PRODUCT_MANAGE,
                                AdminPermission.CATEGORY_MANAGE,
                                AdminPermission.PRODUCT_READ,
                                AdminPermission.CATEGORY_READ
                        );
                    case 2:
                        return EnumSet.of(
                                AdminPermission.PRODUCT_MANAGE,
                                AdminPermission.CATEGORY_MANAGE,
                                AdminPermission.CUSTOMER_MANAGE,
                                AdminPermission.PRODUCT_READ,
                                AdminPermission.CATEGORY_READ
                        );
                    default:
                        return EnumSet.of(
                                AdminPermission.PRODUCT_READ,
                                AdminPermission.CATEGORY_READ
                        );
                }

            case OPERADOR:
            default:
                return EnumSet.of(
                        AdminPermission.PRODUCT_READ,
                        AdminPermission.CATEGORY_READ
                );
        }
    }

    public static String validateAdminEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("❎ Email inválido. Use formato: usuario@dominio.com");
        }
        return email;
    }

    public static String validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("❎ La contraseña debe tener al menos 6 caracteres");
        }
        return password;
    }

    public static AdminState selectAdminState() {
        System.out.println("\n=== Seleccionar Estado ===");
        System.out.println("1. ✅ Activo");
        System.out.println("2. ❌ Inactivo");

        int option = ProductFormValidation.validateInt("Ingrese la opción:");

        switch (option) {
            case 1:
                return AdminState.ACTIVO;
            case 2:
                return AdminState.INACTIVO;
            default:
                System.out.println("❎ Opción no válida. Se asignará ACTIVO.");
                return AdminState.ACTIVO;
        }
    }
}


