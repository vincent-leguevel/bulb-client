package fr.bulb.bean;

/**
 * @author Vincent Le Guével (vincent.leguevel.sio@gmail.com)
 * @since 22/06/2017
 */
public class UserConnected {

    private static int id;
    private static  String pseudo;
    private static String firstName;
    private static String lastName;
    private static String mail;


    public UserConnected(int _id, String _pseudo, String _firstName, String _lastName, String _mail) {
        id = _id;
        pseudo = _pseudo;
        firstName = _firstName;
        lastName = _lastName;
        mail = _mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String _pseudo) {
        pseudo = _pseudo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String _firstName) {
        firstName = _firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String _lastName) {
        lastName = _lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String _mail) {
        mail = _mail;
    }
}
