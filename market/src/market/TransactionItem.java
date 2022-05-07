package market;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import market.CartItem;

/**
 *
 * @author User
 */
public class TransactionItem extends CartItem {

    public TransactionItem(int quantity, String productName, int price, String category, int stock) {
        super(quantity, productName, price, category, stock);
    }

    @Override
    public int calculateTotalPrice() {
        final int itemPrice = this.quantity * this.getPrice();
        final int tax = (int) Math.round(itemPrice / 10);
        
        return itemPrice + tax;
    }
    
}
