package storeapp.domain;

import java.util.List;

public class Product {

    private int idProduct;
    private String description;
    private double price;
    private int stock;
    private boolean state;
    private Category category; // Agregué el modificador private por buena práctica

    // CORRECCIÓN EN EL CONSTRUCTOR:
    // Debes incluir 'Category category' en los parámetros para poder asignarlo
    public Product(int idProduct, String description, double price, int stock, boolean state, Category category) {
        this.idProduct = idProduct;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.state = state;
        this.category = category; // Ahora se asigna correctamente el parámetro
    }

    public Product() {
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {

        String catInfo = (category != null) ? category.toString() : "Sin categoría";

        return "📦 PRODUCTO [" + idProduct + "]\n" +
                "   Descripción: " + description + "\n" +
                "   Precio: $" + price + "\n" +
                "   Stock: " + stock + "\n" +
                "   Estado: " + (state ? "Activo" : "Inactivo") + "\n" +
                "   Categoría: " + catInfo + "\n" +
                "---------------------------";
    }


}