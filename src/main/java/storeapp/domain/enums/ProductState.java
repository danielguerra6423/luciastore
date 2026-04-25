package storeapp.domain.enums;

public enum ProductState {

    AVAILABLE (true),
    OUT_OF_STOCK (false),
    DISCONTINUED (false);
    private final boolean description;

    ProductState(boolean description){
        this.description = description;
    }

    public boolean getDescription() {
        return description;
    }
}
