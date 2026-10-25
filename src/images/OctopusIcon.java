package images;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.Icon; 

/**
 * This class has been automatically generated using svg2java
 * @author auto 
 */
public class OctopusIcon implements Icon {
	
	private float origAlpha = 1.0f;

    /** Couleur de remplissage (par défaut bleu nuit). */
    private Color fill = new Color(10, 20, 80, 255);

    /** Permet de changer la couleur de l'icône dans des cas spécifiques. */
    public void setFill(Color c) {
        this.fill = c;
    }

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

	private void paintShapeNode_0_0_0_0(Graphics2D g) {
		GeneralPath shape0 = new GeneralPath();
		shape0.moveTo(9685.0, 12139.0);
		shape0.moveTo(9685.0, 12139.0);
		shape0.curveTo(9078.0, 12077.0, 8574.0, 11844.0, 8200.0, 11453.0);
		shape0.curveTo(7729.0, 10962.0, 7461.0, 10452.0, 7359.0, 9856.0);
		shape0.curveTo(7329.0, 9682.0, 7322.0, 9334.0, 7344.0, 9149.0);
		shape0.curveTo(7359.0, 9025.0, 7359.0, 9003.0, 7345.0, 8884.0);
		shape0.curveTo(7332.0, 8776.0, 7324.0, 8743.0, 7295.0, 8685.0);
		shape0.curveTo(7218.0, 8525.0, 7061.0, 8402.0, 6852.0, 8336.0);
		shape0.curveTo(6485.0, 8220.0, 5884.0, 8267.0, 5220.0, 8462.0);
		shape0.curveTo(5155.0, 8481.0, 5121.0, 8484.0, 4965.0, 8484.0);
		shape0.curveTo(4744.0, 8484.0, 4654.0, 8467.0, 4260.0, 8349.0);
		shape0.curveTo(3794.0, 8209.0, 3680.0, 8186.0, 3445.0, 8185.0);
		shape0.curveTo(3327.0, 8185.0, 3263.0, 8190.0, 3200.0, 8203.0);
		shape0.curveTo(3046.0, 8236.0, 2899.0, 8295.0, 2775.0, 8375.0);
		shape0.curveTo(2666.0, 8444.0, 2521.0, 8719.0, 2464.0, 8965.0);
		shape0.curveTo(2438.0, 9075.0, 2438.0, 9308.0, 2464.0, 9399.0);
		shape0.curveTo(2504.0, 9545.0, 2588.0, 9663.0, 2687.0, 9712.0);
		shape0.curveTo(2727.0, 9731.0, 2751.0, 9735.0, 2830.0, 9735.0);
		shape0.curveTo(2916.0, 9735.0, 2931.0, 9732.0, 2992.0, 9703.0);
		shape0.curveTo(3086.0, 9659.0, 3143.0, 9618.0, 3235.0, 9530.0);
		shape0.curveTo(3299.0, 9468.0, 3324.0, 9451.0, 3362.0, 9441.0);
		shape0.curveTo(3550.0, 9396.0, 3754.0, 9481.0, 3960.0, 9689.0);
		shape0.curveTo(4072.0, 9804.0, 4155.0, 9917.0, 4222.0, 10046.0);
		shape0.curveTo(4269.0, 10139.0, 4282.0, 10190.0, 4258.0, 10190.0);
		shape0.curveTo(4238.0, 10190.0, 4139.0, 10080.0, 4064.0, 9975.0);
		shape0.curveTo(4007.0, 9895.0, 3976.0, 9862.0, 3912.0, 9815.0);
		shape0.curveTo(3645.0, 9618.0, 3485.0, 9583.0, 3349.0, 9691.0);
		shape0.curveTo(3327.0, 9708.0, 3265.0, 9777.0, 3211.0, 9844.0);
		shape0.curveTo(3079.0, 10005.0, 3016.0, 10051.0, 2900.0, 10065.0);
		shape0.curveTo(2793.0, 10078.0, 2640.0, 10030.0, 2470.0, 9929.0);
		shape0.lineTo(2381.0, 9876.0);
		shape0.lineTo(2337.0, 9781.0);
		shape0.curveTo(2112.0, 9295.0, 2102.0, 8681.0, 2313.0, 8276.0);
		shape0.curveTo(2451.0, 8010.0, 2679.0, 7829.0, 2965.0, 7759.0);
		shape0.curveTo(3069.0, 7733.0, 3276.0, 7724.0, 3400.0, 7739.0);
		shape0.curveTo(3535.0, 7756.0, 3650.0, 7781.0, 3933.0, 7856.0);
		shape0.curveTo(4070.0, 7892.0, 4232.0, 7932.0, 4293.0, 7946.0);
		shape0.curveTo(4421.0, 7973.0, 4662.0, 7997.0, 4704.0, 7986.0);
		shape0.lineTo(4732.0, 7979.0);
		shape0.lineTo(4688.0, 7948.0);
		shape0.curveTo(4613.0, 7894.0, 4426.0, 7699.0, 4346.0, 7591.0);
		shape0.curveTo(4125.0, 7293.0, 3958.0, 6937.0, 3714.0, 6245.0);
		shape0.curveTo(3607.0, 5941.0, 3633.0, 5976.0, 3493.0, 5955.0);
		shape0.curveTo(3064.0, 5892.0, 2731.0, 5796.0, 2441.0, 5651.0);
		shape0.curveTo(2234.0, 5549.0, 2189.0, 5518.0, 2124.0, 5438.0);
		shape0.curveTo(1846.0, 5091.0, 1401.0, 4799.0, 980.0, 4687.0);
		shape0.curveTo(872.0, 4658.0, 854.0, 4656.0, 675.0, 4656.0);
		shape0.curveTo(503.0, 4655.0, 478.0, 4657.0, 410.0, 4678.0);
		shape0.curveTo(278.0, 4720.0, 204.0, 4765.0, 103.0, 4866.0);
		shape0.lineTo(11.0, 4958.0);
		shape0.lineTo(42.0, 4897.0);
		shape0.curveTo(165.0, 4649.0, 377.0, 4494.0, 637.0, 4459.0);
		shape0.curveTo(734.0, 4446.0, 808.0, 4451.0, 985.0, 4481.0);
		shape0.curveTo(1287.0, 4532.0, 1530.0, 4646.0, 2020.0, 4964.0);
		shape0.curveTo(2282.0, 5134.0, 2652.0, 5320.0, 2728.0, 5320.0);
		shape0.curveTo(2744.0, 5320.0, 2774.0, 5331.0, 2793.0, 5345.0);
		shape0.curveTo(2864.0, 5393.0, 3202.0, 5482.0, 3402.0, 5505.0);
		shape0.curveTo(3471.0, 5514.0, 3465.0, 5527.0, 3480.0, 5318.0);
		shape0.curveTo(3540.0, 4459.0, 3371.0, 3618.0, 3039.0, 3120.0);
		shape0.curveTo(2966.0, 3010.0, 2829.0, 2869.0, 2750.0, 2823.0);
		shape0.curveTo(2650.0, 2764.0, 2524.0, 2747.0, 2455.0, 2782.0);
		shape0.curveTo(2441.0, 2789.0, 2438.0, 2789.0, 2445.0, 2782.0);
		shape0.curveTo(2467.0, 2759.0, 2581.0, 2710.0, 2646.0, 2694.0);
		shape0.curveTo(2724.0, 2676.0, 2813.0, 2680.0, 2871.0, 2704.0);
		shape0.curveTo(2914.0, 2722.0, 2957.0, 2768.0, 3057.0, 2904.0);
		shape0.curveTo(3415.0, 3388.0, 3649.0, 4084.0, 3700.0, 4815.0);
		shape0.curveTo(3714.0, 5023.0, 3748.0, 5260.0, 3790.0, 5458.0);
		shape0.lineTo(3802.0, 5510.0);
		shape0.lineTo(3844.0, 5510.0);
		shape0.curveTo(3901.0, 5510.0, 4108.0, 5568.0, 4250.0, 5623.0);
		shape0.curveTo(4418.0, 5688.0, 4510.0, 5730.0, 4886.0, 5913.0);
		shape0.curveTo(5229.0, 6080.0, 5407.0, 6157.0, 5558.0, 6204.0);
		shape0.curveTo(5604.0, 6218.0, 5643.0, 6230.0, 5645.0, 6230.0);
		shape0.curveTo(5647.0, 6230.0, 5641.0, 6210.0, 5630.0, 6186.0);
		shape0.curveTo(5580.0, 6069.0, 5544.0, 5865.0, 5553.0, 5753.0);
		shape0.curveTo(5556.0, 5715.0, 5580.0, 5625.0, 5615.0, 5520.0);
		shape0.lineTo(5673.0, 5350.0);
		shape0.lineTo(5599.0, 5273.0);
		shape0.curveTo(5478.0, 5146.0, 5423.0, 5050.0, 5376.0, 4888.0);
		shape0.curveTo(5311.0, 4662.0, 5319.0, 4379.0, 5401.0, 3980.0);
		shape0.curveTo(5425.0, 3864.0, 5443.0, 3809.0, 5490.0, 3705.0);
		shape0.curveTo(5690.0, 3256.0, 5793.0, 2773.0, 5749.0, 2487.0);
		shape0.curveTo(5743.0, 2447.0, 5698.0, 2306.0, 5649.0, 2169.0);
		shape0.curveTo(5499.0, 1747.0, 5453.0, 1546.0, 5443.0, 1265.0);
		shape0.curveTo(5438.0, 1124.0, 5441.0, 1084.0, 5465.0, 925.0);
		shape0.curveTo(5508.0, 640.0, 5508.0, 640.0, 5509.0, 750.0);
		shape0.curveTo(5516.0, 1297.0, 5636.0, 1801.0, 5844.0, 2154.0);
		shape0.curveTo(5900.0, 2248.0, 5911.0, 2285.0, 5945.0, 2485.0);
		shape0.curveTo(5970.0, 2633.0, 5981.0, 2842.0, 5970.0, 2992.0);
		shape0.curveTo(5952.0, 3237.0, 5902.0, 3445.0, 5780.0, 3785.0);
		shape0.curveTo(5639.0, 4176.0, 5619.0, 4298.0, 5667.0, 4470.0);
		shape0.curveTo(5675.0, 4500.0, 5693.0, 4545.0, 5706.0, 4571.0);
		shape0.curveTo(5719.0, 4597.0, 5734.0, 4640.0, 5740.0, 4667.0);
		shape0.curveTo(5755.0, 4730.0, 5804.0, 4830.0, 5845.0, 4877.0);
		shape0.lineTo(5878.0, 4915.0);
		shape0.lineTo(5889.0, 4875.0);
		shape0.curveTo(5895.0, 4853.0, 5900.0, 4815.0, 5900.0, 4789.0);
		shape0.curveTo(5900.0, 4726.0, 5960.0, 4485.0, 6022.0, 4300.0);
		shape0.curveTo(6109.0, 4041.0, 6231.0, 3813.0, 6444.0, 3516.0);
		shape0.curveTo(6511.0, 3421.0, 6515.0, 3413.0, 6521.0, 3346.0);
		shape0.curveTo(6530.0, 3249.0, 6579.0, 3127.0, 6647.0, 3033.0);
		shape0.curveTo(6723.0, 2927.0, 6743.0, 2869.0, 6738.0, 2760.0);
		shape0.curveTo(6736.0, 2692.0, 6729.0, 2664.0, 6708.0, 2620.0);
		shape0.curveTo(6643.0, 2488.0, 6503.0, 2406.0, 6266.0, 2359.0);
		shape0.curveTo(6199.0, 2346.0, 6205.0, 2336.0, 6285.0, 2326.0);
		shape0.curveTo(6440.0, 2307.0, 6621.0, 2399.0, 6745.0, 2560.0);
		shape0.curveTo(6782.0, 2608.0, 6860.0, 2746.0, 6860.0, 2764.0);
		shape0.curveTo(6860.0, 2766.0, 6864.0, 2780.0, 6870.0, 2794.0);
		shape0.lineTo(6879.0, 2820.0);
		shape0.lineTo(6952.0, 2780.0);
		shape0.curveTo(7078.0, 2713.0, 7315.0, 2626.0, 7448.0, 2598.0);
		shape0.lineTo(7512.0, 2585.0);
		shape0.lineTo(7393.0, 2493.0);
		shape0.curveTo(7168.0, 2318.0, 7028.0, 2183.0, 6930.0, 2045.0);
		shape0.curveTo(6880.0, 1974.0, 6716.0, 1641.0, 6655.0, 1485.0);
		shape0.curveTo(6574.0, 1279.0, 6542.0, 1111.0, 6561.0, 986.0);
		shape0.curveTo(6567.0, 947.0, 6575.0, 911.0, 6579.0, 907.0);
		shape0.curveTo(6584.0, 903.0, 6590.0, 928.0, 6594.0, 964.0);
		shape0.curveTo(6620.0, 1220.0, 6758.0, 1486.0, 6997.0, 1740.0);
		shape0.curveTo(7160.0, 1914.0, 7473.0, 2177.0, 7685.0, 2318.0);
		shape0.lineTo(7805.0, 2399.0);
		shape0.lineTo(7826.0, 2379.0);
		shape0.curveTo(7858.0, 2349.0, 7898.0, 2269.0, 7911.0, 2211.0);
		shape0.curveTo(7920.0, 2166.0, 7919.0, 2144.0, 7900.0, 2062.0);
		shape0.curveTo(7837.0, 1783.0, 7696.0, 1545.0, 7358.0, 1147.0);
		shape0.curveTo(7293.0, 1071.0, 7240.0, 1005.0, 7240.0, 999.0);
		shape0.curveTo(7240.0, 979.0, 7316.0, 991.0, 7366.0, 1019.0);
		shape0.curveTo(7431.0, 1057.0, 7623.0, 1238.0, 7709.0, 1343.0);
		shape0.curveTo(8006.0, 1706.0, 8134.0, 1950.0, 8147.0, 2175.0);
		shape0.curveTo(8155.0, 2316.0, 8126.0, 2420.0, 8048.0, 2521.0);
		shape0.lineTo(8012.0, 2567.0);
		shape0.lineTo(8104.0, 2662.0);
		shape0.curveTo(8401.0, 2966.0, 8590.0, 3312.0, 8650.0, 3664.0);
		shape0.curveTo(8674.0, 3808.0, 8677.0, 4042.0, 8655.0, 4175.0);
		shape0.curveTo(8622.0, 4380.0, 8596.0, 4446.0, 8422.0, 4760.0);
		shape0.curveTo(8272.0, 5032.0, 8190.0, 5226.0, 8159.0, 5380.0);
		shape0.lineTo(8147.0, 5443.0);
		shape0.lineTo(8196.0, 5489.0);
		shape0.curveTo(8223.0, 5515.0, 8287.0, 5580.0, 8339.0, 5633.0);
		shape0.curveTo(8422.0, 5719.0, 8435.0, 5729.0, 8444.0, 5713.0);
		shape0.curveTo(8462.0, 5681.0, 8563.0, 5579.0, 8655.0, 5502.0);
		shape0.curveTo(8774.0, 5402.0, 8954.0, 5285.0, 9115.0, 5203.0);
		shape0.curveTo(9254.0, 5132.0, 9256.0, 5131.0, 9294.0, 5055.0);
		shape0.curveTo(9350.0, 4946.0, 9366.0, 4534.0, 9325.0, 4290.0);
		shape0.curveTo(9283.0, 4037.0, 9193.0, 3790.0, 9065.0, 3570.0);
		shape0.curveTo(9000.0, 3458.0, 8932.0, 3212.0, 8889.0, 2930.0);
		shape0.curveTo(8870.0, 2807.0, 8866.0, 2743.0, 8866.0, 2510.0);
		shape0.curveTo(8865.0, 2228.0, 8870.0, 2159.0, 8916.0, 1825.0);
		shape0.curveTo(8991.0, 1286.0, 9000.0, 1193.0, 9011.0, 905.0);
		shape0.curveTo(9024.0, 578.0, 9021.0, 548.0, 8965.0, 431.0);
		shape0.curveTo(8855.0, 201.0, 8564.0, 73.0, 8144.0, 68.0);
		shape0.curveTo(8011.0, 67.0, 8011.0, 65.0, 8145.0, 36.0);
		shape0.curveTo(8331.0, -4.0, 8517.0, -9.0, 8638.0, 24.0);
		shape0.curveTo(8876.0, 89.0, 9049.0, 267.0, 9133.0, 535.0);
		shape0.curveTo(9209.0, 778.0, 9225.0, 1036.0, 9184.0, 1393.0);
		shape0.curveTo(9147.0, 1725.0, 9188.0, 2392.0, 9271.0, 2820.0);
		shape0.curveTo(9331.0, 3130.0, 9401.0, 3349.0, 9565.0, 3745.0);
		shape0.curveTo(9629.0, 3898.0, 9694.0, 4069.0, 9711.0, 4124.0);
		shape0.curveTo(9743.0, 4234.0, 9770.0, 4379.0, 9770.0, 4450.0);
		shape0.curveTo(9770.0, 4475.0, 9774.0, 4501.0, 9778.0, 4507.0);
		shape0.curveTo(9784.0, 4516.0, 9836.0, 4474.0, 9951.0, 4371.0);
		shape0.curveTo(10214.0, 4134.0, 10380.0, 3951.0, 10465.0, 3806.0);
		shape0.curveTo(10478.0, 3783.0, 10501.0, 3725.0, 10515.0, 3675.0);
		shape0.curveTo(10600.0, 3381.0, 10586.0, 3043.0, 10473.0, 2670.0);
		shape0.curveTo(10415.0, 2477.0, 10390.0, 2415.0, 10182.0, 1938.0);
		shape0.curveTo(10149.0, 1862.0, 10090.0, 1745.0, 10051.0, 1676.0);
		shape0.curveTo(9934.0, 1472.0, 9893.0, 1370.0, 9856.0, 1195.0);
		shape0.curveTo(9817.0, 1011.0, 9790.0, 687.0, 9789.0, 390.0);
		shape0.curveTo(9788.0, 219.0, 9796.0, 73.0, 9806.0, 82.0);
		shape0.curveTo(9807.0, 84.0, 9816.0, 143.0, 9825.0, 214.0);
		shape0.curveTo(9834.0, 284.0, 9851.0, 376.0, 9861.0, 418.0);
		shape0.curveTo(9872.0, 461.0, 9892.0, 556.0, 9906.0, 630.0);
		shape0.curveTo(9989.0, 1085.0, 10090.0, 1333.0, 10456.0, 1996.0);
		shape0.curveTo(10530.0, 2129.0, 10628.0, 2316.0, 10675.0, 2411.0);
		shape0.curveTo(10758.0, 2581.0, 10761.0, 2591.0, 10829.0, 2860.0);
		shape0.curveTo(10907.0, 3170.0, 10920.0, 3246.0, 10920.0, 3398.0);
		shape0.curveTo(10919.0, 3597.0, 10877.0, 3752.0, 10765.0, 3975.0);
		shape0.curveTo(10649.0, 4204.0, 10581.0, 4299.0, 10439.0, 4426.0);
		shape0.curveTo(10291.0, 4558.0, 9955.0, 4903.0, 9969.0, 4908.0);
		shape0.curveTo(9974.0, 4910.0, 10034.0, 4902.0, 10102.0, 4891.0);
		shape0.curveTo(10299.0, 4858.0, 10411.0, 4849.0, 10650.0, 4845.0);
		shape0.curveTo(10856.0, 4841.0, 10882.0, 4839.0, 10965.0, 4815.0);
		shape0.curveTo(11081.0, 4781.0, 11160.0, 4743.0, 11241.0, 4681.0);
		shape0.curveTo(11499.0, 4486.0, 11573.0, 4183.0, 11425.0, 3935.0);
		shape0.curveTo(11405.0, 3902.0, 11359.0, 3844.0, 11322.0, 3806.0);
		shape0.lineTo(11255.0, 3737.0);
		shape0.lineTo(11333.0, 3790.0);
		shape0.curveTo(11545.0, 3934.0, 11650.0, 4124.0, 11637.0, 4340.0);
		shape0.curveTo(11619.0, 4642.0, 11382.0, 4943.0, 11025.0, 5116.0);
		shape0.curveTo(10859.0, 5197.0, 10762.0, 5219.0, 10540.0, 5230.0);
		shape0.curveTo(10280.0, 5243.0, 10046.0, 5281.0, 9772.0, 5356.0);
		shape0.curveTo(9679.0, 5381.0, 9640.0, 5397.0, 9628.0, 5413.0);
		shape0.curveTo(9602.0, 5446.0, 9496.0, 5655.0, 9465.0, 5735.0);
		shape0.curveTo(9425.0, 5836.0, 9382.0, 5988.0, 9364.0, 6098.0);
		shape0.curveTo(9341.0, 6232.0, 9349.0, 6458.0, 9380.0, 6555.0);
		shape0.curveTo(9426.0, 6700.0, 9506.0, 6819.0, 9609.0, 6894.0);
		shape0.lineTo(9658.0, 6930.0);
		shape0.lineTo(9766.0, 6876.0);
		shape0.curveTo(10134.0, 6693.0, 10373.0, 6634.0, 10650.0, 6659.0);
		shape0.curveTo(11067.0, 6697.0, 11473.0, 6911.0, 11856.0, 7294.0);
		shape0.curveTo(12367.0, 7806.0, 12727.0, 8538.0, 12791.0, 9192.0);
		shape0.curveTo(12798.0, 9264.0, 12800.0, 9423.0, 12797.0, 9602.0);
		shape0.curveTo(12793.0, 9915.0, 12776.0, 10065.0, 12725.0, 10270.0);
		shape0.curveTo(12596.0, 10788.0, 12280.0, 11205.0, 11749.0, 11560.0);
		shape0.curveTo(11681.0, 11605.0, 11621.0, 11648.0, 11615.0, 11655.0);
		shape0.curveTo(11598.0, 11676.0, 11414.0, 11779.0, 11276.0, 11845.0);
		shape0.curveTo(10987.0, 11983.0, 10658.0, 12080.0, 10325.0, 12125.0);
		shape0.curveTo(10183.0, 12144.0, 9813.0, 12153.0, 9685.0, 12139.0);
		shape0.closePath();
		shape0.moveTo(5408.0, 7676.0);
		shape0.curveTo(5528.0, 7601.0, 5695.0, 7535.0, 5856.0, 7501.0);
		shape0.curveTo(5949.0, 7480.0, 5957.0, 7477.0, 5990.0, 7438.0);
		shape0.curveTo(6055.0, 7360.0, 6213.0, 7280.0, 6301.0, 7280.0);
		shape0.curveTo(6317.0, 7280.0, 6330.0, 7277.0, 6330.0, 7273.0);
		shape0.curveTo(6330.0, 7249.0, 6264.0, 7156.0, 6218.0, 7115.0);
		shape0.curveTo(5984.0, 6905.0, 5762.0, 6777.0, 5142.0, 6496.0);
		shape0.curveTo(4965.0, 6416.0, 4722.0, 6301.0, 4602.0, 6241.0);
		shape0.curveTo(4396.0, 6138.0, 4359.0, 6123.0, 4215.0, 6085.0);
		shape0.curveTo(4142.0, 6066.0, 3973.0, 6030.0, 3954.0, 6030.0);
		shape0.curveTo(3925.0, 6030.0, 4095.0, 6467.0, 4220.0, 6715.0);
		shape0.curveTo(4356.0, 6984.0, 4481.0, 7167.0, 4643.0, 7336.0);
		shape0.curveTo(4794.0, 7494.0, 4915.0, 7580.0, 5069.0, 7642.0);
		shape0.curveTo(5221.0, 7703.0, 5346.0, 7716.0, 5408.0, 7676.0);
		shape0.closePath();
		shape0.moveTo(6947.0, 6475.0);
		shape0.curveTo(6992.0, 6448.0, 7029.0, 6421.0, 7030.0, 6417.0);
		shape0.curveTo(7030.0, 6404.0, 6972.0, 6322.0, 6904.0, 6240.0);
		shape0.curveTo(6835.0, 6157.0, 6668.0, 5992.0, 6573.0, 5913.0);
		shape0.curveTo(6492.0, 5846.0, 6066.0, 5591.0, 6054.0, 5603.0);
		shape0.curveTo(6051.0, 5605.0, 6047.0, 5661.0, 6043.0, 5726.0);
		shape0.curveTo(6035.0, 5896.0, 6059.0, 6038.0, 6128.0, 6218.0);
		shape0.curveTo(6155.0, 6289.0, 6168.0, 6311.0, 6184.0, 6315.0);
		shape0.curveTo(6409.0, 6361.0, 6604.0, 6420.0, 6765.0, 6489.0);
		shape0.curveTo(6815.0, 6510.0, 6857.0, 6527.0, 6860.0, 6526.0);
		shape0.curveTo(6863.0, 6526.0, 6902.0, 6503.0, 6947.0, 6475.0);
		shape0.closePath();
		shape0.moveTo(7598.0, 6145.0);
		shape0.curveTo(7641.0, 6127.0, 7672.0, 6100.0, 7710.0, 6048.0);
		shape0.lineTo(7737.0, 6011.0);
		shape0.lineTo(7732.0, 5818.0);
		shape0.lineTo(7727.0, 5625.0);
		shape0.lineTo(7675.0, 5560.0);
		shape0.curveTo(7646.0, 5523.0, 7510.0, 5377.0, 7373.0, 5233.0);
		shape0.curveTo(6965.0, 4806.0, 6841.0, 4646.0, 6710.0, 4374.0);
		shape0.curveTo(6658.0, 4267.0, 6585.0, 4061.0, 6574.0, 3991.0);
		shape0.curveTo(6570.0, 3968.0, 6564.0, 3947.0, 6560.0, 3945.0);
		shape0.curveTo(6552.0, 3940.0, 6429.0, 4127.0, 6372.0, 4230.0);
		shape0.curveTo(6322.0, 4319.0, 6287.0, 4437.0, 6299.0, 4471.0);
		shape0.curveTo(6304.0, 4487.0, 6314.0, 4544.0, 6321.0, 4599.0);
		shape0.curveTo(6340.0, 4755.0, 6310.0, 4920.0, 6222.0, 5136.0);
		shape0.curveTo(6202.0, 5185.0, 6202.0, 5189.0, 6218.0, 5203.0);
		shape0.curveTo(6227.0, 5211.0, 6325.0, 5275.0, 6435.0, 5345.0);
		shape0.curveTo(6810.0, 5585.0, 6836.0, 5604.0, 7080.0, 5845.0);
		shape0.curveTo(7204.0, 5966.0, 7329.0, 6081.0, 7358.0, 6099.0);
		shape0.curveTo(7455.0, 6160.0, 7527.0, 6174.0, 7598.0, 6145.0);
		shape0.closePath();
		shape0.moveTo(7825.0, 4963.0);
		shape0.curveTo(7895.0, 4690.0, 8006.0, 4448.0, 8148.0, 4255.0);
		shape0.curveTo(8207.0, 4175.0, 8304.0, 3975.0, 8332.0, 3875.0);
		shape0.curveTo(8365.0, 3758.0, 8364.0, 3565.0, 8332.0, 3460.0);
		shape0.curveTo(8305.0, 3373.0, 8250.0, 3266.0, 8189.0, 3183.0);
		shape0.curveTo(8131.0, 3103.0, 7946.0, 2921.0, 7821.0, 2821.0);
		shape0.curveTo(7734.0, 2751.0, 7713.0, 2739.0, 7691.0, 2745.0);
		shape0.curveTo(7644.0, 2756.0, 7267.0, 2947.0, 7135.0, 3027.0);
		shape0.curveTo(6947.0, 3141.0, 6920.0, 3161.0, 6916.0, 3184.0);
		shape0.curveTo(6897.0, 3286.0, 6865.0, 3390.0, 6830.0, 3470.0);
		shape0.curveTo(6807.0, 3522.0, 6788.0, 3575.0, 6789.0, 3588.0);
		shape0.curveTo(6791.0, 3627.0, 6840.0, 3759.0, 6891.0, 3863.0);
		shape0.curveTo(6987.0, 4058.0, 7124.0, 4268.0, 7319.0, 4519.0);
		shape0.curveTo(7449.0, 4688.0, 7775.0, 5068.0, 7791.0, 5069.0);
		shape0.curveTo(7794.0, 5070.0, 7810.0, 5022.0, 7825.0, 4963.0);
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
		g.transform(new AffineTransform(0.10000000149011612f, 0.0f, 0.0f, -0.10000000149011612f, 0.0f, 1215.0f));
		paintCompositeGraphicsNode_0_0_0(g);
		g.setTransform(trans_0_0_0);
	}

	private void paintRootGraphicsNode_0(Graphics2D g) {
		// _0_0
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
		AffineTransform trans_0_0 = g.getTransform();
		g.transform(new AffineTransform(1.3333333730697632f, 0.0f, 0.0f, 1.3333333730697632f, 0.1666666716337204f, -0.0f));
		paintCanvasGraphicsNode_0_0(g);
		g.setTransform(trans_0_0);
	}



    /**
     * Returns the X of the bounding box of the original SVG image.
     * @return The X of the bounding box of the original SVG image.
     */
    public int getOrigX() {
        return 2;
    }

    /**
     * Returns the Y of the bounding box of the original SVG image.
     * @return The Y of the bounding box of the original SVG image.
     */
    public int getOrigY() {
        return 1;
    }

    /**
     * Returns the width of the bounding box of the original SVG image.
     * @return The width of the bounding box of the original SVG image.
     */
    public int getOrigWidth() {
        return 1705;
    }

    /**
     * Returns the height of the bounding box of the original SVG image.
     * @return The height of the bounding box of the original SVG image.
     */
    public int getOrigHeight() {
        return 1620;
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
	public OctopusIcon() {
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
