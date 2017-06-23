package fr.bulb.bean;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 22/06/2017
 * Cette classe sert à inscrire de nouveaux utilisateurs
 */
public class User {

    private String pseudo;
    private String firstame;
    private String lastName;
    private String email;
    private String confEmail;
    private String pwd;
    private String confPwd;

    public User(String pseudo, String firstame, String lastName, String email, String confEmail, String pwd, String confPwd) {
        this.pseudo = pseudo;
        this.firstame = firstame;
        this.lastName = lastName;
        this.email = email;
        this.confEmail = confEmail;
        this.pwd = pwd;
        this.confPwd = confPwd;
    }

    public User() {

    }


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstame() {
        return firstame;
    }

    public void setFirstame(String firstame) {
        this.firstame = firstame;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfEmail() {
        return confEmail;
    }

    public void setConfEmail(String confEmail) {
        this.confEmail = confEmail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getConfPwd() {
        return confPwd;
    }

    public void setConfPwd(String confPwd) {
        this.confPwd = confPwd;
    }
}
