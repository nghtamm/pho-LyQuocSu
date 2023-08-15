package models;

public class customer extends human{
    private String customerID;

    public customer(){
    }

    public customer(String fullName, String gender, String phoneNumber, String customerID){
        super(fullName, gender, phoneNumber);
        this.customerID = customerID;
    }

    public customer(String fullName, String customerID){
        super(fullName);
        this.customerID = customerID;
    }

    public String getCustomerID(){
        return customerID;
    }
}
