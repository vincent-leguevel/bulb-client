package fr.bulb.api;

import fr.bulb.bean.User;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.protocol.HTTP.USER_AGENT;

/**
 * @author Vincent Le Guevel (vincent.leguevel.sio@gmail.com)
 * @since 21/06/2017
 */
public class UserService {

    private final String SITE = "http://localhost:3000/";

    /**
     * Connexion d'un utilisateur via son pseudo ou son adresse mail (Requête POST)
     * @param user
     * @param password
     */
    public JSONObject connection(String user, String password) {


        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(SITE+"api/users/connection");


        post.addHeader("UserConnected-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("email", user));
        urlParameters.add(new BasicNameValuePair("pwd", password));

        return sendPost(client,post,urlParameters);
    }

    /**
     * Inscrit un utilisateur (Requête POST)
     * @param user
     * @return
     */
    public JSONObject inscription(User user){

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(SITE+"api/users/");


        post.addHeader("UserConnected-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("email", user.getEmail()));
        urlParameters.add(new BasicNameValuePair("confEmail", user.getConfEmail()));
        urlParameters.add(new BasicNameValuePair("pseudo", user.getPseudo()));
        urlParameters.add(new BasicNameValuePair("firstName", user.getFirstame()));
        urlParameters.add(new BasicNameValuePair("lastName", user.getPseudo()));
        urlParameters.add(new BasicNameValuePair("pwd", user.getPwd()));
        urlParameters.add(new BasicNameValuePair("confPwd", user.getConfPwd()));


        return sendPost(client,post,urlParameters);
    }

    /**
     * Execute une requête de type post
     * @param client
     * @param post
     * @param urlParameters
     * @return
     */
    private JSONObject sendPost(HttpClient client, HttpPost post,List<NameValuePair> urlParameters){

        HttpResponse response = null;
        JSONObject json = null;
        try {

            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            response = client.execute(post);

            String stringToParse = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONParser parser = new JSONParser();

            json = (JSONObject) parser.parse(stringToParse);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }

}
