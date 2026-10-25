package images;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.Icon; 


/**
 * This class has been automatically generated using svg2java
 * @author auto 
 */
public class SharkIcon implements Icon {
	
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

	private void paintShapeNode_0_0_0_0(Graphics2D g) {
		GeneralPath shape0 = new GeneralPath();
		shape0.moveTo(2830.7903, 5657.3926);
		shape0.curveTo(2825.882, 5654.1475, 2807.2556, 5639.841, 2789.6685, 5625.2944);
		shape0.curveTo(2771.9214, 5611.0034, 2728.1453, 5576.891, 2692.2678, 5549.492);
		shape0.curveTo(2566.712, 5454.141, 2627.4941, 5518.2197, 2574.14, 5467.2734);
		shape0.curveTo(2531.2104, 5426.3984, 2506.3328, 5405.8735, 2494.566, 5401.366);
		shape0.curveTo(2489.5776, 5399.6724, 2479.9214, 5398.618, 2472.7754, 5398.954);
		shape0.curveTo(2459.3623, 5399.643, 2401.122, 5410.024, 2323.0261, 5425.427);
		shape0.lineTo(2278.6787, 5434.3516);
		shape0.lineTo(2268.8638, 5447.7817);
		shape0.curveTo(2259.896, 5460.1406, 2258.969, 5462.762, 2258.1233, 5478.0625);
		shape0.lineTo(2257.15, 5494.705);
		shape0.lineTo(2243.3525, 5490.8857);
		shape0.curveTo(2230.0671, 5487.3857, 2215.214, 5478.9937, 2205.349, 5469.45);
		shape0.lineTo(2201.08, 5465.182);
		shape0.lineTo(2056.176, 5501.2627);
		shape0.curveTo(1771.7238, 5571.9688, 1638.2183, 5607.1685, 1628.5308, 5613.5645);
		shape0.curveTo(1617.7399, 5620.8716, 1587.8313, 5655.3584, 1573.5249, 5676.831);
		shape0.curveTo(1557.668, 5701.069, 1552.7614, 5714.898, 1550.4138, 5741.709);
		shape0.curveTo(1549.2317, 5755.554, 1548.209, 5757.7603, 1540.2, 5765.739);
		shape0.curveTo(1532.031, 5773.9727, 1531.7599, 5774.6924, 1536.1405, 5775.6514);
		shape0.curveTo(1538.8745, 5776.1147, 1541.1289, 5777.345, 1541.3206, 5778.177);
		shape0.curveTo(1541.5608, 5779.2163, 1538.7496, 5805.9146, 1535.1713, 5837.8257);
		shape0.curveTo(1530.3473, 5881.407, 1529.1501, 5901.8228, 1530.367, 5920.3677);
		shape0.curveTo(1531.1838, 5935.2837, 1530.9768, 5946.715, 1529.3306, 5949.0645);
		shape0.curveTo(1527.0929, 5952.646, 1525.7976, 5952.726, 1515.8054, 5950.217);
		shape0.curveTo(1500.8733, 5946.221, 1475.1484, 5929.6133, 1454.5869, 5910.7183);
		shape0.curveTo(1414.8396, 5874.1436, 1372.3572, 5822.8765, 1307.8563, 5733.131);
		shape0.curveTo(1276.6617, 5689.9844, 1264.3188, 5682.9834, 1202.032, 5673.9404);
		shape0.curveTo(1149.2418, 5666.2075, 1024.637, 5655.572, 958.242, 5653.3877);
		shape0.curveTo(869.0497, 5650.1187, 716.88153, 5624.3936, 558.22174, 5585.7197);
		shape0.curveTo(454.99048, 5560.518, 401.76782, 5545.2227, 283.21945, 5505.826);
		shape0.curveTo(156.64523, 5463.904, 114.965485, 5448.352, 76.86632, 5429.347);
		shape0.curveTo(59.07193, 5420.5396, 41.420822, 5406.6646, 40.077396, 5400.845);
		shape0.curveTo(38.15822, 5392.532, 72.23984, 5361.898, 106.56203, 5340.8394);
		shape0.curveTo(146.47972, 5316.519, 158.08612, 5312.745, 229.6112, 5300.1724);
		shape0.curveTo(263.50342, 5294.318, 293.3509, 5288.9595, 296.05292, 5288.3354);
		shape0.curveTo(302.49545, 5286.848, 300.67255, 5281.7964, 291.89514, 5277.912);
		shape0.lineTo(284.397, 5274.8267);
		shape0.lineTo(288.3135, 5269.9824);
		shape0.lineTo(292.27783, 5265.3457);
		shape0.lineTo(283.61255, 5263.844);
		shape0.curveTo(279.0721, 5263.141, 275.10748, 5262.0864, 275.01138, 5261.6704);
		shape0.curveTo(274.72354, 5260.424, 302.02786, 5244.05, 320.20453, 5234.6);
		shape0.curveTo(357.96417, 5215.1562, 416.9867, 5192.9927, 479.3508, 5174.8735);
		shape0.curveTo(557.9416, 5152.1323, 829.97626, 5130.0444, 1020.62317, 5130.906);
		shape0.lineTo(1042.3015, 5130.9355);
		shape0.lineTo(1070.0851, 5108.103);
		shape0.curveTo(1085.3195, 5095.392, 1115.4849, 5069.602, 1136.8896, 5050.431);
		shape0.curveTo(1208.0906, 4987.1475, 1238.1603, 4963.7876, 1288.2769, 4933.391);
		shape0.curveTo(1344.6766, 4898.9175, 1401.5883, 4873.301, 1478.8995, 4846.915);
		shape0.curveTo(1529.1935, 4829.6123, 1536.4673, 4827.9326, 1537.427, 4832.0894);
		shape0.curveTo(1539.4423, 4840.818, 1497.9438, 4889.583, 1399.6171, 4993.7163);
		shape0.curveTo(1336.1544, 5061.124, 1312.2244, 5088.3203, 1312.9282, 5092.3174);
		shape0.curveTo(1313.2158, 5093.5645, 1318.1724, 5097.017, 1323.864, 5099.8623);
		shape0.lineTo(1334.0162, 5104.9614);
		shape0.lineTo(1326.7427, 5109.487);
		shape0.lineTo(1319.2611, 5114.0596);
		shape0.lineTo(1327.6067, 5116.0728);
		shape0.curveTo(1340.8442, 5119.3647, 1387.4954, 5126.1074, 1455.1216, 5134.5737);
		shape0.curveTo(1557.9042, 5147.402, 1568.8875, 5149.4634, 1713.127, 5184.4614);
		shape0.curveTo(1789.1633, 5202.807, 1855.0155, 5217.813, 1859.5721, 5217.6367);
		shape0.curveTo(1864.9594, 5217.2676, 1890.7299, 5208.473, 1936.4675, 5191.3467);
		shape0.curveTo(1974.132, 5177.1787, 2005.4338, 5165.793, 2006.2013, 5166.272);
		shape0.curveTo(2006.7125, 5166.592, 2005.4183, 5175.209, 2003.3252, 5185.1055);
		shape0.curveTo(2001.0718, 5195.258, 2000.1617, 5205.5376, 2001.201, 5208.143);
		shape0.curveTo(2002.3848, 5211.3726, 2009.515, 5214.761, 2027.7249, 5220.627);
		shape0.curveTo(2041.4585, 5225.1177, 2054.2646, 5229.3853, 2056.1667, 5230.0405);
		shape0.curveTo(2059.0444, 5231.1274, 2058.9167, 5232.4707, 2054.681, 5237.8267);
		shape0.curveTo(2051.9795, 5241.296, 2050.109, 5244.5737, 2050.8765, 5245.0527);
		shape0.curveTo(2056.9683, 5248.6816, 2154.9387, 5264.3716, 2209.5195, 5270.378);
		shape0.curveTo(2243.2366, 5274.196, 2244.5315, 5274.1157, 2266.977, 5268.9336);
		shape0.curveTo(2286.9285, 5264.3276, 2289.726, 5264.119, 2289.359, 5267.2686);
		shape0.curveTo(2287.889, 5277.021, 2289.984, 5287.045, 2294.0293, 5289.3945);
		shape0.curveTo(2296.1235, 5290.8813, 2316.2842, 5294.764, 2338.3142, 5298.2153);
		shape0.curveTo(2402.9194, 5307.8174, 2460.2656, 5312.5283, 2484.9976, 5309.6646);
		shape0.lineTo(2495.6768, 5308.5127);
		shape0.lineTo(2505.875, 5291.0537);
		shape0.curveTo(2511.5334, 5281.4287, 2526.8145, 5249.0054, 2539.617, 5219.125);
		shape0.curveTo(2574.0298, 5138.722, 2583.7483, 5119.185, 2609.7393, 5078.1597);
		shape0.curveTo(2634.6753, 5038.2534, 2648.183, 5023.752, 2665.1934, 5018.7305);
		shape0.curveTo(2673.4102, 5016.3955, 2673.4102, 5016.3955, 2674.3699, 5023.398);
		shape0.curveTo(2675.026, 5027.1865, 2673.476, 5038.4897, 2671.127, 5048.226);
		shape0.curveTo(2666.94, 5065.173, 2667.0999, 5067.7627, 2671.3872, 5096.763);
		shape0.curveTo(2679.754, 5154.8115, 2659.5203, 5226.625, 2657.1267, 5273.148);
		shape0.curveTo(2656.1055, 5292.428, 2652.9417, 5319.927, 2654.1897, 5328.176);
		shape0.curveTo(2657.7888, 5352.3, 2617.4087, 5294.4575, 2733.3318, 5467.555);
		shape0.curveTo(2772.33, 5525.5366, 2782.1794, 5541.651, 2782.5476, 5547.0386);
		shape0.curveTo(2782.692, 5553.3535, 2783.5076, 5554.0405, 2790.366, 5555.3027);
		shape0.curveTo(2797.6882, 5556.6772, 2798.3438, 5557.62, 2809.0732, 5579.442);
		shape0.curveTo(2821.5457, 5605.02, 2838.1604, 5647.5923, 2839.2322, 5656.9766);
		shape0.lineTo(2840.0005, 5663.1475);
		shape0.closePath();
		g.setPaint(fill);
		g.fill(shape0);
	}

	private void paintCompositeGraphicsNode_0_0_0(Graphics2D g) {
		// _0_0_0_0
		AffineTransform trans_0_0_0_0 = g.getTransform();
		g.transform(new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
		paintShapeNode_0_0_0_0(g);
		g.setTransform(trans_0_0_0_0);
	}

	private void paintCanvasGraphicsNode_0_0(Graphics2D g) {
		// _0_0_0
		AffineTransform trans_0_0_0 = g.getTransform();
		g.transform(new AffineTransform(-0.07500000298023224f, 0.0f, 0.0f, -0.07500000298023224f, 216.0f, 512.3299560546875f));
		paintCompositeGraphicsNode_0_0_0(g);
		g.setTransform(trans_0_0_0);
	}

	private void paintRootGraphicsNode_0(Graphics2D g) {
		// _0_0
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
		AffineTransform trans_0_0 = g.getTransform();
		g.transform(new AffineTransform(1.3333333730697632f, 0.0f, 0.0f, 1.3333333730697632f, -0.0f, -0.0f));
		paintCanvasGraphicsNode_0_0(g);
		g.setTransform(trans_0_0);
	}



    /**
     * Returns the X of the bounding box of the original SVG image.
     * @return The X of the bounding box of the original SVG image.
     */
    public int getOrigX() {
        return 4;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * @return The Y of the bounding box of the original SVG image.
     */
    public int getOrigY() {
        return 88;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * @return The width of the bounding box of the original SVG image.
     */
    public int getOrigWidth() {
        return 281;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * @return The height of the bounding box of the original SVG image.
     */
    public int getOrigHeight() {
        return 113;
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
	public SharkIcon() {
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

