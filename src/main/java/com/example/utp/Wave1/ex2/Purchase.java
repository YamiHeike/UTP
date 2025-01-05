package com.example.utp.Wave1.ex2;

public class Purchase {

    String clientId;
    String clientname;
    String product;
    double price;
    double qty;

    public Purchase(String ci, String cn, String pd, double pr, double qt) {
        clientId = ci;
        clientname = cn;
        product = pd;
        price = pr;
        qty = qt;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientname() {
        return clientname;
    }

    public double getTotalCost() {
        return price * qty;
    }

    @Override
    public String toString() {
        return clientId + ";" + clientname + ";" + product + ";" + price + ";" + qty;
    }

    public void printIncludeCost() {
        System.out.println(toString() + " (koszt: " + getTotalCost() + ")");
    }
}