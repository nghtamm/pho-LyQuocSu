package models;

import java.util.Date;

public class employee extends human{
    private String empID;
    private Date empBirth;
    private String empAddress;
    private String empPos;
    private String empImage;

    public employee(String fullName, String gender, String phoneNumber, String empID, Date empBirth, String empAddress, String empPos, String empImage){
        super(fullName, gender, phoneNumber);
        this.empID = empID;
        this.empBirth = empBirth;
        this.empAddress = empAddress;
        this.empPos = empPos;
        this.empImage = empImage;
    }

    public String getEmpID(){
        return empID;
    }

    public Date getEmpBirth(){
        return empBirth;
    }

    public String getEmpAddress(){
        return empAddress;
    }

    public String getEmpPos(){
        return empPos;
    }

    public String getEmpImage(){
        return empImage;
    }
}