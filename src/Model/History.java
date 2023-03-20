/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author beepxD
 */
public class History {
    private int id;
    private final String username;
    private final String name;
    private final int quantity;
    private final float price;
    private Timestamp timestamp;
    private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

    public History(String username, Product product, int quantity) {
        this.username = username;
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = quantity;
        this.timestamp = new Timestamp(new Date().getTime());
    }

    public History(int id, String username, String name, int quantity, float price, String timestamp) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        try {
            this.timestamp = new Timestamp(dateformat.parse(timestamp).getTime());
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public float getPrice() {
        return price;
    }
}
