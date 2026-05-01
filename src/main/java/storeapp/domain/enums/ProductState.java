package storeapp.domain.enums;
// Enum para representar el estado de un producto
public enum ProductState {
// Cada estado tiene una descripción asociada
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
