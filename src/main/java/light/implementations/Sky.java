/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package light.implementations;

import color.SpectralPowerDistribution;
import java.util.Random;
import light.LightSource;
import math_and_utils.Math3dUtil;
import math_and_utils.Math3dUtil.Vector3;

/**
 *Like laser, but from different starting points
 * @author rasto
 */
public class Sky extends LightSource{
    Vector3 A,B,C, dir, AB,BC;
    private Random ABshift,BCshift;
    double ABdistance, BCdistance;
    
    /**
     * sides AB BC 
     * @param spd
     * @param A corner A
     * @param B corner B
     * @param C corner C
     * @param dir normal of this plane
     */
    public Sky(SpectralPowerDistribution spd, Vector3 A, Vector3 B, Vector3 C, Vector3 dir){
        super(spd);
        this.A = A;
        this.B = B;
        this.C = C;
        this.dir = dir.normalize();
        ABshift = new Random();
        BCshift = new Random();
        
        ABdistance = A.distance(B);
        AB = B.sub(A).normalize();
        
        BCdistance = B.distance(C);
        BC = C.sub(B).normalize();
    }
    
    @Override
    public Beam getNextBeam(){
        beams++;
        //go from A to B
        Vector3 poz = new Vector3(A.x,A.y,A.z);
        double ab_s = ABshift.nextDouble()*ABdistance;
        poz = poz.add(AB.scale(ab_s));
        
        //go from B to C
        double bc_s = BCshift.nextDouble()*BCdistance;
        poz = poz.add(BC.scale(bc_s));
        
        return new Beam(poz, dir, (double)spd.getNextLamnbda(), this);
    }
}
