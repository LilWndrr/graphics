package com.seyf.core.rendering;

import com.seyf.core.Camera;
import com.seyf.core.lighting.DirectionalLight;
import com.seyf.core.lighting.PointLight;
import com.seyf.core.lighting.SpotLight;
import com.seyf.entity.Model;

public interface IRenderer<T> {
    public void init() throws Exception;
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight);
    abstract void bind(Model model);
    public void unbind();
    public void prepare(T t, Camera camera);
     public void cleanup();
}
