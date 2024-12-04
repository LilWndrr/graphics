package com.seyf.test;

import com.seyf.core.*;
import com.seyf.core.lighting.DirectionalLight;
import com.seyf.entity.Entity;
import com.seyf.entity.Material;
import com.seyf.entity.Model;
import com.seyf.entity.Texture;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class TestGame implements ILogic {

  private static final float CAMERA_MOVE_SPEED=0.005f;
    private static final float MOUSE_SENSITIVITY=0.2f;


    private float colour= 0.0f;

    private final RenderManager renderer;
    private final WindowManager window;
    private final ObjectLoader loader;
    private final Camera camera;
    private Entity entity;



    Vector3f cameraInc;

    private float lightAngle;
    private DirectionalLight directionalLight;

    public TestGame(){
        renderer= new RenderManager();
        window= Launcher.getWindow();
        loader=new ObjectLoader();
        camera= new Camera();
        cameraInc = new Vector3f(0,0,0);
        lightAngle= -90;
    }
    @Override
    public void init() throws Exception {
        renderer.init();


        Model model= loader.loadOBJModel("/models/f16.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/blue.jpg")), 1f);
        entity= new Entity(model,new Vector3f(0,0,-5),new Vector3f(0,0,0),1.5f);

        float lightIntensity= 0.0f;
        Vector3f lightPosition= new Vector3f(-1,-10,0);
        Vector3f lightColor= new Vector3f(1,1,1);
        directionalLight= new DirectionalLight(lightColor,lightPosition,lightIntensity);
    }

    @Override
    public void input() {
        cameraInc.set(0,0,0);
        if(window.isKeyPressed(GLFW.GLFW_KEY_W)){
            cameraInc.z=-1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_S)){
            cameraInc.z=1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_A)){
            cameraInc.x=-1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_D)){
            cameraInc.x=1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_Z)){
            cameraInc.y=-1;
        }
        if(window.isKeyPressed(GLFW.GLFW_KEY_X)){
            cameraInc.y=1;
        }
    }

    @Override
    public void update( MouseInput mouseInput) {
        camera.movePosition(cameraInc.x* CAMERA_MOVE_SPEED ,cameraInc.y*CAMERA_MOVE_SPEED, cameraInc.z*CAMERA_MOVE_SPEED);
        if(mouseInput.isLeftButtonPress()){
            Vector2f rotVec= mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x*MOUSE_SENSITIVITY, rotVec.y*MOUSE_SENSITIVITY,0);
        }

        //entity.incRotation(0.0f,0.25f,0.0f);
        lightAngle+=0.5f;
        if(lightAngle>90){
            directionalLight.setIntensity(0);
            if(lightAngle>=360){
                lightAngle=-90;
            }
        }else if ( lightAngle<=-80|| lightAngle>=80){
            float factor= 1- (Math.abs(lightAngle)-80)/10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y=Math.max(factor,0.9f);
            directionalLight.getColor().z=Math.max(factor,0.5f);
        }else {
            directionalLight.setIntensity(1);
            directionalLight.getColor().x=1;
            directionalLight.getColor().y=1;
            directionalLight.getColor().z=1;
        }
        double angRad= Math.toRadians(lightAngle);
        directionalLight.getDirection().x= (float) Math.sin(angRad);
        directionalLight.getDirection().y= (float) Math.sin(angRad);
    }

    @Override
    public void render() {
        if(window.isResize()){
            GL11.glViewport(0,0,window.getWidth(),window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(colour,colour,colour,0.1f);
        renderer.render(entity,camera, directionalLight);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanUp();
    }
}
