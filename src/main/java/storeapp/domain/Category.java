package storeapp.domain;

public class Category {

    private int idCategory;
    private String description;
    private boolean state;

    public Category(int idCategory, String description, boolean state) {
        this.idCategory = idCategory;
        this.description = description;
        this.state = state;
    }

    public Category() {
    }

    // Método fundamental para que el producto muestre texto en lugar de direcciones de memoria
    @Override
    public String toString() {
        return description + " (ID: " + idCategory + ")";
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}