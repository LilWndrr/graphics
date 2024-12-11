    package com.seyf.test;

    import com.seyf.core.*;
    import com.seyf.core.lighting.DirectionalLight;
    import com.seyf.core.lighting.PointLight;
    import com.seyf.core.lighting.SpotLight;
    import com.seyf.core.rendering.RenderManager;
    import com.seyf.core.terrain.Terrain;
    import com.seyf.core.utils.Consts;
    import com.seyf.entity.*;
    import org.joml.Vector2f;
    import org.joml.Vector3f;
    import org.joml.Vector4f;
    import org.lwjgl.glfw.GLFW;
    import org.lwjgl.opengl.GL11;

    public class TestGame implements ILogic {




        private float colour= 0.0f;

        private final RenderManager renderer;
        private final WindowManager window;
        private final ObjectLoader loader;
        private final Camera camera;

        private SceneManager sceneManager;

        Vector3f cameraInc;



        public TestGame(){
            renderer= new RenderManager();
            window= Launcher.getWindow();
            loader=new ObjectLoader();
            camera= new Camera();
            cameraInc = new Vector3f(0,0,0);
            sceneManager= new SceneManager(-90);
        }
        @Override
        public void init() throws Exception {
            renderer.init();

            camera.setPosition(0,2,1.5f);
            /*Terrain terrain= new Terrain(new Vector3f(0,1,-800),loader,
                    new Material(new Texture(loader.loadTexture("textures/terrain.jpg")),0.1f));
            Terrain terrain2= new Terrain(new Vector3f(-800,1,-800),loader,
                    new Material(new Texture(loader.loadTexture("textures/flowers.jpg")),0.1f));
            sceneManager.addTerrain(terrain);
            sceneManager.addTerrain(terrain2);*/

            Model cubeModel= loader.loadOBJModel("/models/cube.obj");
            //cubeModel.setTexture(new Texture(loader.loadTexture("textures/gold.png")), 0.2f);
            cubeModel.setMaterial(new Material(new Vector4f(0.2f, 0.2f, 0.2f, 1f), new Vector4f(1.0f, 0.84f, 0.0f, 1f), new Vector4f(1.0f, 1.0f, 1.0f, 1f), 0.9f, new Texture(loader.loadTexture("textures/gold.png"))));

            sceneManager.addEntity(new Entity(cubeModel, new Vector3f(0,2f,-2.5f),new Vector3f(0,0,0),1f));

            Model piramidModel= loader.loadOBJModel("/models/piramid.obj");

            piramidModel.setTexture(new Texture(loader.loadTexture("textures/steel.jpg")), 1f);

            sceneManager.addEntity(new Entity(piramidModel, new Vector3f(0,3f,-2.5f),new Vector3f(0,0,0),1f));

            Model piramidModel2= loader.loadOBJModel("/models/3fpiramid.obj");
            piramidModel2.setTexture(new Texture(loader.loadTexture("textures/steel.jpg")), 1f);
            piramidModel2.setMaterial(new Material( new Vector4f(0.1f, 0.1f, 0.1f, 1.0f), new Vector4f(0.7f, 0.7f, 0.7f, 1.0f), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 0.9f, null));
            sceneManager.addEntity(new Entity(piramidModel2, new Vector3f(3,1f,-2.5f),new Vector3f(0,0,0),1f));


            Model sphereModel= loader.loadOBJModel("/models/sphere.obj");
            sphereModel.setTexture(new Texture(loader.loadTexture("textures/red.jpg")), 10f);

            sceneManager.addEntity(new Entity(sphereModel, new Vector3f(2,5f,-2.5f),new Vector3f(0,0,0),-0.3f));

            Model wallModel= loader.loadOBJModel("/models/wall.obj");
            wallModel.setTexture(new Texture(loader.loadTexture("textures/wall.jpg")), 1f);
            Model floorModel= loader.loadOBJModel("/models/wall.obj");
            floorModel.setTexture(new Texture(loader.loadTexture("textures/wood_floor.jpg")), 1f);
            sceneManager.addEntity(new Entity(floorModel,new Vector3f(-2,1f,-5f),new Vector3f(300,0,0),2));
            sceneManager.addEntity(new Entity(wallModel,new Vector3f(-2,1f,-5f),new Vector3f(0,0,0),2));
            sceneManager.addEntity(new Entity(wallModel,new Vector3f(-2,1f,1f),new Vector3f(0,300,0),2));
            sceneManager.addEntity(new Entity(wallModel,new Vector3f(4,1f,1f),new Vector3f(0,300,0),2));

           /* Model model= loader.loadOBJModel("/models/f16.obj");
            model.setTexture(new Texture(loader.loadTexture("textures/wall.jpg")), 1f);
            sceneManager.addEntity(new Entity(model,new Vector3f(0,1.5f,-2.5f),new Vector3f(0,0,0),1));*/



           /* Random rnd= new Random();
            for(int i= 0; i<100; i++){
                float x= rnd.nextFloat()*100-50;

                float z= rnd.nextFloat()*-300;
                Entity entity =new Entity(model, new Vector3f(x,2,z),
                        new Vector3f(0,0,0),1);
                sceneManager.addEntity(entity);



            }*/




            //PointLight
            float lightIntensity= 1.0f;
            Vector3f lightPosition= new Vector3f(0,2f,-2.5f);
            Vector3f lightColor= new Vector3f(1,1,1);
            PointLight pointLight=new PointLight(lightColor,lightPosition,lightIntensity,0,0,1);

            //SpotLight
            Vector3f coneDir= new Vector3f(1,1,10);
            Vector3f coneDir2= new Vector3f(3,1,5);

            float cutoff= (float) Math.cos(Math.toRadians(40));
            lightIntensity=5f;
            SpotLight spotLight = new SpotLight(new PointLight(new Vector3f(0f,0,0), new Vector3f(0,2f,-2.5f),lightIntensity,0,0,0.2f),coneDir,cutoff);

            Vector3f pyramidPosition = new Vector3f(0, 3f, -2.5f);
            Vector3f spherePosition = new Vector3f(2, 5f, -2.5f);
            // Calculate direction vector from sphere to pyramid
            Vector3f directionToPyramid = new Vector3f(
                    pyramidPosition.x - spherePosition.x,
                    pyramidPosition.y - spherePosition.y,
                    pyramidPosition.z - spherePosition.z
            );
            directionToPyramid.normalize();


            SpotLight spotLight1 = new SpotLight(new PointLight(new Vector3f(0.25f,0f,0), new Vector3f(2,5f,-2.5f),lightIntensity,0,0,0.2f),directionToPyramid,cutoff);
            //spotLight1.getPointLight().setPosition(new Vector3f(0.5f,0.5f,-3.6f));


            //DirectionalLight
             lightPosition= new Vector3f(10f,500f,10f);
             lightColor= new Vector3f(1,1,1);
            sceneManager.setDirectionalLight(new DirectionalLight(lightColor,lightPosition,0.00005f)) ;
            sceneManager.setPointLights(new PointLight[]{pointLight});
            SpotLight sphereSpotLight =  new SpotLight(
                    new PointLight(new Vector3f(1, 1, 1), new Vector3f(2, 8f, -2.5f), 10f, 0, 0, 0.2f),
                    new Vector3f(0, -1, 0), (float) Math.cos(Math.toRadians(10))
            );;
            sceneManager.setSpotLights(new SpotLight[]{spotLight,spotLight1,sphereSpotLight}) ;
        }

        @Override
        public void input() {

            cameraInc.set(0,0,0);
            if(window.isKeyPressed(GLFW.GLFW_KEY_W)){
                cameraInc.z=-3;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_S)){
                cameraInc.z=3;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_A)){
                cameraInc.x=-3;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_D)){
                cameraInc.x=3;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_Z)){
                cameraInc.y=-3;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_X)){
                cameraInc.y=3;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_RIGHT)){
                sceneManager.getPointLights()[0].getPosition().x+=0.001f;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT)){
                sceneManager.getPointLights()[0].getPosition().x-=0.001f;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_UP)){
                sceneManager.getPointLights()[0].getPosition().y+=0.001f;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_DOWN)){
                sceneManager.getPointLights()[0].getPosition().y-=0.001f;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_P)) {
                sceneManager.getPointLights()[0].setIntensity(sceneManager.getPointLights()[0].getIntensity()+0.05f);
            } if(window.isKeyPressed(GLFW.GLFW_KEY_O)) {
                sceneManager.getPointLights()[0].setIntensity(sceneManager.getPointLights()[0].getIntensity()-0.05f);
            }

            float lightPos= sceneManager.getSpotLights()[0].getPointLight().getPosition().z;
            if(window.isKeyPressed(GLFW.GLFW_KEY_N)){
                sceneManager.getSpotLights()[0].getPointLight().getPosition().z= lightPos+0.1f;
            }
            if(window.isKeyPressed(GLFW.GLFW_KEY_M)){
                sceneManager.getSpotLights()[0].getPointLight().getPosition().z= lightPos-0.1f;
            }

        }

        @Override
        public void update( MouseInput mouseInput) {

            camera.movePosition(cameraInc.x* Consts.CAMERA_MOVE_SPEED,cameraInc.y*Consts.CAMERA_MOVE_SPEED, cameraInc.z*Consts.CAMERA_MOVE_SPEED);
            if(mouseInput.isLeftButtonPress()){
                Vector2f rotVec= mouseInput.getDisplVec();
                System.out.println("Mouse rotation vector: " + rotVec);
                camera.moveRotation(rotVec.x*Consts.MOUSE_SENSITIVITY, rotVec.y*Consts.MOUSE_SENSITIVITY,0);
            }

            //entity.incRotation(0,0.25f,0);
            sceneManager.incSpotAngle(0.15f);

            if(sceneManager.getSpotAngle()>4){
                sceneManager.setSpotInc(-1) ;
            } else if (sceneManager.getSpotAngle()<=-4) {
                sceneManager.setSpotInc(1);
            }

            double spotAngleRad= Math.toRadians(sceneManager.getSpotAngle());
            Vector3f coneDir= sceneManager.getSpotLights()[0].getPointLight().getPosition();
            coneDir.x= (float) Math.sin(spotAngleRad);

            coneDir= sceneManager.getSpotLights()[1].getPointLight().getPosition();
            coneDir.x= (float) Math.cos(spotAngleRad);

            sceneManager.incLightAngle(1.1f);
            if(sceneManager.getLightAngle()>90){
                sceneManager.getDirectionalLight().setIntensity(0);
                if(sceneManager.getLightAngle()>=360){
                    sceneManager.setLightAngle(-90);
                }
            }else if ( sceneManager.getLightAngle()<=-80|| sceneManager.getLightAngle()>=80){
                float factor= 1- (Math.abs(sceneManager.getLightAngle())-80)/10.0f;
                sceneManager.getDirectionalLight().setIntensity(factor);
                sceneManager.getDirectionalLight().getColor().y=Math.max(factor,0.9f);
                sceneManager.getDirectionalLight().getColor().z=Math.max(factor,0.5f);
            }else {
                sceneManager.getDirectionalLight().setIntensity(1);
                sceneManager.getDirectionalLight().getColor().x=1;
                sceneManager.getDirectionalLight().getColor().y=1;
                sceneManager.getDirectionalLight().getColor().z=1;
            }
            double angRad= Math.toRadians(sceneManager.getLightAngle());
            sceneManager.getDirectionalLight().getDirection().x= (float) Math.sin(angRad);
            sceneManager.getDirectionalLight().getDirection().y= (float) Math.sin(angRad);
            for(Entity e: sceneManager.getEntities()){
                renderer.processEntity(e);
            }
            for(Terrain terrain: sceneManager.getTerrains()){
                renderer.processTerrain(terrain);
            }
        }

        @Override
        public void render() {
            if(window.isResize()){
                GL11.glViewport(0,0,window.getWidth(),window.getHeight());
                window.setResize(true);
            }

            window.setClearColor(colour,colour,colour,0.1f);
            renderer.render(camera, sceneManager);
        }

        @Override
        public void cleanup() {
            renderer.cleanup();
            loader.cleanUp();
        }
    }
