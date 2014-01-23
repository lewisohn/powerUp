package com.powerup.logic;

public class Game {

    // pelilauta
    private final Board board;
    // pelaajat
    private Player[] players;
    // sähköyritykset
    private Company eclipse;
    private Company maniac;
    private Company king;
    private Company guzzler;
    private Company superslick;
    private Company whoops;

    public Game() {
        board = new Board();
        players = new Player[4];
        createPlayers();
        createCompanies();
    }

    private void createPlayers() {
        for (int i = 0; i < 4; i++) {
            players[i] = new Player("Player " + (i + 1));
        }
    }

    private void createCompanies() {
        eclipse = new Company("Eclipse Solar", 200, board);
        maniac = new Company("Maniac Timber", 200, board);
        king = new Company("King Coal", 300, board);
        guzzler = new Company("Guzzler Gas", 300, board);
        superslick = new Company("Superslick Oil", 400, board);
        whoops = new Company("Whoops Uranium", 400, board);
    }

    public Board getBoard() {
        return board;
    }

    // turhahko metodi, mutta käytetään JUnit-testeissä
    public Company getEclipse() {
        return eclipse;
    }

}
