package app.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * définit les éléments graphiques de l'interface 
 * utilisateur principale (boutons etc)
 */
public class UiKit {
    private UiKit() {}

    public static JButton primary(String text){
        return stylePrimary(new JButton(text));
    }

    /** Applique le style "primary" à un bouton existant. */
    public static JButton stylePrimary(JButton b){
        b.setFont(b.getFont().deriveFont(Font.BOLD, 18f));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(66,133,244));
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(14,22,14,22));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JButton secondaryDisabled(String text){
        JButton b = new JButton(text);
        b.setFont(b.getFont().deriveFont(Font.PLAIN, 16f));
        b.setEnabled(false);
        b.setForeground(new Color(210,210,210));
        b.setBackground(new Color(80,80,80));
        b.setBorder(new EmptyBorder(14,22,14,22));
        return b;
    }

    // Panneau avec un dégradé de couleur vertical
    public static final class GradientPanel extends JPanel {
        private final Color top, bottom;
        public GradientPanel(Color top, Color bottom){ this.top = top; this.bottom = bottom; }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(0, 0, top, 0, getHeight(), bottom));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}
