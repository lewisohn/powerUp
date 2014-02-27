package com.powerup.logic;

/**
 * A tile on the game board.
 *
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public class Tile implements Comparable<Tile> {

   private final int x;
   private final int y;
   private Company owner;
   private Location location;

   /**
    * Creates a new tile with the given coordinates.
    *
    * @param x The x-coordinate of the tile.
    * @param y The y-coordinate of the tile.
    * @param board The game's board.
    */
   public Tile(int x, int y, Board board) {
      this.x = x;
      this.y = y;
      location = Location.NONE;
      owner = null;
   }
   /**
    * The three possible locations for a tile: BOARD - on the game's board; HAND
    * - in a player's hand; NONE - unassigned.
    */
   public enum Location {

      BOARD,
      HAND,
      NONE
   }

   public Location getLocation() {
      return location;
   }

   public Company getOwner() {
      return owner;
   }

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   /**
    * Checks if this tile is a neighbour of another tile.
    *
    * @param tile The potential neighbour tile to be checked.
    * @return True if the parameter tile is a neighbour, otherwise false.
    */
   public boolean isNextTo(Tile tile) {
      return ((this.x == tile.getX()) && (Math.abs(this.y - tile.getY()) == 1))
              || ((this.y == tile.getY()) && (Math.abs(this.x - tile.getX()) == 1));
   }

   public void setLocation(Location loc) {
      this.location = loc;
   }

   public void setOwner(Company company) {
      owner = company;
   }

   /* Override methods: no Javadoc */
   @Override
   public int compareTo(Tile o) {
      if (this.x < o.getX()) {
         return -1;
      } else if (this.x == o.getX()) {
         if (this.y < o.getY()) {
            return -1;
         }
         return 1;
      }
      return 1;
   }

   @Override
   public String toString() {
      String ref = new String();
      ref = ref + (char) (x + 65);
      ref = ref + y;
      return ref;
   }
}
