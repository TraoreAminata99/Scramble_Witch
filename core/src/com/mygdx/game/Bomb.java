package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bomb extends Obstacle {
    private Rectangle collisionRectangle;

    public Bomb(float x, float y, float size) {
        super(x, y, size,"images/bomb.png");
        collisionRectangle = new Rectangle(x, y, size/3, size/3); // Set collision rectangle dimensions
    }

    @Override
    public Rectangle getCollisionRectangle() {
        collisionRectangle.setPosition(x, y); // Update position
        return collisionRectangle;
    }

    public void update(float deltaTime, float speed) {
        y -= speed * deltaTime;
        collisionRectangle.setPosition(x, y); // Update position in collision rectangle
    }

}
