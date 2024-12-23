package com.seyf.core;

import com.seyf.test.Launcher;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class MouseInput {

    private final Vector2d previousPosition, currentPosition;
    private final Vector2f displVec;

    private boolean inWindow= false, leftButtonPress= false, rightButtonPress= false;

    public MouseInput(){
        previousPosition= new Vector2d(-1,-1);
        currentPosition= new Vector2d(0,0);
        displVec= new Vector2f();
    }

    public void init(){
        GLFW.glfwSetCursorPosCallback(Launcher.getWindow().getWindowHandle(), (window, xpos, ypos) -> {
            System.out.println(xpos+" "+ ypos);
            currentPosition.x=xpos;
            currentPosition.y=ypos;
            System.out.println("Current pos:  "+previousPosition.x + " " + previousPosition.y);
            System.out.println("Current pos:  "+currentPosition.x + " " + currentPosition.y);

        });

        GLFW.glfwSetCursorEnterCallback(Launcher.getWindow().getWindowHandle(),((window, entered) -> {
            System.out.println("Cursor entered window: " + entered);
            inWindow= entered;
        }));

        GLFW.glfwSetMouseButtonCallback(Launcher.getWindow().getWindowHandle(),((window, button, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                System.out.println("Mouse button pressed: " + button);
            }
            leftButtonPress= button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS;
            rightButtonPress= button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS;
        }));
    }

    public void input(){
        //System.out.println("MouseInput.input() called");
        displVec.x=0;
        displVec.y=0;

        if(previousPosition.x>0&&previousPosition.y>0&&inWindow){
            double x= currentPosition.x-previousPosition.x;
            //System.out.println(x);
            double y= currentPosition.y-previousPosition.y;
           // System.out.println(y);
            //System.out.println("Delta: x=" + x + ", y=" + y);
            boolean rotateX = x!=0;
            boolean rotateY = y!=0;
            if(rotateX){
                displVec.y= (float) x;
            }
            if(rotateY){
                displVec.x= (float) y;
            }
        }
        //System.out.println(displVec);
        previousPosition.x=currentPosition.x;
        previousPosition.y=currentPosition.y;
        //System.out.println("Displacement vector: " + displVec);

    }


    public Vector2f getDisplVec() {
        return displVec;
    }

    public boolean isLeftButtonPress() {
        return leftButtonPress;
    }

    public boolean isRightButtonPress() {
        return rightButtonPress;
    }
}
