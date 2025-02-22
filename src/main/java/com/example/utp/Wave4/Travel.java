package com.example.utp.Wave4;

import java.util.Date;


public class Travel {
    private final int id;
    private String country;
    private Date startDate;
    private Date endDate;
    private String dest;
    private double price;
    private String currency;

    private Travel(Builder builder) {
        this.id = builder.id;
        this.country = builder.country;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.dest = builder.dest;
        this.price = builder.price;
        this.currency = builder.currency;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public static class Builder {
        private int id;
        private String country;
        private Date startDate;
        private Date endDate;
        private String dest;
        private double price;
        private String currency;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder toCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder from(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder to(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder withDestination(String dest) {
            this.dest = dest;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder inCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Travel build() {
            return new Travel(this);
        }

    }
}
