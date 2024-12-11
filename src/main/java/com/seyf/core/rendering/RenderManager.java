package com.seyf.core.rendering;

import com.seyf.core.Camera;
import com.seyf.core.ShaderManager;
import com.seyf.core.WindowManager;
import com.seyf.core.lighting.DirectionalLight;
import com.seyf.core.lighting.PointLight;
import com.seyf.core.lighting.SpotLight;
import com.seyf.core.terrain.Terrain;
import com.seyf.core.utils.Consts;
import com.seyf.entity.Entity;
import com.seyf.entity.SceneManager;
import com.seyf.test.Launcher;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderManager  {

    private final WindowManager window;
    private EntityRenderer entityRenderer;
    private TerrainRenderer terrainRenderer;


    public RenderManager(){
        window= Launcher.getWindow();
    }

    public void init() throws Exception{
        entityRenderer = new EntityRenderer();
        terrainRenderer = new TerrainRenderer();
        entityRenderer.init();
        terrainRenderer.init();


    }



    public static void renderLights(PointLight[] pointLights,SpotLight[] spotLights,DirectionalLight directionalLight, ShaderManager shader){
        shader.setUniform("ambientLight", Consts.AMBIENT_LIGHT);
        shader.setUniform("specularPower",Consts.SPECULAR_POWER);
        int numLights= spotLights!=null?spotLights.length:0;
        for (int i=0;i<numLights;i++){
            shader.setUniform("spotLights", spotLights[i],i);
        }
        numLights= pointLights!=null?pointLights.length:0;
        for (int i=0;i<numLights;i++){
            shader.setUniform("pointLights", pointLights[i],i);
        }
        shader.setUniform("directionalLight", directionalLight);
    }

    public void render(Camera camera, SceneManager scene){
        clear();

        entityRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
        terrainRenderer.render(camera,scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
    }

    public void processEntity(Entity entity){
        List<Entity> entityList= entityRenderer.getEntities().get(entity.getModel());
        if(entityList!=null){
            entityList.add(entity);
        }else{
            List<Entity> newEntityList= new ArrayList<>();
            newEntityList.add(entity);
            entityRenderer.getEntities().put(entity.getModel(),newEntityList);
        }
    }

    public void processTerrain(Terrain terrain){
        terrainRenderer.getTerrains().add(terrain);
    }

    public void clear(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup(){
        entityRenderer.cleanup();
        terrainRenderer.cleanup();
    }

}
