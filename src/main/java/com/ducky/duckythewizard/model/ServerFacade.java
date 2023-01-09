package com.ducky.duckythewizard.model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// used pattern: FACADE
public class ServerFacade {

    public void sendHighScoreToServer(String name, int score) {
        // send high score to sever

        // MOCK sending
        // System.out.println("SENDING HIGH SCORE (not really)");


        String urlParameters  = "name=" + name + "&score=" + score;
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        String request        = "http://localhost:8080/add";
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

        String url = "http://localhost:8080/top?limit=" + limit;
        StringBuffer response = new StringBuffer();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        //print result
        System.out.println(response);

        int startIndex = 0;
        int endIndex = 0;
        String substring = null;

        // building high score objects from JSON data
        for (int i = 0; i < limit; i++) {
            endIndex = response.indexOf("}", startIndex);
            if (endIndex == -1){
                break;
            }
            substring = response.substring(startIndex, endIndex);
            startIndex = endIndex + 1;
            topHighScores.add(i, getHighScoreFromString(substring, i + 1));
        }

        return topHighScores;
    }

    private HighScore getHighScoreFromString(String text, int rank){
        int nameStartIndex = text.indexOf("name") + 7;
        int nameEndIndex = text.indexOf("\"", nameStartIndex);
        int scoreStartIndex = text.indexOf("score") + 7;
        String name = text.substring(nameStartIndex, nameEndIndex);
        int score = Integer.parseInt(text.substring(scoreStartIndex));
        HighScore highScore = new HighScore(rank, name, score);
        return highScore;
    }
}
