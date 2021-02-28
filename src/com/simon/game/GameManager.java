package com.simon.game;

import com.simon.engine.AbstractGame;
import com.simon.engine.GameContainer;
import com.simon.engine.Renderer;
import com.simon.engine.audio.SoundClip;
import com.simon.engine.gfx.Image;
import com.simon.engine.gfx.ImageTile;
import com.simon.engine.gfx.Light;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameManager extends AbstractGame {
    private int[] collision;
    private int levelW, levelH;
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public GameManager(){
        objects.add(new Player(2,2));
    }

    @Override
    public void init(GameContainer gameContainer){
       gameContainer.getRenderer().setAmbientColor(-1);
    }

    @Override
    public void update(GameContainer gameContainer, float deltaTime) {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update(gameContainer, deltaTime);
            if (objects.get(i).isDead()){
                objects.remove(i);
                i--; //Prevent next object from skipping while removing object from iterating ArrayList
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Renderer renderer) {

        for (int y = 0; y < levelH; y++) {
            for (int x = 0; x < levelW; x++) {
                if (collision[x + y * levelW] == 1){
                    renderer.drawFillRect(x * 16, y * 16, 16, 16, 0xff0f0f0f);
                } else {
                    renderer.drawFillRect(x * 16, y * 16, 16, 16, 0xff9f9f9f);
                }

            }
        }

        for (GameObject obj : objects){
            obj.render(gameContainer, renderer);
        }
    }

    public void loadLevel(String path){
        Image levelImage = new Image(path);

        levelW = levelImage.getW();
        levelH = levelImage.getH();
        collision = new int[levelW * levelH];

        for (int y = 0; y < levelImage.getH(); y++) {
            for (int x = 0; x < levelImage.getW(); x++) {
                if (levelImage.getP()[x + y * levelImage.getW()] == 0xff000000){
                    collision[x + y * levelImage.getW()] = 1;
                } else {
                    collision[x + y * levelImage.getW()] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.setScale(3f);
        gc.start();
    }
}
