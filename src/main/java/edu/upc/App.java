package edu.upc;

import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class App 
{
    public static final String API_URL = "https://api.github.com";

    public static class User {
        public final String login;
        public final int id;

        public User(String login, int id){
            this.login = login;
            this.id = id;
        }
    }

    public interface GitHub {
        @GET("users/garretaserra/followers")
        Call<List<User>> users();
    }


    public static void main( String[] args ) throws IOException
    {

        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<User>> call = github.users();

        // Fetch and print a list of the contributors to the library.
        List<User> followers = call.execute().body();
        for (User u : followers) {
            System.out.println("Follower: "+u.login+" with id: "+u.id);
        }
    }
}
