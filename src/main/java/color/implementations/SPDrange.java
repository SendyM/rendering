/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package color.implementations;

import color.SpectralPowerDistribution;

import java.util.Random;

/**
 * SPD that has power = 1 on desired wavelength range, 0 outside of it
 * @author rasto
 */
public class  SPDrange implements SpectralPowerDistribution{
    /**
     * Scales Y (POWEEER)
     */
    private double Ys = 1;
    private Random ran = new Random();
    int f,l;
    
    /**
     * 
     * @param first first generable lambda
     * @param last last generable lambda
     */
    public SPDrange(int first, int last){
        f = first;
        l = last; 
    }
    
    public double getNextLamnbda(){
        return f + ran.nextDouble()*(l-f);
    }
    
    public double getValue(double l){
        return (1.0/(1+l-f))*Ys;
    }
    
    public double[] getFirstLastZero(){
        return new double[]{f-1,l+1};
    }
    
    public void setY(double y){
        Ys = y;
    }
    
    public double getY(){
        return Ys;
    }
}