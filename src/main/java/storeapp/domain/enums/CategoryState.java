package storeapp.domain.enums;

public enum CategoryState {
    ACTIVE(true),
    INACTIVE(false);

    private final boolean value;

    CategoryState(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}

