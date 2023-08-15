package models;

public class menu{
    private String menuID;
    private String menuName;
    private String menuType;
    private int menuPrice;
    private String menuImage;

    public menu(){
    }

    public menu(String menuID, String menuName, int menuPrice){
        this.menuID = menuID;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public menu(String menuID, String menuName, String menuType, int menuPrice, String menuImage){
        this.menuID = menuID;
        this.menuName = menuName;
        this.menuType = menuType;
        this.menuPrice = menuPrice;
        this.menuImage = menuImage;
    }

    public String getMenuID(){
        return menuID;
    }

    public String getMenuName(){
        return menuName;
    }

    public String getMenuType(){
        return menuType;
    }

    public int getMenuPrice(){
        return menuPrice;
    }

    public String getMenuImage(){
        return menuImage;
    }
}

