/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer;

import beam.BeamInterface;
import math_and_utils.Math3dUtil;
import math_and_utils.Pair;

import java.util.List;

/**
 * Something that interacts with light beams 
 * Its made up of triangles
 * @author rasto
 */
public interface SceneObject {
    /**
     * Intersection point is b origin + direction*distance
     * we return list because of possibly transparent SceneObject
     * @param b
     * @return List of triangles and their distances form b origin
     */
    List<Pair<Triangle, Double>> intersects(BeamInterface b);
    
    /**
     * 
     * @param t Triangle
     * @param direction direction
     * @return SceneObjectProperty of Triangle T that is on side from which direction enters triangle
     */
    SceneObjectProperty getSideProperty(Triangle t,Math3dUtil.Vector3 direction);
    
    /**
     * 
     * @param t Triangle
     * @param direction direction
     * @return SceneObjectProperty of Triangle T that is on side from which direction exits triangle
     */
    SceneObjectProperty getOtherSideProperty(Triangle t,Math3dUtil.Vector3 direction);
}
