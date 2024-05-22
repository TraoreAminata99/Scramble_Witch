package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Monster extends Obstacle {
    public Monster(float x, float y, float size) {
        super(x,y,size, "images/monster.png");
    }
    public void update(float deltaTime, int vitesse) {
        // Utilisez les fonctions trigonométriques pour simuler un mouvement oscillant
        float movement = (float) Math.sin(Gdx.graphics.getFrameId() * 20) * 70;
        y += movement * deltaTime;
        x -= vitesse * deltaTime;
        circle.set(x, y, size / 2);
    }
}
