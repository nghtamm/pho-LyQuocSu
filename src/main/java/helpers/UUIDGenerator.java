package helpers;

import java.util.UUID;

public class UUIDGenerator{
    public static String shortenUUID(){
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("-", "").substring(0, 6).toUpperCase();
    }
}