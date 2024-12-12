package com.seyf.entity;

import com.seyf.core.lighting.DirectionalLight;
import com.seyf.core.lighting.PointLight;
import com.seyf.core.lighting.SpotLight;
import com.seyf.core.terrain.Terrain;
import com.seyf.core.utils.Consts;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {

    private List<Entity> entities;
    private List<Terrain> terrains;

    private Vector3f ambientLight;
    private SpotLight[] spotLights;
    private PointLight[] pointLights;
    private DirectionalLight directionalLight;

    private float lightAngle;
    private float spotAngle;
    private float spotInc;

    public SceneManager(float lightAngle) {
        this.lightAngle = lightAngle;
        entities = new ArrayList<Entity>();
        terrains = new ArrayList<>();
        ambientLight= Consts.AMBIENT_LIGHT;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }
    public float getSpotInc() {
        return spotInc;
    }

    public void setSpotInc(float spotInc) {
        this.spotInc = spotInc;
    }

    public float getSpotAngle() {
        return spotAngle;
    }

    public void setSpotAngle(float spotAngle) {
        this.spotAngle = spotAngle;
    }

    public float getLightAngle() {
        return lightAngle;
    }

    public void setLightAngle(float lightAngle) {
        this.lightAngle = lightAngle;
    }

    public DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public void setDirectionalLight(DirectionalLight directionalLight) {
        this.directionalLight = directionalLight;
    }

    public PointLight[] getPointLights() {
        return pointLights;
    }

    public void setPointLights(PointLight[] pointLights) {
        this.pointLights = pointLights;
    }

    public SpotLight[] getSpotLights() {
        return spotLights;
    }

    public void setSpotLights(SpotLight[] spotLights) {
        this.spotLights = spotLights;
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(Vector3f ambientLight) {
        this.ambientLight = ambientLight;
    }
    public void setAmbientLight(float x, float y, float z) {
        ambientLight = new Vector3f(x,y,z);
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public void addTerrain(Terrain terrain) {
        this.terrains.add(terrain);
    }

    public void incLightAngle(float v) {
        this.lightAngle += v;
    }

    public void incSpotAngle(float v) {
        this.spotAngle *= v;
    }
}
