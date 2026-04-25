package storeapp.domain.enums;

public enum ProductTypeEnum {
    AVAILABLE ("Disponible"),
    OUT_OF_STOCK ("Sin Stock"),
    DISCONTINUED ("Descontinuado");

    private final String description;

    ProductTypeEnum(String description){
        this.description = description;
    }
    public  String getDescription(){
        return description;
    }
}
