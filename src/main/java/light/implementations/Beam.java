package light.implementations;

import light.LightSource;
import math_and_utils.Math3dUtil;

import static math_and_utils.Math3dUtil.anglesToVector3;

public class Beam {
    //origin of beam
    public Math3dUtil.Vector3 origin;
    //direction of beam, should be normalized
    public Math3dUtil.Vector3 direction;
    //wavelength of beam
    public double lambda;
    public LightSource source;
    public double power = 1;

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

}
