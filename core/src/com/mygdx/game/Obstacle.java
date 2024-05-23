package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;


public class Obstacle {
    float x, y;
    float size;
    Circle circle;
    Texture texture;
    Random random;

    public Obstacle(float x, float y, float size, String path){
        this.x = x;
        this.y = y;
        this.size = size;
        this.circle = new Circle();
        texture = new Texture(Gdx.files.internal(path));
        random = new Random();
    }
    public void update(float deltaTime, int vitesse) {
        // Vitesse de déplacement vers le haut
        y += vitesse * deltaTime;
        if(random.nextBoolean())
            x += random.nextFloat();
        else
            x -= random.nextFloat();
        circle.set(x, y, size );
    }

    public void draw(SpriteBatch spriteBatch) {

        spriteBatch.draw(texture, x, y, size, size);
    }

    public Rectangle getCollisionRectangle() {
        return new Rectangle(x , y, size, size);
    }

    public Circle getCircle() {
        return this.circle;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
    public void dispose() {
        texture.dispose();
    }

}
