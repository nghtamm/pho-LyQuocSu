package models;

import java.util.Date;

public class receipt extends customer{
    private String receiptID;
    private Date date;
    private int totalPrice;
    private String empUsername;

    public receipt(String fullName, String customerID, String receiptID, Date date, int totalPrice, String empUsername){
        super(fullName, customerID);
        this.receiptID = receiptID;
        this.date = date;
        this.totalPrice = totalPrice;
        this.empUsername = empUsername;
    }

    public String getReceiptID(){
        return receiptID;
    }

    public Date getDate(){
        return date;
    }

    public int getTotalPrice(){
        return totalPrice;
    }

    public String getEmpUsername(){
        return empUsername;
    }
}
