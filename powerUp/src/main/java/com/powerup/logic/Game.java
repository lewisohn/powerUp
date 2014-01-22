package com.powerup.logic;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    // kaksiulotteinen taulukko, jossa säilytetään pelin tontit
    private ArrayList<ArrayList<Plot>> plots;
    // pelaajat
    private Player[] players = new Player[4];
    // sähköyritykset
    private Company eclipse;
    private Company maniac;
    private Company king;
    private Company guzzler;
    private Company superslick;
    private Company whoops;
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Integer> directions;

    public Game() {
        plots = new ArrayList<>();
        createPlots();
        createPlayers();
        createCompanies();
        directions = new ArrayList<>();
        directions.add(-1);
        directions.add(0);
        directions.add(1);
    }

    private void createPlots() {
        for (int i = 0; i < 10; i++) {
            plots.add(new ArrayList<Plot>());
            for (int j = 0; j < 10; j++) {
                plots.get(i).add(new Plot(i, j));
            }
        }
    }

    private void createPlayers() {
        for (int i = 0; i < 4; i++) {
            System.out.print("Enter a name for player " + (i + 1) + ": ");
            players[i] = new Player(scanner.nextLine());
        }
    }

    private void createCompanies() {
        eclipse = new Company("Eclipse Solar", 200);
        maniac = new Company("Maniac Timber", 200);
        king = new Company("King Coal", 300);
        guzzler = new Company("Guzzler Gas", 300);
        superslick = new Company("Superslick Oil", 400);
        whoops = new Company("Whoops Uranium", 400);
    }

    public ArrayList<Plot> getNeighbours(Plot plot) {
        ArrayList<Plot> neighbours = new ArrayList<>();
        for (int dx : directions) {
            for (int dy : directions) {
                if ((dx != 0) && (dy != 0)) {
                    // "kulmanaapuri" - ei vaikuta
                    continue;
                } else if ((dx == 0) && (dy == 0)) {
                    // oma paikka
                    continue;
                } else {
                    if (plots.get(plot.getX() + dx).get(plot.getY() + dy).played()) {
                        // should probably still check for outofbounds exception
                        neighbours.add(plots.get(plot.getX() + dx).get(plot.getY() + dy));
                    }
                }
            }
        }
        return neighbours;
    }
}
