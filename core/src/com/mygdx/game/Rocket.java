package com.mygdx.game;


import com.badlogic.gdx.Gdx;

public class Rocket extends Obstacle{
    public Rocket(float x, float y, float size) {
        super(x,y,size,"images/rocket.png");
    }
    public void update(float deltaTime, int vitesse, int level) {
        // Vitesse de déplacement vers le haut
        y += vitesse * deltaTime;

        // Déplacement horizontal aléatoire
        if(level == 3) x += (random.nextFloat() - 0.5f) * 10 * vitesse * deltaTime;

        // Assurez-vous que la rocket reste dans les limites de l'écran
        if (x < 0) {
            x = 0;
        } else if (x + size > Gdx.graphics.getWidth()) {
            x = Gdx.graphics.getWidth() - size;
        }

        // Mettre à jour le cercle de collision
        circle.set(x, y, size);
    }

}
