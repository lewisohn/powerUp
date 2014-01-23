package com.powerup.logic;

import java.util.ArrayList;

public class Board {

    // kaksiulotteinen taulukko, johon laitetaan pelin tontit
    private final ArrayList<ArrayList<Plot>> plots;

    public Board() {
        plots = new ArrayList<>();
        createPlots();
    }

    private void createPlots() {
        for (int i = 0; i < 10; i++) {
            plots.add(new ArrayList<Plot>());
            for (int j = 0; j < 10; j++) {
                plots.get(i).add(new Plot(i, j));
            }
        }
    }

    public Plot getPlot(int x, int y) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            throw new IllegalArgumentException();
        } else {
            return plots.get(x).get(y);
        }
    }

    public boolean playPlot(int x, int y) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            return false;
        } else {
            getPlot(x, y).play();
            return true;
        }
    }

    public boolean assignPlot(int x, int y, Company company) {
        if ((x < 0) || (x > 9) || (y < 0) || (y > 9)) {
            return false;
        } else if (!getPlot(x, y).played()) {
            return false;
        } else {
            getPlot(x, y).setOwner(company);
            return true;
        }
    }

    public ArrayList<Plot> getNeighbours(Plot plot) {
        int x = plot.getX();
        int y = plot.getY();
        ArrayList<Plot> neighbours = new ArrayList<>();
        if (plot.getX() > 0) {
            if (getPlot(x - 1, y).played() == true) {
                neighbours.add(getPlot(x - 1, y));
            }
        }
        if (plot.getX() < 9) {
            if (getPlot(x + 1, y).played() == true) {
                neighbours.add(getPlot(x + 1, y));
            }
        }
        if (plot.getY() > 0) {
            if (getPlot(x, y - 1).played() == true) {
                neighbours.add(getPlot(x, y - 1));
            }
        }
        if (plot.getY() < 9) {
            if (getPlot(x, y + 1).played() == true) {
                neighbours.add(getPlot(x, y + 1));
            }
        }
        return neighbours;
    }

    public ArrayList<Plot> getCompanyPlots(Company company) {
        ArrayList<Plot> companyPlots = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Company x = getPlot(i, j).getOwner();
                if ((x != null) && (x.equals(company))) {
                    companyPlots.add(getPlot(i, j));
                }
            }
        }
        return companyPlots;
    }

}
