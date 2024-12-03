package com.seyf.core;

import org.joml.Vector3f;

public class Camera {

    private Vector3f position,rotation;

    public Camera(){
        position = new Vector3f(0,0,0);
        rotation= new Vector3f(0,0,0);

    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void movePosition(float x,float y,float z){
        if(z!=0){
            position.x+= (float) (Math.sin(Math.toRadians(rotation.y))* -1.0*z);// movement ro right left
            position.z+= (float) (Math.cos(Math.toRadians(rotation.y))*z);// back and forward
        }
        if(x!=0){
            position.x+= (float) (Math.sin(Math.toRadians(rotation.y-90))* -1.0*x);
            position.z+= (float) (Math.cos(Math.toRadians(rotation.y-90))*x);
        }
        position.y+=y;
    }

    public void setPosition(float x,float y,float z){
        position.x=x;
        position.y=y;
        position.z=z;
    }

    public void setRotation(float x,float y,float z){
        rotation.x=x;
        rotation.y=y;
        rotation.z=z;
    }

    public void moveRotation(float x,float y,float z){
        rotation.x+=x;
        rotation.y+=y;
        rotation.z+=z;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
