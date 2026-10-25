package images;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.Icon; 

/**
 * This class has been automatically generated using svg2java
 * @author auto 
 */
public class FishIcon implements Icon {
	
	private float origAlpha = 1.0f;
    private Color fill = new Color(0, 0, 0, 255);

	/**
	 * Paints the transcoded SVG image on the specified graphics context. You
	 * can install a custom transformation on the graphics context to scale the
	 * image.
	 * 
	 * @param g
	 *            Graphics context.
	 */
	public void paint(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        origAlpha = 1.0f;
        Composite origComposite = g.getComposite();
        if (origComposite instanceof AlphaComposite) {
            AlphaComposite origAlphaComposite = 
                (AlphaComposite)origComposite;
            if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
                origAlpha = origAlphaComposite.getAlpha();
            }
        }
        
		// _0
		AffineTransform trans_0 = g.getTransform();
		paintRootGraphicsNode_0(g);
		g.setTransform(trans_0);

	}

    public void setFill(Color c) { 
        this.fill = c; 
    }

	private void paintShapeNode_0_0_0_0_0(Graphics2D g) {
		g.setPaint(fill);
		GeneralPath shape0 = new GeneralPath();
		shape0.moveTo(184.82336, 245.82338);
		shape0.curveTo(185.87746, 244.76929, 186.23756, 241.58073, 186.23756, 241.58073);
		shape0.lineTo(184.82336, 237.3381);
		shape0.lineTo(53.124744, -2.9014347);
		shape0.curveTo(53.124744, -2.9014347, 51.690765, -5.357135, 50.473083, -5.9066358);
		shape0.curveTo(49.613724, -6.294416, 47.644653, -5.906644, 47.644653, -5.906644);
		shape0.curveTo(47.644653, -5.906644, 0.47688293, 22.87637, -18.823372, 42.17662);
		shape0.curveTo(-38.12365, 61.47686, -66.90664, 108.64466, -66.90664, 108.64466);
		shape0.curveTo(-66.90664, 108.64466, -67.29444, 110.61371, -66.90664, 111.47308);
		shape0.curveTo(-66.35712, 112.69078, -63.90143, 114.124756, -63.90143, 114.124756);
		shape0.lineTo(176.33807, 245.82338);
		shape0.curveTo(176.33807, 245.82338, 179.09, 247.23758, 180.58072, 247.23758);
		shape0.curveTo(182.07144, 247.2376, 183.76929, 246.87746, 184.82336, 245.82338);
		shape0.closePath();
		g.fill(shape0);
	}

	private void paintCompositeGraphicsNode_0_0_0_0(Graphics2D g) {
		// _0_0_0_0_0
		AffineTransform trans_0_0_0_0_0 = g.getTransform();
		g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
		paintShapeNode_0_0_0_0_0(g);
		g.setTransform(trans_0_0_0_0_0);
	}

	private void paintCompositeGraphicsNode_0_0_0(Graphics2D g) {
		// _0_0_0_0
		AffineTransform trans_0_0_0_0 = g.getTransform();
		g.transform(new AffineTransform(0.7071067811865476f, -0.7071067811865475f, 0.7071067811865475f, 0.7071067811865476f, -77.51323932934629f, 100.8664863476206f));
		paintCompositeGraphicsNode_0_0_0_0(g);
		g.setTransform(trans_0_0_0_0);
	}

	private void paintCanvasGraphicsNode_0_0(Graphics2D g) {
		// _0_0_0
		AffineTransform trans_0_0_0 = g.getTransform();
		g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 61.0f, 0.0f));
		paintCompositeGraphicsNode_0_0_0(g);
		g.setTransform(trans_0_0_0);
	}

	private void paintRootGraphicsNode_0(Graphics2D g) {
		// _0_0
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
		AffineTransform trans_0_0 = g.getTransform();
		g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, -0.0f, -0.0f));
		paintCanvasGraphicsNode_0_0(g);
		g.setTransform(trans_0_0);
	}



    /**
     * Returns the X of the bounding box of the original SVG image.
     * @return The X of the bounding box of the original SVG image.
     */
    public int getOrigX() {
        return 18;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * @return The Y of the bounding box of the original SVG image.
     */
    public int getOrigY() {
        return 18;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * @return The width of the bounding box of the original SVG image.
     */
    public int getOrigWidth() {
        return 254;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * @return The height of the bounding box of the original SVG image.
     */
    public int getOrigHeight() {
        return 254;
    }
    
    
	/**
	 * The current width of this resizable icon.
	 */
	int width;

	/**
	 * The current height of this resizable icon.
	 */
	int height;

	/**
	 * Creates a new transcoded SVG image.
	 */
	public FishIcon() {
        this.width = getOrigWidth();
        this.height = getOrigHeight();
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.Icon#getIconHeight()
	 */
    @Override
	public int getIconHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.Icon#getIconWidth()
	 */
    @Override
	public int getIconWidth() {
		return width;
	}

	/*
	 * Set the dimension of the icon.
	 */

	public void setDimension(Dimension newDimension) {
		this.width = newDimension.width;
		this.height = newDimension.height;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics, int, int)
	 */
    @Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.translate(x, y);

		double coef1 = (double) this.width / (double) getOrigWidth();
		double coef2 = (double) this.height / (double) getOrigHeight();
		double coef = Math.min(coef1, coef2);
		g2d.scale(coef, coef);
		paint(g2d);
		g2d.dispose();
	}
}