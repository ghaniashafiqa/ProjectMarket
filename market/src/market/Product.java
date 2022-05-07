/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package market;

import java.util.UUID;

/**
 *
 * @author User
 */
public class Product {

    // Encapsulate fields or properties
    private String ID;
    private String productName;
    private int price;
    private String category;
    private int stock;
    
    private Database dbConnection = new Database();

    public Product(String productName, int price, String category, int stock) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.ID = UUID.randomUUID().toString();
    }

    public boolean addToCart(int amount, int user_id) {
        // Validate apakah stock mencukupi
        if (this.stock > 0 && amount <= this.stock) {
            this.stock -= amount;
            
            // Add product to cart
            dbConnection.cudQuery(String.format("INSERT INTO carts VALUES (0, %s, %s, %s);", this.ID, amount, user_id));
            
            // Decrement product stock in database
            dbConnection.cudQuery(String.format("UPDATE products SET stock = stock - %s WHERE id = %s", amount, this.ID));
            return true;
        }
        
        return false;
    }

    // Getters and setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
