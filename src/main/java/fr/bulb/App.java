package fr.bulb;

import fr.bulb.view.Client;

public class App
{
    public static void main( String[] args )
    {
//        UserService userService = new UserService();
//        JSONObject json = userService.connection("vincent","azerty");
//        System.out.println(json.toString());
        new Client().createView();
    }
}