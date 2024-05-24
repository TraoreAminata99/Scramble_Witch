package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Map {
    private OrthographicCamera camera;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private Array<String> lines;
    private Texture backgroundTexture;
    private Texture brickTexture;
    private Texture moonTexture;
    private Texture starTexture;
    private float scrollX = 0; // Position de défilement
    int x;
    int y;
    public Map() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        lines = new Array<>();
        backgroundTexture = new Texture(Gdx.files.internal("images/ciel.jpg"));
        brickTexture = new Texture(Gdx.files.internal("images/brickBleuBic.jpg"));
        moonTexture = new Texture(Gdx.files.internal("images/brikChocolat.jpg"));
    }

    public void addLine(String line) {
        lines.add(line);
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        // Dessine la texture de fond
        spriteBatch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.end();

        int charWidth = 16; // Largeur approximative d'un caractère
        int charHeight = 16; // Hauteur approximative d'un caractère
        int startX = 10; // Position de départ en X
        int screenHeight = Gdx.graphics.getHeight();
        int screenWidth = Gdx.graphics.getWidth();

        // Mise à jour de la position de défilement
        scrollX += Gdx.graphics.getDeltaTime() * 50; // Vitesse de défilement
        if (scrollX >= charWidth) {
            scrollX -= charWidth;
            // Déplace le premier caractère de chaque ligne à la fin de celle-ci
            for (int i = 0; i < lines.size; i++) {
                String line = lines.get(i);
                line = line.substring(1) + line.charAt(0);
                lines.set(i, line);
            }
        }

        spriteBatch.begin();
        for (int i = 0; i < lines.size; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                // Ajustement de la position x
                int x = startX + (j * charWidth) - (int) scrollX;
                int y = screenHeight - (i * charHeight);

                switch (c) {
                    case '*':
                        spriteBatch.draw(brickTexture, x, y - charHeight, charWidth, charHeight);
                        break;
                    case '+':
                        spriteBatch.draw(moonTexture, x, y - charHeight, charWidth, charHeight);
                        break;
//                    case 'C':
//                        spriteBatch.draw(starTexture, x, y - charHeight, charWidth, charHeight);
//                        break;
                    default:
                        // Dessine le caractère par défaut
                        font.draw(spriteBatch, String.valueOf(c), x, y);
                }
            }
        }
        spriteBatch.end();
    }
    int getX(){return x;}
    int getY(){
        return y;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        backgroundTexture.dispose();
        brickTexture.dispose();
        moonTexture.dispose();
        starTexture.dispose();
    }

    //
    public Array<Rectangle> getCollisionRectangles() {
        Array<Rectangle> collisionRectangles = new Array<>();

        int charWidth = 16; // Largeur approximative d'un caractère
        int charHeight = 16; // Hauteur approximative d'un caractère
        int startX = 10; // Position de départ en X
        int screenHeight = Gdx.graphics.getHeight();

        for (int i = 0; i < lines.size; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                x = startX + (j * charWidth) - (int) scrollX;
                y = screenHeight - (i * charHeight) - charHeight;

                if (c == '*' || c == '+') {
                    collisionRectangles.add(new Rectangle(x, y, charWidth, charHeight));
                }
            }
        }
        return collisionRectangles;
    }



}
