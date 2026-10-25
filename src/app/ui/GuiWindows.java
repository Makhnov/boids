package app.ui;


import java.awt.*;


/**
 * Gère les fenêtres en cours d'exécution et ferme toutes les 
 * fenêtres avant de lancer un nouveau cas si l'utilisateur 
 * change de choix pendant l'exécution.
 */
public final class GuiWindows {
    private GuiWindows() {}

    /** Ferme toutes les fenêtres du GUISimulator si elles sont ouvertes. */
    public static void closeAllSimulators() {
        for (Frame f : Frame.getFrames()) {
            if (f != null && f.isDisplayable()) {
                String title = f.getTitle();
                if (title != null && title.contains("Simulateur de Systèmes Multi-Agents")) {
                    f.dispose();
                }
            }
        }
    }
}
