package com.example.assignmentdatabase.models;

public class CustomerSpender {
    private double TotalInvoice;
    private String customerId;

    public CustomerSpender( String customerId, double TotalInvoice) {
        this.customerId = customerId;
        this.TotalInvoice = TotalInvoice;
    }

    public double getHighestSpenders() {
        return TotalInvoice;
    }

    public void setHighestSpenders(double TotalInvoice) {
        this.TotalInvoice = TotalInvoice;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
