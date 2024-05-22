package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class Apple {
    private float x, y, size;
    private Circle circle;
    private Texture texture;
    public Apple(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
        texture = new Texture("images/apple.png");
        circle = new Circle(x, y, size / 2);
    }


    public Rectangle getCollisionRectangle() {
        return new Rectangle(x - size / 2, y - size / 2, size, size);
    }
    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(texture, x, y, size, size);
   }
    public void update(float deltaTime, int vitesse) {
        y += deltaTime;
        x -= vitesse * deltaTime;
        circle.set(x, y, size / 2);
    }

    public void dispose() {
        texture.dispose();
    }
}
