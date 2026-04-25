package storeapp.domain.enums;

public enum AdminState {
    ACTIVO(true),
    INACTIVO(false);

    private final boolean value;

    AdminState(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}

