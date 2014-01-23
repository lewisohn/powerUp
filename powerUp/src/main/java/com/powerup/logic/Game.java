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

    public Game() {
        plots = new ArrayList<>();
        createPlots();
        createPlayers();
        createCompanies();
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
//        for (int i = 0; i < 4; i++) {
//            System.out.print("Enter a name for player " + (i + 1) + ": ");
//            players[i] = new Player(scanner.nextLine());
//        }
        for (int i = 0; i < 4; i++) {
            players[i] = new Player("Player " + (i + 1));
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

    public Plot getPlot(int x, int y) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            throw new IllegalArgumentException();
        } else {
            return plots.get(x).get(y);
        }
    }

    public Player[] getPlayers() {
        return players;
    }

    public ArrayList<Plot> getNeighbours(Plot plot) {
        ArrayList<Plot> neighbours = new ArrayList<>();
        if (plot.getX() > 0) {
            if (plots.get(plot.getX() - 1).get(plot.getY()).played() == true) {
                neighbours.add(plots.get(plot.getX() - 1).get(plot.getY()));
            }
        }
        if (plot.getX() < 9) {
            if (plots.get(plot.getX() + 1).get(plot.getY()).played() == true) {
                neighbours.add(plots.get(plot.getX() + 1).get(plot.getY()));
            }
        }
        if (plot.getY() > 0) {
            if (plots.get(plot.getX()).get(plot.getY() - 1).played() == true) {
                neighbours.add(plots.get(plot.getX()).get(plot.getY() - 1));
            }
        }
        if (plot.getY() < 9) {
            if (plots.get(plot.getY()).get(plot.getY() + 1).played() == true) {
                neighbours.add(plots.get(plot.getX()).get(plot.getY() + 1));
            }
        }
        return neighbours;
    }
}
