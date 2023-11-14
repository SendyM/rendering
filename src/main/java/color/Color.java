package color;

/**
 * This class handless transformation of SPD to RGB
 *
 * It is interface, because color perception (or what RGB will we get) depends on observer
 * @author rasto
 */
public interface Color {
    /**
     * Takes SPD and returns RGB
     * @param spd SPD to transform
     * @return int[3] containing R G B in this order
     */
    public int[] SPDtoRGB(SpectralPowerDistribution spd);

    /**
     * Takes SPD and returns XYZ
     * Useful for camera, because XYZ is easier to modify (relating to power and corrections) that RGB
     * @param spd SPD to transform
     * @return double[3] containing X Y Z in this order
     */
    public double[] SPDtoXYZ(SpectralPowerDistribution spd);

    /**
     * Transforms XYZ to RGB
     * @param X X from XYZ color space
     * @param Y Y from XYZ color space
     * @param Z Z from XYZ color space
     * @param scale number that X Y Z will be multiplied by
     * @return int[3] containing R G B in this order
     */
    public int[] XYZtoRGB(double X, double Y, double Z, double scale);
}
