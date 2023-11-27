package light;

import color.SpectralPowerDistribution;
import light.implementations.Beam;

public abstract class LightSource implements java.io.Serializable{
    protected SpectralPowerDistribution spd;
    protected double beams = 0;
    protected double power = 0;

    public LightSource(SpectralPowerDistribution spd){
        this.spd = spd;
    }

    public abstract Beam getNextBeam();



    //Useful but not necessary
    public SpectralPowerDistribution getSpectralPowerDistribution(){
        return spd;
    }

    public void setSpectralPowerDistribution(SpectralPowerDistribution spd){
        this.spd = spd;
    }

    public double getNumberOfBeams(){
        return beams;
    }

    public void setPower(double p){
        power = p;
    }

    public double getPower(){
        return power;
    }


}
