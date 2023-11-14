/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderer;

/**
 * Holds info about LS, Camera and SceneObjects
 * @author rasto
 */
public interface Scene {    
    /**
     * Will generate and compute another beam
     */
    public void next();
}
