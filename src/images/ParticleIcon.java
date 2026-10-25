package images;

import gui.GraphicalElement;
import javax.swing.Icon;
import java.awt.*;
import java.awt.geom.AffineTransform;

/** Adapte un javax.swing.Icon au GUI du sujet (coordonnées = centre). */
public class ParticleIcon implements GraphicalElement {
    private final int cx, cy;
    private final Icon icon;
    private final double angleRad;

    public ParticleIcon(int cx, int cy, Icon icon, double angleRad) {        
        this.cx = cx;
        this.cy = cy;
        this.icon = icon;
        this.angleRad = angleRad;
    }

    @Override
    public void paint(Graphics2D g2) {
        AffineTransform old = g2.getTransform();
        g2.translate(cx, cy);
        if (angleRad != 0.0) g2.rotate(angleRad);
        g2.translate(-icon.getIconWidth() / 2.0, -icon.getIconHeight() / 2.0);
        icon.paintIcon(null, g2, 0, 0);
        g2.setTransform(old);
    }
}
