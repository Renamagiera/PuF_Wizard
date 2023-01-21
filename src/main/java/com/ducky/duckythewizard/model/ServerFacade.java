package com.ducky.duckythewizard.model;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.net.http.HttpClient;

// used pattern: FACADE
public class ServerFacade {

    private String serverUrl = "http://localhost:8080/";

    public void sendHighScoreToServer(String name, int score) {
        // send high score to server

        String postBody = "{\r\n    \"score\":" + score + ",\r\n    \"name\":\"" + name + "\"\r\n}";

        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "addScore"))
                    .headers("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(postBody))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch(Exception e){
            System.out.println("ERROR: " + e.getMessage());
        }
        if (response != null && response.body().contains("Saved")){
            System.out.println("=> new HighScore saved");
        }
        else if (response != null && response.body().contains("ERROR")) {
            System.out.println("=> Something went wrong while trying to save high score");
        }
    }

    // not needed, therefore not implemented
    public List<HighScore> getAllHighScoresFromServer() {
        List<HighScore> highScores = new ArrayList<>();
        // get all high scores from server and store them in a hashmap
        return highScores;
    }

    public List<HighScore> getTopHighScoresFromServer(int limit) {
        // get top <limit> high scores from server and store them in an ArrayList of HighScore objects
        List<HighScore> topHighScores = new ArrayList<>();

    /*    // MOCK building high score objects from JSON data for testing purposes
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

    public List<HighScore> getAllHighScoresOfUser(String name) {
        // get all high scores of one user from server and store them in an ArrayList of HighScore objects
        List<HighScore> userHighScores = new ArrayList<>();

        String url = serverUrl + "allOfUser?name=" + name;

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
            int i = 0;
            // building high score objects from JSON data
            do {
                endIndex = responseString.indexOf("}}", startIndex) + 1;
                if (endIndex == 0){
                    break;
                }
                substring = responseString.substring(startIndex, endIndex);
                startIndex = endIndex + 1;
                userHighScores.add(i, getHighScoreFromString(substring, i + 1));
                i++;
            } while (endIndex != 0);
        }
        return userHighScores;
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
            return false;
        }
        return false;
    }
}
