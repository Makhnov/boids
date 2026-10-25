package systems;

import entities.Cell;
import java.awt.Color;
/**
 * Implementation of Conway's Game of Life on a toroidal grid.
 * This class extends MainSimulator to simulate the game using a graphical interface.
 */
public class ConwaySimulator extends GridSimulator {
     /**
      * Constructor for ConwaySimulator.
      * @param rows The number of rows in the grid.
      * @param cols The number of columns in the grid.
      * @param coverage The percentage of cells initially alive.
      */
     public ConwaySimulator(int width, int height, int rows, int cols, int coverage, Color bgColor) {
         super(width, height, rows, cols, 2, bgColor, coverage);
         init();
     }

     /**
      * Updates the grid according to Conway's rules using a double buffer.
      */
     @Override
     protected void update() {
         int[] next = new int[rows*cols];
         for (int r = 0; r < rows; r++) {
             for (int c = 0; c < cols; c++) {
                 Cell e = entities.get(toIndex(r,c));
                 boolean alive = e.isAlive();
                 int n = similarNeighbors(e, 1);
                 if ((alive && (n == 2 || n == 3)) || (!alive && n == 3)) {
                     next[toIndex(r,c)] = 1;
                 }
             }
         }
         // apply the buffer
         for (int i = 0; i < entities.size(); i++) {
             entities.get(i).setState(next[i]);
             entities.get(i).setColor(mapGradientColor(next[i]));
         }
     }
}
