package beam.implementations;

import math_and_utils.Math3dUtil;

public class CameraBeam implements beam.BeamInterface{
    //origin of beam
    public Math3dUtil.Vector3 origin;
    //direction of beam, should be normalized
    public Math3dUtil.Vector3 direction;

    public CameraBeam(Math3dUtil.Vector3 o, Math3dUtil.Vector3 d){
        origin = o;
        direction = d;
    }

    @Override
    public Math3dUtil.Vector3 getOrigin() {
        return origin;
    }

    @Override
    public Math3dUtil.Vector3 getDirection() {
        return direction;
    }
}
