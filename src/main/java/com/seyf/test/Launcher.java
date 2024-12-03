package com.seyf.test;

import com.seyf.core.EngineManager;
import com.seyf.core.WindowManager;
import com.seyf.core.utils.Consts;
import org.lwjgl.Version;

public class Launcher {

    private static WindowManager window;
    private static EngineManager engine;

    private static TestGame game;

        public static void main(String[] args) {

            window = new WindowManager(Consts.TITLE, 1600,900,false);
            game=new TestGame();
            EngineManager engine = new EngineManager();

            try {
                engine.start();
            }catch (Exception ex){
                ex.printStackTrace();
            }



        }

    public static WindowManager getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}
