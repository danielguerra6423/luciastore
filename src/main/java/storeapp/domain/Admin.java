package storeapp.domain;

import storeapp.domain.enums.AdminPermission;
import storeapp.domain.enums.AdminRole;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
// La clase Admin representa a un administrador del sistema, que es un tipo de persona con permisos especiales para gestionar diferentes aspectos de la aplicación. Esta clase hereda de Person, lo que significa que tiene todas las propiedades y métodos de una persona común, pero también incluye atributos específicos para los administradores, como su rol y los permisos que tienen asignados. El uso de EnumSet para los permisos permite una gestión eficiente y clara de los permisos disponibles para cada administrador.
public class Admin extends Person {

    private AdminRole rol;
    private final EnumSet<AdminPermission> permissions = EnumSet.noneOf(AdminPermission.class);

    public Admin(int id, String name, String lastName, String email, String password, boolean status,
                 AdminRole rol, Set<AdminPermission> permissions) {
        super(id, name, lastName, email, password, status);
        this.rol = rol;
        setPermissions(permissions);
    }

    public Admin() {
        super();
        this.rol = AdminRole.OPERADOR;
    }

    public Admin(String email) {
        super(email);
        this.rol = AdminRole.OPERADOR;
    }

    public AdminRole getRol() {
        return rol;
    }

    public void setRol(AdminRole rol) {
        this.rol = rol;
    }

    public Set<AdminPermission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public void setPermissions(Set<AdminPermission> newPermissions) {
        permissions.clear();
        if (newPermissions != null) {
            permissions.addAll(newPermissions);
        }
    }

    public boolean hasPermission(AdminPermission permission) {
        return permissions.contains(permission);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", rol=" + rol +
                ", permissions=" + permissions +
                '}';
    }
}

