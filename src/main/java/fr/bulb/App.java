package fr.bulb;

import fr.bulb.api.UserService;
import fr.bulb.bean.User;


public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        UserService us = new UserService();
        System.out.println(us.connection("test@gmail.com","azerty").toJSONString());
        System.out.println(us.inscription(new User("cvJava","Vincent","Le Guevel","cvtatitoto@gmail.com","cvtatitoto@gmail.com","0000","0000")).toJSONString());


    }
}
