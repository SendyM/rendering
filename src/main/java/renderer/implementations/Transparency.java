/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer.implementations;

import renderer.SceneObjectProperty;

/**
 *https://en.wikipedia.org/wiki/Sellmeier_equation
 * computes N for one side of triangle
 * @author rasto
 */
@FunctionalInterface
public interface Transparency extends SceneObjectProperty{
    public double getN(double l);
}

//private double b1= 0,b2= 0,b3= 0,c1= 0,c2= 0,c3= 0;
    
    /**
     * some values on https://refractiveindex.info/?shelf=glass&book=BK7&page=SCHOTT
     * @param B1
     * @param B2
     * @param B3
     * @param C1
     * @param C2
     * @param C3 
     */
    /*public Transparency(double B1,double B2,double B3,double C1,double C2,double C3){
        b1 = B1;b2 = B2;b3 = B3;
        c1 = c1;c2 = c2;c3 = c3;
    }

    public double getN(double lambda){
        lambda*= 0.001;//micormeters
        double l2 = lambda*lambda;

        return Math.sqrt(1+(b1*l2)/(l2-c1)+(b2*l2)/(l2-c1)+(b3*l2)/(l2-c1));
    }
}
*/