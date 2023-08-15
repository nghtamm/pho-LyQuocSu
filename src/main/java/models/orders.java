package models;

public class orders extends menu{
    private int ordQuantity;

    public orders(String menuID, String menuName, int menuPrice, int ordQuantity){
        super(menuID, menuName, menuPrice);
        this.ordQuantity = ordQuantity;
    }

    public int getOrdQuantity(){
        return ordQuantity;
    }
}
