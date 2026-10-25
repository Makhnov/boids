package core;

import gui.GUISimulator;
import gui.Simulable;

import entities.Entity;
import world.Board;

import java.awt.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * Trame commune : un Simulable abstrait qui sait piloter update/draw/reset.
 * Les sous-classes (Conway, Balls, etc.) redéfinissent la logique.
 */
public abstract class Simulator<E extends Entity, B extends Board> implements Simulable {
    final protected GUISimulator gui;

    // Rows = width, cols = height if not using cell simulations
    final protected int rows;
    final protected int cols;
    final protected float heightCoef = (float) 0.9;
    private int size = -1;

    // List of entities in the simulation, defined by subclasses (Cell, Particle, etc.)
    protected List<E> entities;

    // The board used for drawing, defined by subclasses (Grid, Space, etc.)
    final protected B board;

    protected Simulator(int width, int height, int rows, int cols, Color bgColor, B board) {
        gui = new GUISimulator(width, height, bgColor);
        // gui.setDelay(20);
        this.rows = rows;
        this.cols = cols;
        this.board = board;

        // A DEPLACER UNE FOIS LE REWORK DE INIT FAIT
        init_margin(0, 0, 50, 0);
    }

    // appelé par le GUI quand on clique "Play"
    public void play() {
        gui.setSimulable(this);
        draw();
    }

    // appelé par le GUI à chaque "pas"
    @Override
    public void next() {
        update();
        draw();
    }

    // appelé par le GUI quand on clique "Début"
    @Override
    public void restart() {
        clear();
        init();
    }

    // dessine le plateau et les entités
    protected void draw() {
        gui.reset();
        int newSize = scaling(); // newSize en fonction de la taille actuelle de la fenêtre
        if (newSize != size) { // si changement de size, on met à jour
            size = newSize;
            Entity.setSize(size);
            board.setSize(size);
            update_offsets();
        }
        board.draw(gui, rows, cols);
        for (E e : entities) e.draw(gui);
    }

    /* NEED REWORK 
     *  EDIT: Le rework n'a pas encore été fait par manque de temps, mais le code est fonctionnel tel quel.
     *  Ces méthodes gèrent les marges et offsets en pixels pour le board et les entités.
     *  Elles devraient être revues pour une meilleure intégration avec l'initialisation de la simulation.
     */
    // A REVOIR UNE FOIS LE REWORK DE INIT FAIT
    protected void init_margin(int topPx, int rightPx, int bottomPx, int leftPx) {
        board.setMarginsPx(topPx, rightPx, bottomPx, leftPx);
        int newSize = scaling(); // calcule size avec les marges pixels
        if (newSize != size) { // si changement de size, on met à jour
            size = newSize;
            Entity.setSize(size);
            board.setSize(size);
        }
        update_offsets(); // centre l’aire jouable et propage l’offset
    }

    protected void init_offset() {
        int x = board.getMarginLeft() * size;
        int y = board.getMarginTop()  * size;
        Entity.setOffset(x, y);
    }

    // A refaire: centre et pousse les offsets pixels à Board et Entity
    private void update_offsets() {
        int availW = gui.getWidth()  - board.getMarginLeftPx() - board.getMarginRightPx();
        int availH = (int)(gui.getHeight() * heightCoef) - board.getMarginTopPx() - board.getMarginBottomPx();

        // aire jouable réelle consommée en px par la grille
        int usedW = cols * size;
        int usedH = rows * size;

        // centrage dans l’aire jouable (reste non multiple de size)
        int padX = board.getMarginLeftPx() + Math.max(0, (availW - usedW) / 2);
        int padY = board.getMarginTopPx()  + Math.max(0, (availH - usedH) / 2);

        board.setPlayAreaOffsetPx(padX, padY);
        Entity.setOffset(padX, padY);
    }
    /* END OF "NEED REWORK" */

    // Initialize the simulation
    protected abstract void init();

    // Update the state of the simulation
    protected abstract void update();

    // Assigne default values to entities
    protected abstract void clear();

    /* METHODES INTERNES */

    /* ADAPTATIVE SIZE
     * Calcule la taille optimale des entités en fonction de la taille de la fenêtre et des marges7
     */
    private int scaling() {
        if (rows <= 0 || cols <= 0) return 1;
        int availW = gui.getWidth()  - board.getMarginLeftPx() - board.getMarginRightPx();
        int availH = (int)(gui.getHeight() * heightCoef) - board.getMarginTopPx() - board.getMarginBottomPx();
        if (availW <= 0 || availH <= 0) return 1;
        return Math.max(1, Math.min(availW / cols, availH / rows));
    }

    /* INDEX FLAT LIST
     * Convertit des coordonnées 2D (i,j) en index 1D pour une liste plate
     */
    protected int toIndex(int i, int j) {
        if (i < 0 || i > rows - 1 || j < 0 || j > cols - 1) throw new IndexOutOfBoundsException("Invalid coordinate");
        return i * cols + j;
    }

    /* WRAP FUNCTIONS */

    // Wrap des colonnes pour un comportement torique
    protected int wrapCol(int c) {
        int m = c % cols;
        return (m < 0) ? m + cols : m;
    }

    // Wrap des lignes pour un comportement torique
    protected int wrapRow(int r) {
        int m = r % rows;
        return (m < 0) ? m + rows : m;
    }

    /* NEIGHBORS */
    
    // Retourne la liste des voisins d'une entité (à redéfinir dans les sous-classes si besoin)
    protected List<E> getNeighbors(E e) {
        return Collections.emptyList();
    }
}
