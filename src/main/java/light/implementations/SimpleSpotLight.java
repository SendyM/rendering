package light.implementations;

import color.SpectralPowerDistribution;
import light.LightSource;
import math_and_utils.Math3dUtil.Vector3;

import java.util.Random;

import static math_and_utils.Math3dUtil.rotateVectorCC;

/**
 *Shines in cone, form position, in cone_direction, with cone_angle as beam 
 * direction deviation from cone_direction
 */
public class SimpleSpotLight extends LightSource{
    protected Random rndrAX,rndrAY;
    
    /**centre of Light source*/
    protected double position[];
    
    /**direction where  will be center of light cone base*/
    protected double direction[];
    
    /**Angle form direction*/
    protected double angle;

    public SimpleSpotLight(SpectralPowerDistribution spd, double[] position, double cone_direction[], double cone_angle){
        super(spd);
        rndrAX = new Random();
        rndrAY = new Random();
        this.position = position;
        this.direction = cone_direction;
        this.angle = cone_angle;
    }


    public Beam getNextBeam(){
        Vector3 poz = new Vector3(position[0],position[1],position[2]);
        double lambda = spd.getNextLamnbda();
        
        Vector3 dir = new Vector3(direction[0],direction[1],direction[2]).normalize();
        //shift to side
            //get orhtogonal vector - cross of dir and random vector
            Vector3 orthogonal = dir.cross(new Vector3(658,781,356).normalize()).normalize();
        double rot = (rndrAX.nextDouble() * angle);
        dir = rotateVectorCC(dir, orthogonal,Math.toRadians(rot));
        //rotate around dir
        double rot2 = (rndrAY.nextDouble() *360);
        dir = rotateVectorCC(dir, new Vector3(direction[0],direction[1],direction[2]).normalize(),Math.toRadians(rot2));
        
        beams++;
        return new Beam(poz, dir, lambda, this);
    }

}
