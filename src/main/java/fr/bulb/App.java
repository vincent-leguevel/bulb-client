package fr.bulb;

import fr.bulb.api.UserService;
import fr.bulb.view.Client;
import org.json.simple.JSONObject;

public class App
{
    public static void main( String[] args )
    {
        UserService userService = new UserService();
        JSONObject json = userService.connection("vincent","azerty");
        System.out.println(json.toString());
        new Client().createView();
    }
}