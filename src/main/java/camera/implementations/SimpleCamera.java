/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera.implementations;

import color.Color;
import color.implementations.SPDsingle;
import light.implementations.Beam;
import light.LightSource;
import camera.Camera;

import java.util.Vector;

import static math_and_utils.Math3dUtil.*;

public class SimpleCamera extends Camera {
    protected double[][] camToWorld, worldToCam, camToWorld_direction;
    protected int w,h;
    protected Color col;
    protected Vector<Vector<XYZHolder>> spds;
    protected double Aw, Ah, hits;
    protected XYZHolder lasthitspds = null;
    protected double canvasWhalf, canvasHhalf;
    protected double PixelsCW, PixelsCH;
    protected SPDsingle spdsingle;
    
    protected Vector3 cdir;
    
    /**
     * Place camera to "from" and look to "to" point. 
     * The smaller "(lastlambda - firstlambda)", the less memory does this camera need
     * @param from camera position
     * @param to point to where we are looking
     * @param pixelwidth width of generated image
     * @param pixelheight height of generated image
     * @param AngleX total horizontal camera angle (half of it on both sides from direction)
     * @param AngleY total vertical camera angle (half of it on both sides from direction)
     * @param color see {@link Color}.
     */
    public SimpleCamera(Vector3 from, Vector3 to, int pixelwidth, int pixelheight, 
            double AngleX, double AngleY,Color color)
    {
        Vector3 tmp = new Vector3(0,1,0).normalize();
        Vector3 forward = from.sub(to).normalize();
        
        //fixes wrong cross in case of looking from (0,0,0) directly up or down
        if(vithinError(tmp.dot(forward), -1 ,epsilon))
        {
            tmp = new Vector3(0,0,1).normalize();
        }
        else if(vithinError(tmp.dot(forward),  1,epsilon))
        {
            tmp = new Vector3(0,0,-1).normalize();
        }
        
        Vector3 right = ((tmp).cross(forward)).normalize();
        Vector3 up = (forward.cross(right)).normalize();
        
        cdir = forward.scale(-1);
        
        camToWorld = new double[][]{right.V3toM4(0), up.V3toM4(0), forward.V3toM4(0), from.V3toM4(1)};
        camToWorld_direction = createNormalTransofrmMatrix(camToWorld);
        worldToCam = Minvert(camToWorld);

        w = pixelwidth; h = pixelheight;
        Aw = AngleX; Ah = AngleY;
        col = color;
        
        spds = new Vector<>();
        for(int a = 0;a < w;++a ){
            Vector<XYZHolder> v = new Vector<>();
            for(int b = 0;b < h;++b){
                v.add(new XYZHolder());
            }
            spds.add(v);
        }
        spdsingle = new SPDsingle(500);
        
        canvasWhalf = Math.sin(Math.toRadians(Aw/2.0)) / Math.sin(Math.toRadians(90 - Aw/2.0));
        canvasHhalf = Math.sin(Math.toRadians(Ah/2.0)) / Math.sin(Math.toRadians(90 - Ah/2.0));
        //System.out.println( canvasWhalf + " " + canvasHhalf);
        
        PixelsCW = w/ (canvasWhalf*2.0);
        PixelsCH = h/ (canvasHhalf*2.0);
    }

    private boolean watch(Vector3 _origin, Vector3 _direction, double lambda){
        Vector3 b_origin = _origin.multiplyByM4(worldToCam);
        
        //check if direction is correct
        if(cdir.dot(_direction) >0 )
        {//
            return false;
        }
        
        //depth = Math.abs(b_origin.z);
        double Px = /*depth*/ (b_origin.x)/(-b_origin.z);
        double Py = /*depth*/ (b_origin.y)/(-b_origin.z);
        //System.out.println(Px+ " " + Py + "  " + canvasWhalf + " " + canvasHhalf);
        if( Math.abs(Px) > canvasWhalf ||
            Math.abs(Py) > canvasHhalf
           ){return false;}
        
        Px = w/2.0 + Px*PixelsCW;
        Py = h/2.0 - Py*PixelsCH; // - to flip y
        //System.out.println(Px+ " " + Py);
        
        try{
            lasthitspds = spds.get((int)Px).get((int)Py);
            //spds.get((int)Px).get((int)Py).inc(lambda);
            //spdsingle.setLambda((int)lambda);
            //lasthitspds/*spds.get((int)Px).get((int)Py)*/.inc(col.SPDtoXYZ(spdsingle));
        } catch(Exception ex){
            System.out.println("Camera error");
            return false;
        }
        
        hits++;
        return true;
    }
    @Override
    public boolean watch(Beam b)
    {
        boolean r = watch(b.origin, b.direction, b.lambda);
        if(r == true && lasthitspds != null){//works only if all beams are from 1 LS
            //tempomary SPD holder, holds only beam lambda
            spdsingle.setLambda((int)b.lambda);
            //beam power
            //spdsingle.setY(b.power); //needed only of it != 1 
            //add beam lambda to pixel SPD
            lasthitspds.inc(col.SPDtoXYZ(spdsingle));
            
            //This sould be done in getPixels(), but that would require LSsource 
            //to be stored in pixels, but we use only one LS, so this untroduces only small error
            double newY = (lasthitspds).spdshits * (b.source.getPower()/ b.source.getNumberOfBeams());
            lasthitspds.setY( newY);
        }
        return r;
    }

    //@Override
    public int[][][] getPixels(){
        int coloredpixels[][][] = new int[w][h][3];
        
        for(int a = 0;a < spds.size();++a){
            for(int b = 0;b < spds.get(a).size();++b){
                XYZHolder ab = spds.get(a).get(b);
                //this si where power for this pixel shoudl be set
                //ab.setY(ab.spdshits * (b.source.getPower()/ b.source.getNumberOfBeams()));
                
                coloredpixels[a][b] = col.XYZtoRGB(
                        ab.XYZ[0], 
                        ab.XYZ[1],
                        ab.XYZ[2], ab.Ys);
            }
        }
        return coloredpixels;
    }
    
    public Vector3 GetPosition(){
        return new Vector3(camToWorld[3]);
    }


    /**
     * Stores pixel XYZ, hit and POWEEER data
     */
    class XYZHolder{
        double XYZ[];
        double Ys = 1;
        public double spdshits = 0;
        
        public XYZHolder(){
            XYZ = new double[3];
        }
        
        public void clear(){
            XYZ[0] = 0;
            XYZ[1] = 0;
            XYZ[2] = 0;
            Ys = 1;
            spdshits = 0;
        }
        
        public void setY(double y){
            Ys = y;
        }
    
        public double getY(){
            return Ys;
        }
        
        public void inc(double xyz[]){
            spdshits++;
            for(int a = 0;a < 3;++a){
                XYZ[a] += xyz[a];
            }
        }
    }

}
