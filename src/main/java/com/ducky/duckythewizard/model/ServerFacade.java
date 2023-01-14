package com.ducky.duckythewizard.model;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.net.http.HttpClient;

// used pattern: FACADE
public class ServerFacade {

    private String serverUrl = "http://localhost:8080/";

    public void sendHighScoreToServer(String name, int score) {
        // send high score to sever

        // MOCK sending
        // System.out.println("SENDING HIGH SCORE (not really)");


        String urlParameters  = "name=" + name + "&score=" + score;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        String request        = "http://localhost:8080/addScore";
        URL url            = null;
        HttpURLConnection conn= null;
        try {
            url = new URL( request );
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        try {
            conn.setRequestMethod( "POST" );
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        conn.setUseCaches( false );
        try( OutputStream wr = conn.getOutputStream()) {
            wr.write( postData );
            conn.getInputStream();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }


    }

    // not needed, therefore not implemented
    public HashMap<String, Integer> getAllHighScoresFromServer() {
        HashMap<String, Integer> highScores = new HashMap<String, Integer>();

        // get all high scores from server and store them in a hashmap
        return highScores;
    }

    public List<HighScore> getTopHighScoresFromServer(int limit) {
        // get top <number> high scores from server and store them in an ArrayList of HighScore objects
        List<HighScore> topHighScores = new ArrayList<>();

    /*    // MOCK building high score objects from JSON data
        for (int i = 0; i < limit; i++) {
            // test data
            topHighScores.add(i, new HighScore(i + 1, "Name" + (i+1), 100 - (i*10)));
        }*/

        String url = serverUrl + "top?limit=" + limit;

        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch(Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        if (response != null){
            String responseString = response.body();
            int startIndex = 0;
            int endIndex = 0;
            String substring = null;

            // building high score objects from JSON data
            for (int i = 0; i < limit; i++) {
                endIndex = responseString.indexOf("}}", startIndex) + 1;
                if (endIndex == 0){
                    break;
                }
                substring = responseString.substring(startIndex, endIndex);
                startIndex = endIndex + 1;
                topHighScores.add(i, getHighScoreFromString(substring, i + 1));
            }
        }
        return topHighScores;
    }

    private HighScore getHighScoreFromString(String text, int rank){
        int nameStartIndex = text.indexOf("name") + 7;
        int nameEndIndex = text.indexOf("\"", nameStartIndex);
        int scoreStartIndex = text.indexOf("score") + 7;
        int scoreEndIndex = text.indexOf(",", scoreStartIndex);
        String name = text.substring(nameStartIndex, nameEndIndex);
        int score = Integer.parseInt(text.substring(scoreStartIndex, scoreEndIndex));
        HighScore highScore = new HighScore(rank, name, score);
        return highScore;
    }

    // adds new user, returns true if successful, false if not
    public boolean addNewUser(String name) {
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "addUser"))
                    .POST(HttpRequest.BodyPublishers.ofString(name))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
        if (response != null && response.body().contains("Saved")){
            return true;
        }
        else if (response != null && response.body().contains("ERROR")) {
            return false;
        }
        return false;
    }

    public boolean getIfUserExists(String name){
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "getUser?name="+ name))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
        if (response != null && response.body().contains("true")){
            return true;
        }
        else if (response != null && response.body().contains("false")) {
            System.out.println("==> ERROR");
            return false;
        }
        return false;
    }
}
