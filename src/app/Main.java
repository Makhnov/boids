package app;

import app.ui.ArenaFrame;
import javax.swing.SwingUtilities;

/**
 * Classe qui lance l'application et affiche l'Arène (fenêtre dans laquelle 
 * l'utilisateur peut choisir les jeux auxquels il souhaite jouer). 
 */
public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> e.printStackTrace());
        SwingUtilities.invokeLater(() -> {
            new ArenaFrame().setVisible(true);
        });
    }
}
