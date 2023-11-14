package light.implementations;

import light.LightSource;
import math_and_utils.Math3dUtil;

import static math_and_utils.Math3dUtil.anglesToVector3;

public class Beam {
    /**
     * starting point of beam
     */
    public Math3dUtil.Vector3 origin;

    /**
     * direction of beam
     */
    public Math3dUtil.Vector3 direction;

    /**
     * wavelength of beam
     */
    public double lambda;

    /**
     * LS that generated this beam
     */
    public LightSource source;

    /**
     * 1 beam has power of (LS.power/LS.beams)*power , so it should not be more than 1
     * NOT IMPLEMENTED YET
     */
    public double power = 1;

    /**
     * Additional data bout this Beam
     */
    public String data = "";

    /**
     * Constructor that takes variables of type Vector3 as vector parameters for origin and direction
     * @param o beam origin (starting point)
     * @param d beam direction, should be normalized
     * @param l wavelength
     * @param s parent LS
     */
    public Beam(Math3dUtil.Vector3 o, Math3dUtil.Vector3 d, double l, LightSource s){
        origin = o;
        direction = d;
        lambda = l;
        source = s;
    }

    /**
     * Constructor that takes variable of type double[3] as vector origin and
     * double[2] containing angles, from witch it will calculate Vector direction
     * @param o beam origin (starting point)
     * @param angles see {@link math_and_utils.Math3dUtil#anglesToVector3(double A, double B)}.
     * @param l wavelength
     * @param s parent LS
     */
    public Beam(double[] o, double[] angles, double l, LightSource s){
        origin = new Math3dUtil.Vector3(o[0], o[1], o[2]);
        direction = anglesToVector3(angles[0], angles[1]);//normalizes
        lambda = l;
        source = s;
    }
}
