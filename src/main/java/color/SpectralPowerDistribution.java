/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package color;

/**
 *
 * https://en.wikipedia.org/wiki/Spectral_power_distribution
 *
 *  if we imagine this as graph, y power and x are lambdas
 *  if we have 2 spikes with y = 1 on graph, that means that they make up equivalent
 *  portion of all radiated wavelengths
 * @author rasto
 */
public interface SpectralPowerDistribution extends java.io.Serializable{

    /**
     *
     * @return return next w-randomly chose wavelength
     */
    public double getNextLamnbda();

    /**
     *
     * @param lambda wavelength
     * @return Power on wavelength lambda
     */
    public double getValue(double lambda);

    /**
     *
     * @return double[2], where on index 0 is first wavelength with no power and on index 1 is the next wavelength with no power
     */
    public double[] getFirstLastZero();

    /**
     * Scales Y (POWEER) by y
     * @param y POWEEER
     */
    public void setY(double y);

    /**
     *
     * @return number that Y is scaled by
     */
    public double getY();
}
