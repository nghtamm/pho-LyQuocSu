package models;

public class human{
    protected String fullName;
    protected String gender;
    protected String phoneNumber;

    public human(){
    }

    public human(String fullName, String gender, String phoneNumber){
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public human(String fullName){
        this.fullName = fullName;
    }

    public String getFullName(){
        return fullName;
    }

    public String getGender(){
        return gender;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
}