package fr.bulb;

import fr.bulb.view.Client;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Main" );
        new Client().createView();
    }
}