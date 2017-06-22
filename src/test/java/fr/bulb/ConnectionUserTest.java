package fr.bulb;

import fr.bulb.api.UserService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 21/06/2017
 */
public class ConnectionUserTest  {

    @Test
    public void testConnectionNull(){
        UserService us = new UserService();
//        Assert.assertFalse(us.connection(null,null));
    }
}
