package com.simon.engine;

public abstract class AbstractGame {
    public abstract void init(GameContainer gc);
    public abstract void update(GameContainer gameContainer, float deltaTime);
    public abstract void render(GameContainer gameContainer, Renderer renderer);
}
