package com.ducky.duckythewizard.controller.TO_DELETE;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
						
			Pane root = FXMLLoader.load(getClass().getResource("mockup_pane.fxml"));
			
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("Ducky & the Wizard Stones - Main.java");
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void testMethod4(){
		System.out.println("Hello (Britta's test in Main.java)");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
