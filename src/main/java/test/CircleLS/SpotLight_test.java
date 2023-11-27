/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.CircleLS;

import color.implementations.SPDrange;
import color.implementations.SPDsingle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import light.LightSource;
import light.implementations.SimpleSpotLight;
import light.implementations.Sky;
import math_and_utils.Math3dUtil;
import renderer.Scene;
import color.implementations.CIE1931StandardObserver;
import javafx.stage.Stage;
import math_and_utils.Math3dUtil.Vector3;
import camera.Camera;
import camera.implementations.SimpleCamera;
import renderer.implementations.DefaultScene;
import renderer.implementations.SimpleSceneObject;
import renderer.implementations.SimplifiedRenderer_Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javafx.application.Application.launch;
import static math_and_utils.Math3dUtil.createNormalTransofrmMatrix;
import static math_and_utils.Math3dUtil.createRotXMatix;

/**
 * @author rasto
 */
public class SpotLight_test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        System.out.println("Starting test");
        test(stage);
    }

    public static void processWidnow(Stage primaryStage, Scene scene, Camera camera, String filename) {
        //JAVAFX*************************************************************************
        primaryStage.setTitle("Renderer");
        //StackPane root = new StackPane();

        ImageView imageView = new ImageView();
        TextField textField = new TextField();
        Label lab = new Label("0");

        Button bGen = new Button("Generate");
        bGen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int togen = Integer.valueOf(textField.getText());
                long startTime = System.nanoTime();
                //double lasth = camera.getNumberOfHits();
                for (int a = 0; a < togen; ++a) {
                    scene.next();
                    /*if ((a % 10000) == 0) {
                        System.out.println(a);
                    }*/
                }
                long endTime = System.nanoTime() - startTime;
                System.out.println("This iteration took " + (endTime * 0.000000001) + " seconds");
                save(camera, filename);
                lab.setText(Integer.toString((Integer.valueOf(lab.getText()) + togen)));
                imageView.setImage(new Image("file:" + filename));

                /*System.out.println("# added " + (camera.getNumberOfHits() - lasth) + " hits, resulting in " + camera.getNumberOfHits()
                        + " total hits. This iteration took " + (endTime * 0.000000001) + " seconds");*/
            }
        });

        ScrollPane sp = new ScrollPane();
        sp.setContent(imageView);
        VBox bbox = new VBox(bGen, textField, lab, sp);
        primaryStage.setScene(new javafx.scene.Scene(bbox, 600, 600));
        primaryStage.show();
    }

    public static void save(Camera cam, String location) {
        //image creation
        int pixels[][][] = cam.getPixels();
        BufferedImage image = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);

        for (int a = 0; a < pixels.length; ++a) {
            for (int b = 0; b < pixels[a].length; ++b) {
                //R, G, B
                try {
                    image.setRGB(a, b, new Color(pixels[a][b][0], pixels[a][b][1], pixels[a][b][2]).getRGB());
                } catch (Exception e) {
                }
            }
        }

        //save image
        try {
            File outputfile = new File(location);
            System.out.println("Saving image to " + outputfile.getAbsolutePath());
            boolean result = ImageIO.write(image, "png", outputfile);
            if (result) {
                System.out.println("Image saved");
            } else {
                System.out.println("Image not saved");
            }

        } catch (IOException ignored) {
        }
    }

    public void test(Stage primaryStage) {
        Camera cam = new SimpleCamera(new Vector3(0, 0, 0), new Vector3(0, 0, -1), 500, 500, 90, 90, new CIE1931StandardObserver());
        SimpleSpotLight ls = new SimpleSpotLight(new SPDrange(400, 700),
                new Vector3(-1, 0, 0).V3toM(),
                new Vector3(0, 0, -1).V3toM(),
                90);
        ls.setPower(50000);

        SimplifiedRenderer_Scene scene = new SimplifiedRenderer_Scene();
        scene.addCamera(cam);
        scene.addLightSource(ls);
        scene.addSceneObject(
                new SimpleSceneObject(
                        new Vector3(0, 10, -7),
                        new Vector3(6, 10, 0),
                        new Vector3(6, -10, 0),
                        new Vector3(0, -10, -7))
        );
        scene.addSceneObject(
                new SimpleSceneObject(
                        new Vector3(0, 10, -7),
                        new Vector3(-6, 10, 0),
                        new Vector3(-6, -10, 0),
                        new Vector3(0, -10, -7))
        );
        scene.addSceneObject(
                new SimpleSceneObject(
                        new Vector3(-6, -2, 0),
                        new Vector3(0, -2, -10),
                        new Vector3(6, -2, 0),
                        new Vector3(0, -2, 0))
        );
        scene.addSceneObject(
                new SimpleSceneObject(
                        new Vector3(0, -1, -3),
                        new Vector3(1, -1, -2.5),
                        new Vector3(1, -2, -2.5),
                        new Vector3(0, -2, -3))
        );

        processWidnow(primaryStage, scene, cam, "SL_output.png");
    }
}
