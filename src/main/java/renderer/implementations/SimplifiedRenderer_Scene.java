/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer.implementations;

import java.util.ArrayList;
import java.util.List;
import light.LightSource;
import math_and_utils.Math3dUtil;
import static math_and_utils.Math3dUtil.reflect;
import static math_and_utils.Math3dUtil.refract;
import light.implementations.Beam;
import math_and_utils.Pair;
import camera.Camera;
import renderer.Scene;
import renderer.SceneObject;
import renderer.SceneObjectProperty;
import renderer.Triangle;

/**
 *
 * @author rasto
 */
public class SimplifiedRenderer_Scene implements Scene {

    public Camera cam;
    public LightSource ls;
    /**
     * scene objects list
     */
    public List<SceneObject> so_list;
    /**
     * maximum iteration, especially useful to prevent infinite loops with Total
     * Reflection
     */
    public int maxiter = 10;
    /**
     * If enabled, will send beam directly to camera as soon, as beam hits
     * object with (both) null side properties
     */
    public boolean forcesendtocamera = false;

    //public Map<Double, Double> ltrans = new TreeMap<Double, Double>();
    public SimplifiedRenderer_Scene() {
        so_list = new ArrayList<SceneObject>();
    }

    public void addLightSource(LightSource ls) {
        this.ls = ls;
    }

    public void addCamera(Camera c) {
        this.cam = c;
    }

    public void addSceneObject(SceneObject so) {
        so_list.add(so);
    }

    public void next() {
        Beam b = ls.getNextBeam();
        Triangle ignoredT = null;
        double iter = 0;

        //double sl = b.lambda;
        boolean cameraBeam = false;
        //System.out.print(b.lambda + " ");

        do {
            if (iter > 2) {
                System.out.println(iter);
            }
            Pair<Triangle, Double> closestT = Pair.createPair(null, Double.MAX_VALUE);

            for (SceneObject so : so_list) {
                List<Pair<Triangle, Double>> contact = so.intersects(b);

                for (Pair<Triangle, Double> pair : contact) {
                    if (pair.second() < closestT.second() && pair.first() != null && pair.first() != ignoredT) {
                        closestT = Pair.createPair(pair.first(), pair.second());
                    }
                }
            }

            //if beam doesnt hit any triangle
            if (closestT.first() == null) {
                //if beam shoudl go to camera
                if (cameraBeam) {
                    cam.watch(b);
                }
                return;
            } else //if beam hit something
            {
                //if beam should have had free view of camera, but it doesnt
                if (cameraBeam) {
                    return;
                }
            }

            Math3dUtil.Vector3 intersectionPoint = b.origin.add((b.direction).scale(closestT.second()));
            double cos = Math.abs(b.direction.normalize().dot(closestT.first().normal));
            b.power *= cos;

            SceneObjectProperty side = closestT.first().parent.getSideProperty(closestT.first(), b.direction);
            SceneObjectProperty oside = closestT.first().parent.getOtherSideProperty(closestT.first(), b.direction);

            //refraction
            if (side instanceof Transparency
                    && oside instanceof Transparency)//transparent triangle
            {
                Pair<Math3dUtil.Vector3, Double> ref = refract(b.direction, closestT.first().normal,
                        ((Transparency) side).getN(b.lambda),
                        ((Transparency) oside).getN(b.lambda),
                        b.lambda);

                if (ref.first().x == 0 && ref.first().y == 0 && ref.first().z == 0) {
                    System.out.println("x");
                    return;
                }

                b.origin = intersectionPoint;
                b.direction = ref.first().normalize();
                b.lambda = ref.second();

                ignoredT = closestT.first();
            } else if (side == null && oside == null)//nontransparent triangle
            {
                if (forcesendtocamera) {
                    Math3dUtil.Vector3 difusedirection = (cam.GetPosition().sub(intersectionPoint)).normalize();

                    b.origin = intersectionPoint;
                    b.direction = difusedirection;
                    cam.watch(b);
                    System.out.println(2);
                    return;
                }

                Math3dUtil.Vector3 difusedirection = (cam.GetPosition().sub(intersectionPoint)).normalize();

                b.origin = intersectionPoint;
                b.direction = difusedirection;

                cameraBeam = true;
                ignoredT = closestT.first();
            } else if (side instanceof TotalReflection || oside instanceof TotalReflection) {
                Math3dUtil.Vector3 ref = reflect(b.direction, closestT.first().normal);

                b.origin = intersectionPoint;
                b.direction = ref.normalize();

                ignoredT = closestT.first();
            } else//idk lol
            {
                System.out.println("DefaultScene undefined behavior");
                return;
            }
            iter++;

        } while (!(iter >= maxiter));
    }
}
