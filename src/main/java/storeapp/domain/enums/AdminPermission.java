package storeapp.domain.enums;
// Este enum define los diferentes permisos que un administrador puede tener en el sistema. Cada permiso representa una acción específica que un administrador puede realizar, como gestionar productos, categorías o clientes. Al asignar estos permisos a los administradores, se puede controlar de manera granular qué acciones pueden llevar a cabo dentro de la aplicación, mejorando así la seguridad y la gestión de roles.
public enum AdminPermission {
    ADMIN_MANAGE,
    CUSTOMER_MANAGE,
    PRODUCT_MANAGE,
    CATEGORY_MANAGE,
    PRODUCT_READ,
    CATEGORY_READ
}

