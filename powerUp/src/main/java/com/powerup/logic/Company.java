package com.powerup.logic;

import java.awt.Color;
import java.util.ArrayList;

/**
 * An electricity company. <p /> Initially inactive, but is activated during the
 * course of the game. Once activated, owns tiles and sells shares. Can take
 * over other companies. Becomes inactivate again if taken over.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public final class Company implements Comparable<Company> {

   private final ArrayList<Share> shares;
   private final ArrayList<Tile> tiles;
   private final Color color;
   private final int initialValue;
   private final String name;
   private boolean active;
   private Tile headquarters;

   /**
    * Creates a new inactive company with the given name, price, and 25 shares.
    *
    * @param name The name of the company.
    * @param initialValue The initial share price of the company.
    * @param color The color of the company's tiles.
    */
   public Company(String name, int initialValue, Color color) {
      this.name = name;
      this.initialValue = initialValue;
      this.color = color;
      active = false;
      shares = new ArrayList<>();
      tiles = new ArrayList<>();
   }

   /**
    * Activates the company and sets its headquarters.
    *
    * @param tile The company's new headquarters.
    */
   public void activate(Tile tile) {
      headquarters = tile;
      active = true;
      createShares();
   }

   /**
    * Assigns a tile to the company. <p /> A tile must be on the board for it to
    * be assigned to a company.
    *
    * @param tile The tile to be added.
    */
   public void addTile(Tile tile) {
      if (tile.getLocation() == Tile.Location.BOARD) {
         tile.setOwner(this);
         tiles.add(tile);
      }
   }

   /**
    * Deactivates the company.
    */
   public void deactivate() {
      active = false;
   }

   public boolean getActive() {
      return active;
   }

   public Color getColor() {
      return this.color;
   }

   public Tile getHeadquarters() {
      return headquarters;
   }

   public int getSize() {
      return tiles.size();
   }

   public ArrayList<Share> getShares() {
      return shares;
   }

   public ArrayList<Tile> getTiles() {
      return tiles;
   }

   /**
    * Calculates the price of buying one share from the company. <p /> A
    * company's value grows by 50 dollars for each tile it owns in addition to
    * the minimum of two tiles.
    *
    * @return The price of buying one share from the company.
    */
   public int sellPrice() {
//        return initialValue + ((initialValue / 100) * (Math.max(0, tiles.size() - 2) * 10));
      return initialValue + (Math.max(0, tiles.size() - 2) * 50);

   }

   /**
    * Instructs the company to sell one of its shares, if it has any left.
    *
    * @return The share which is being sold.
    */
   public Share sellShare() {
      if (shares.isEmpty()) {
         return null;
      } else {
         Share purchase = shares.get(0);
         shares.remove(0);
         return purchase;
      }
   }

   /**
    * Makes one company take over another one, gaining all its tiles.
    *
    * @param company The company to be taken over.
    */
   public void takeOver(Company company) {
      for (Tile tile : company.getTiles()) {
         this.addTile(tile);
      }
      company.liquidate();
   }

   /* Private methods: no Javadoc */
   private void createShares() {
      for (int i = 0; i < 25; i++) {
         shares.add(new Share(this));
      }
   }

   private void liquidate() {
      tiles.clear();
      shares.clear();
      active = false;
   }

   /* Override methods: no Javadoc */
   @Override
   public int compareTo(Company o) {
      return o.getSize() - this.getSize();
   }

   @Override
   public String toString() {
      return name;
   }
}
