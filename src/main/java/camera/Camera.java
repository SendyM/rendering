package camera;

import beam.implementations.CameraBeam;
import math_and_utils.Math3dUtil.*;
import beam.implementations.Beam;

public abstract class Camera implements java.io.Serializable{
    protected Vector3 position;
    protected int[][][] pixels;

    public Vector3 GetPosition(){return position;}

    public int[][][] getPixels(){return pixels;}

    public abstract void watch(Beam b);

    public abstract CameraBeam getNextBeam();
}
