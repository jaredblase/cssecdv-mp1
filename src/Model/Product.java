/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * @author beepxD
 */
public class Product {
    private int id;
    private String name;
    private int stock;
    private float price;

    public Product(String name, int stock, float price) throws Exception {
        this.setName(name);
        this.setStock(stock);
        this.setPrice(price);
    }

    public Product(int id, String name, int stock, float price) throws Exception {
        this(name, stock, price);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.length() == 0 || name.length() > 256) {
            throw new Exception("Product name must be between 1 to 255 characters.");
        }
        this.name = name.toUpperCase();
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) throws Exception {
        if (stock < 0) {
            throw new Exception("Stocks cannot be a negative number.");
        }
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) throws Exception {
        if (price < 0) {
            throw new Exception("Price must be a positive decimal number.");
        }
        this.price = price;
    }
}
