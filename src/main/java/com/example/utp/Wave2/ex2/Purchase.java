package com.example.utp.Wave2.ex2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

public class Purchase implements Serializable {
    private String prod;
    private String data;
    private Double price;
    private final PropertyChangeSupport pChange;
    private final VetoableChangeSupport vChange;

    public Purchase() {
        pChange = new PropertyChangeSupport(this);
        vChange = new VetoableChangeSupport(this);
    }

    public Purchase(String prod, String data, Double price) {
        this();
        this.prod = prod;
        this.data = data;
        this.price = price;
    }

    //Getters

    public String getProd() {
        return prod;
    }

    public String getData() {
        return data;
    }

    public Double getPrice() {
        return price;
    }

    //Setters

    public void setProd(String newProd) {
        prod = newProd;
    }

    public void setData(String newData) {
        String oldData = data;
        data = newData;
        pChange.firePropertyChange("data", oldData, newData);
    }

    public void setPrice(Double newPrice) throws PropertyVetoException {
        double oldPrice = price;
        vChange.fireVetoableChange("price", oldPrice, newPrice);
        price = newPrice;
        pChange.firePropertyChange("price", oldPrice, newPrice);
    }


    //Change listeners methods

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pChange.addPropertyChangeListener(l);
    }

    public void addVetoableChangeListner(VetoableChangeListener v) {
        vChange.addVetoableChangeListener(v);
    }

    @Override
    public String toString() {
        return "Purchase [prod=" + prod + ", data=" + data + ", price=" + price + "]";
    }

}
