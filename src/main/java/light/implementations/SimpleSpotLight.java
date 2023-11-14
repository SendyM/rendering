package light.implementations;

import color.SpectralPowerDistribution;
import light.LightSource;
import math_and_utils.Math3dUtil.Vector3;

import java.util.Random;

import static math_and_utils.Math3dUtil.rotateVectorCC;

/**
 *Shines in cone, form position, in cone_direction, with cone_angle as beam 
 * direction deviation from cone_direction
 * @author rasto
 */
public class SimpleSpotLight extends LightSource{
    protected Random rndrAX,rndrAY;
    
    /**centre of Light source*/
    protected double position[] = new double[3];  
    
    /**direction where  will be center of light cone base*/
    protected double direction[] = new double[3];
    
    /**Angle form direction*/
    protected double angle;
    
    /**cos(f) = (a*b)/(||a||*||b||)
       return acos(cos(f)) * (double)(180/Math.PI) -> angle in degrees*/
    public double angleBetvenVectors(double[] a, double[] b){
        double axb[] = new double[3];
        axb[0] = a[0]*b[0];
        axb[1] = a[1]*b[1];
        axb[2] = a[2]*b[2];
        return (double)Math.acos( (axb[0]+axb[1]+axb[2])/
                ( (double)Math.sqrt(a[0]*a[0]+a[1]*a[1]+a[2]*a[2])*
                  (double)Math.sqrt(b[0]*b[0]+b[1]*b[1]+b[2]*b[2]) )) * 
                (double)(180/Math.PI);
    }
    
    /**
     * 
     * @param spd
     * @param position
     * @param cone_direction direction
     * @param cone_angle angle from cone_direction
     */
    public SimpleSpotLight(SpectralPowerDistribution spd, double[] position, double cone_direction[], double cone_angle){
        super(spd);
        rndrAX = new Random();
        rndrAY = new Random();
        this.position = position;
        this.direction = cone_direction;
        this.angle = cone_angle;
    }
    
    /**
     * Takes Vector3 instead of double[3]
     * @param spd
     * @param position
     * @param cone_direction
     * @param cone_angle 
     */
    public SimpleSpotLight(SpectralPowerDistribution spd, Vector3 position, Vector3 cone_direction, double cone_angle){
        super(spd);
        rndrAX = new Random();
        rndrAY = new Random();
        this.position = position.V3toM();
        this.direction = cone_direction.V3toM();
        this.angle = cone_angle;
    }
    
    /**generates square*/
    private double[] getNextBeamArray(){
        double[] r = new double[6];
        r[0] = position[0];
        r[1] = position[1];
        r[2] = position[2];
        r[3] = rndrAX.nextDouble() * angle *2;
        r[4] = rndrAY.nextDouble() * angle *2; 
        r[3] += angleBetvenVectors(new double[]{1,0,0}, new double[]{direction[0], direction[1], direction[2]}) - angle;
        r[4] += angleBetvenVectors(new double[]{0,0,-1}, new double[]{direction[0], direction[1], direction[2]}) - angle;
        //r[3] = Math3dUtil.getPositiveAngle(r[3]);
        //r[4] = Math3dUtil.getPositiveAngle(r[4]);
        r[5] = (double)spd.getNextLamnbda();
        beams++;
        //-------------
        return r;
    }
    
    /**
     * looks like it works
     * @return 
     */
    public Beam getNextBeam(){
        Vector3 poz = new Vector3(position[0],position[1],position[2]);
        double lambda = (double)spd.getNextLamnbda();
        
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

    @Override
    public boolean watch(Beam b) {
        return false;
    }

    /**vX,vY,vZ*/
    public double[] getDirection(){
        return direction;
    } 
    
    public void setDirection(double[] direction){
        this.direction = direction;
    }
    
    /**X,Y,Z*/
    public double[] getPosition(){
        return position;
    } 
    
    public void setPosition(double[] position){
        this.position = position.clone();
    }
    
    /**A*/
    public double getAngle(){
        return angle;
    } 
    
    public void setAngle(double angle){
        this.angle = angle;
    }
}
