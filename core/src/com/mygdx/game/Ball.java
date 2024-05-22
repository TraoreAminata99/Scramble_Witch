package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    private float x, y; // Position de la balle
    private float size; // Taille de la balle
    private float yspeed, xspeed; // Vitesses en x et y
    private Circle circle; // Représentation circulaire de la balle pour la détection des collisions
    Texture texture; // Texture de la balle

    public static final String PATH = "M.png";

    public Ball(String path, float x, float y, float size, float yspeed, float xspeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.yspeed = yspeed;
        this.xspeed = xspeed;
        this.circle = new Circle();
        texture = new Texture(Gdx.files.internal(path));
    }

        public static Ball shootBalls(float x, float y) {
        // Vitesse horizontale pour chaque balle (positive pour se déplacer vers la droite)
        float ballSpeedX = 5; // Vitesse en x
        float ySpeed = 0; // Vitesse verticale (fixée à 0 pour ce scénario)

        // Créer trois balles avec des positions initiales différentes
        return new Ball("images/badlogic.jpg", x - 20, y, 12, ySpeed, ballSpeedX);
        //Ball ballMiddle = new Ball("badlogic.jpg", x, y, 12, ySpeed, ballSpeedX);
        //Ball ballRight = new Ball("badlogic.jpg", x + 20, y, 12, ySpeed, ballSpeedX);

        //balls.add(ballLeft);
        //balls.add(ballMiddle);
        //balls.add(ballRight);
    }
    public void update() {
        // Mettre à jour la position de la balle
        y += yspeed;
        x += xspeed;
        circle.set(x, y, size / 2);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        circle.set(x, y, size / 2);
    }

    public void draw(SpriteBatch spriteBatch) {
        // Dessiner la texture de la balle
        spriteBatch.draw(texture, x, y, size, size);
    }

    public Rectangle getCollisionRectangle() {
        return new Rectangle(x - size / 2, y - size / 2, size, size);
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
