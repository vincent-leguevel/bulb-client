package fr.bulb.api;

import fr.bulb.bean.User;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 21/06/2017
 */
public class UserServiceTest {


    private UserService us;
    private User userNotExist;
    private User userExist;

    @Before
    public void initialize() {
        us = new UserService();
        userExist = new User();
        userNotExist = new User();

        //Jeu de test à changer pour testInscriptionWithUserNotAlreadyCreated
        userNotExist.setPseudo("testUnitadfire");
        userNotExist.setEmail("testUnitaire@gmaildf.com");
        userNotExist.setConfEmail("testUnitaire@gmaildf.com");
        userNotExist.setPwd("azerty");
        userNotExist.setConfPwd("azerty");

        userExist.setPseudo("testtest");
        userExist.setEmail("test@gmail.com");
        userExist.setConfEmail("test@gmail.com");
        userExist.setPwd("azerty");
        userExist.setConfPwd("azerty");
    }

    @Test
    public void testConnectionWrongPwdOrMail() {

        JSONObject json1 = us.connection("test@gmail.com","gelrkgrlsfkzlkrf");
        JSONObject json2 = us.connection("testtest@gmail.com","azerty");

        Assert.assertEquals("BAD PASSWORD OR MAIL",json1.get("errorInfo"));
        Assert.assertEquals(true,json1.get("error"));

        Assert.assertEquals("BAD PASSWORD OR MAIL",json2.get("errorInfo"));
        Assert.assertEquals(true,json2.get("error"));
    }

    @Test
    public void testConnectionNullPwdORMail() {

        JSONObject json1 = us.connection(null,"azerty");
        JSONObject json2 = us.connection("test@gmail.com",null);

        Assert.assertEquals("INVALID INFORMATIONS",json1.get("errorInfo"));
        Assert.assertEquals(true,json1.get("error"));

        Assert.assertEquals("INVALID INFORMATIONS",json2.get("errorInfo"));
        Assert.assertEquals(true,json2.get("error"));
    }

    @Test
    public void testConnectionWithGoodId() {

        JSONObject json1 = us.connection("test@gmail.com","azerty");
        Assert.assertEquals(false,json1.get("error"));
    }

    @Test
    public void testInscriptionWithUserNotAlreadyCreated() {

        JSONObject json1 = us.inscription(userNotExist);
        Assert.assertEquals(new Long(201),json1.get("errorCode"));

    }

    @Test
    public void testInscriptionWithUserAlreadyCreated() {

        JSONObject json1 = us.inscription(userExist);
        Assert.assertEquals(new Long(409),json1.get("errorCode"));
        Assert.assertEquals("MAIL OR PSEUDO ALREADY USED",json1.get("errorInfo"));

    }

    @Test
    public void testInscriptionwithMissingInformation() {

        JSONObject json1 = us.inscription(new User());
        Assert.assertEquals(new Long(400),json1.get("errorCode"));
        Assert.assertEquals("INVALID INFORMATIONS",json1.get("errorInfo"));

        JSONObject json2 = us.inscription(new User("user",null,null,"test@gmail.com","test@gmail.com","azerty","azerty"));
        Assert.assertNotEquals("INVALID INFORMATIONS",json2.get("errorInfo"));

    }

}
