package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Représente une sorcière dans le jeu.
 */
public class Witch {
    private Texture witch;
    private SpriteBatch batch;
    public int x, y;

    public Witch(SpriteBatch batch) {
        witch = new Texture(Gdx.files.internal("images/witch.png"));
        this.batch = batch;
        x = Gdx.graphics.getWidth() / 2 - witch.getWidth() / 2 - 100; // Position initiale au milieu, décalée vers la gauche
        y = Gdx.graphics.getHeight() / 2 - witch.getHeight() / 2; // Position initiale au centre verticalement
//        x = new Random().nextInt(Gdx.graphics.getWidth()); // Position initiale
//        y = new Random().nextInt(Gdx.graphics.getHeight()); // Position initiale
    }

    public void draw() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            x -= (int) (200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            x += (int) (200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            y += (int) (200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            y -= (int) (200 * Gdx.graphics.getDeltaTime());

        // Limiter la sorcière à l'écran
        /*if (x < 0)
            x = 0;
        if (x > Gdx.graphics.getWidth() - witch.getWidth())
            x = Gdx.graphics.getWidth() - witch.getWidth();*/
        if (y < 0)
            y = 0;
        if (y > Gdx.graphics.getHeight() - witch.getHeight())
            y = Gdx.graphics.getHeight() - witch.getHeight();

        batch.draw(witch, x, y);
    }

    public void update() {
        // Logique supplémentaire de mise à jour si nécessaire
    }

    public Rectangle getCollisionRectangle() {
        return new Rectangle(x, y, witch.getWidth(), witch.getHeight());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void dispose() {
        witch.dispose();
    }
}
