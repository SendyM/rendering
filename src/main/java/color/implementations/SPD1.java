package color.implementations;

import color.SpectralPowerDistribution;

import java.util.Random;

/**
 * 
 * max = 1 at l=555 and linearly drops to 0 over 100l
 * function for this lambda distribution graph should be f(x) = 1 - abs(x -555)/100
 * @author rasto
 */
public class SPD1 implements SpectralPowerDistribution{
    private Random ran = new Random();
    private double npsum = 0;
    private double Ys = 0;
    
    public SPD1(){
        for(int a = 0;a <= 200;++a){
            npsum += fx(a+455);
        }
        /*same as calcualting integral from -100 to 100 of 1-abs(x)/100, which 
         is 100*/
        //System.out.println(Double.toString(npsum));
    }
    
    private double fx(double x){
        return 1 - Math.abs(x - 555.0)/100.0;
    }
    
    private double getSome(double r){
        r *= npsum;
        double d =0;
        int a =0;
        for(;a <= 200 && d <r;++a){
            d += fx(a+455);
        }
        //we wnat to know value n in (integral form -100 to n of fx) = r
        //I ma too lazy to do it right now so I will keep the for loop for now
        
        return 450 + a-1;
    }
    
    public double getNextLamnbda(){
        return getSome(ran.nextDouble());
    }
    
    //this will turn distribution to SPD .. kinda
    //it isnt reall needed
    public double getValue(double lambda){
        return fx(lambda);// * wavelenghtEnergy(lambda);
    }
    
    public double[] getFirstLastZero(){
        return new double[]{455,655};
    }
    
    public void setY(double y){
        Ys = y;
    }
    
    public double getY(){
        return Ys;
    }
}
