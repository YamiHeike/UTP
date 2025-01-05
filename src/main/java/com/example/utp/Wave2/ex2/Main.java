package com.example.utp.Wave2.ex2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class Main {
    public static void main(String[] args) {

        Purchase purch = new Purchase("komputer", "nie ma promocji", 3000.00);
        System.out.println(purch);

        // Exercise requirement: the below code must compile and produce expected results, changes can be only made in this part of the class

        PropertyChangeListener pcl = evt -> System.out.println("Change value of: " + evt.getPropertyName() +
                " from: " + evt.getOldValue() +
                " to: " + evt.getNewValue());
        VetoableChangeListener priceVeto = evt -> {
            if (!evt.getPropertyName().equals("price")) {
                System.out.println("Price VetoableChangeListener assigned to a wrong property");
            }
            Double np = Double.valueOf((Double) evt.getNewValue());
            if (np < 1000) {
                throw new PropertyVetoException("Price change to: " + np + " not allowed", evt);
            }
        };

        purch.addPropertyChangeListener(pcl);
        purch.addVetoableChangeListner(priceVeto);

        // ----------------------------------

        try {
            purch.setData("w promocji");
            purch.setPrice(2000.00);
            System.out.println(purch);

            purch.setPrice(500.00);

        } catch (PropertyVetoException exc) {
            System.out.println(exc.getMessage());
        }
        System.out.println(purch);

    }
}
