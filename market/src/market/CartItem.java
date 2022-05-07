/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package market;

/**
 *
 * @author User
 */
public class CartItem extends Product {

    public int quantity;

    public CartItem(int quantity, String productName, int price, String category, int stock) {
        super(productName, price, category, stock);
        this.quantity = quantity;
    }

    public int calculateTotalPrice() {
        return this.quantity * this.getPrice();
    }

}
