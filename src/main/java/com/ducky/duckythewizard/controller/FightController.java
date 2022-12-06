package com.ducky.duckythewizard.controller;

import com.ducky.duckythewizard.model.DuckySprite;
import com.ducky.duckythewizard.model.Stone;

import java.util.Scanner;

public class FightController extends Controller {

    GameController myGameController;
    public FightController(GameController gameController){
        myGameController = gameController;
    }

    public String startFight(Stone stone, DuckySprite player) { // TODO change DuckySprite for Player
        System.out.println("--> starting fight");
        stone.setInactive();
        // Fight fight = new Fight
        Scanner s = new Scanner(System.in);
        System.out.print("Press w to win, something else to lose: ");
        String input = s.next();
        if(input.equals("w")) {
            return "win";
        }
        return "lose";
    }
}
