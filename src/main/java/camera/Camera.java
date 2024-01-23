package camera;

import beam.implementations.CameraBeam;
import math_and_utils.Math3dUtil.*;
import beam.implementations.Beam;

public abstract class Camera implements java.io.Serializable{
    protected Vector3 position;
    protected int[][][] pixels;

    public abstract void watch(Beam b);

    public abstract CameraBeam getNextBeam();

    //TODO: I know it`s not supposed to be like this, but I cant really think of a better way, YET.
    public Vector3 GetPosition(){return position;}

    public int[][][] getPixels(){return pixels;}

}
