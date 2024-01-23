/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer.implementations;

import beam.BeamInterface;
import camera.Camera;
import beam.implementations.CameraBeam;
import beam.implementations.Beam;
import light.LightSource;
import math_and_utils.Math3dUtil.Vector3;
import math_and_utils.Pair;
import renderer.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultScene implements Scene {
    public List<Camera> cam_list;
    public List<LightSource> ls_list;
    public List<SceneObject> so_list;

    //max number of iterations
    public int maxiter = 1;

    // If enabled, reflected beams will lose some power
    public boolean refl_fading = true;

    public DefaultScene() {
        cam_list = new ArrayList<Camera>();
        ls_list = new ArrayList<LightSource>();
        so_list = new ArrayList<SceneObject>();
    }

    public void addLightSource(LightSource ls) {
        ls_list.add(ls);
    }

    public void addCamera(Camera c) {
        cam_list.add(c);
    }

    public void addSceneObject(SceneObject so) {
        so_list.add(so);
    }

    public void next() {
        Beam b = ls_list.get(0).getNextBeam();
        CameraBeam cb = cam_list.get(0).getNextBeam();
        Triangle ignoredT = null;
        double iter = 0;

        boolean camerabema = false;

        ArrayList<Vector3> intersectionPointsCam = new ArrayList<>();
        ArrayList<Vector3> intersectionPoints = new ArrayList<>();
        Pair<Triangle, Double> closestT;
        Pair<Triangle, Double> cameraClosestT;

        do {
            closestT = getClosestT(b, ignoredT); //Pair.createPair(null, Double.MAX_VALUE);
            cameraClosestT = getClosestT(cb, ignoredT);

            //if beam doesn't hit any triangle
            if (closestT.first() == null) {
                //if beam should go to camera
                if (camerabema) {
                    for (Camera cam : cam_list) {
                        cam.watch(b);
                    }
                }
                return;
            }
            Vector3 intersectionPoint = b.origin.add((b.direction).scale(closestT.second()));
            Vector3 intersectionPointCam = cb.origin.add((cb.direction).scale(cameraClosestT.second()));

            intersectionPointsCam.add(intersectionPointCam);
            intersectionPoints.add(intersectionPoint);

            Vector3 difusedirectionCam = (intersectionPointCam.sub(intersectionPoint)).normalize();
            b = new Beam(intersectionPoint, difusedirectionCam, b.lambda, b.source);

            camerabema = true;
            ignoredT = closestT.first();
            iter++;

        } while (!(iter >= maxiter));

        for (Camera cam : cam_list) {
            //TODO: make it more general so that it works with more iterations
            if (getClosestT(b, ignoredT).first() == cameraClosestT.first()) {
                Vector3 difusedirectionToCam = (cam.GetPosition().sub(intersectionPointsCam.get(0))).normalize();

                b = new Beam(intersectionPointsCam.get(0), difusedirectionToCam, b.lambda, b.source);
                cam.watch(b);
            }
        }
    }

    Pair<Triangle, Double> getClosestT(BeamInterface b, Triangle ignoredT) {
        Pair<Triangle, Double> closestT = Pair.createPair(null, Double.MAX_VALUE);
        for (SceneObject so : so_list) {
            List<Pair<Triangle, Double>> contact = so.intersects(b);

            for (Pair<Triangle, Double> pair : contact) {
                if (pair.second() < closestT.second() && pair.first() != null && pair.first() != ignoredT) {
                    closestT = Pair.createPair(pair.first(), pair.second());
                }
            }
        }
        return closestT;
    }
}

