package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.BufferedReader;
import java.io.IOException;

public class MainMenuScreen implements Screen {
    final MyGdxGame game;
    private Texture background;
    private BitmapFont styleFont;
    public Sound soundPause;
    private SpriteBatch batch;
    private  Map map1;
    private  Map map2;
    private  Map map3;
    // Ajoutez ce membre à votre classe MainMenuScreen
    private ShapeRenderer shapeRenderer;
    private GlyphLayout layout;

    public MainMenuScreen(MyGdxGame game, Sound soundPause, BitmapFont styleFont) {
        this.game = game;
        this.soundPause = soundPause;
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("images/acceuil.png"));  // Ajoutez une image de fond pour le menu

        this.styleFont = styleFont;
        shapeRenderer = new ShapeRenderer();
        layout = new GlyphLayout();

        //Initialisation des map
        map1 = new Map();
        map2 = new Map();
        map3 = new Map();

        // Chargement des fichiers de map
        loadMap("ascii_art/new1.txt", map1);
        loadMap("ascii_art/new2.txt", map2);
        loadMap("ascii_art/new3.txt", map3);

    }

    private void loadMap(String filePath, Map map) {
        FileHandle file = Gdx.files.internal(filePath);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(file.reader())) {
                String line;
                while ((line = reader.readLine()) != null) {
                    map.addLine(line);
                }
            } catch (IOException e) {
                Gdx.app.log("MyGdxGame", "Error reading file: " + e.getMessage());
            }
        } else {
            Gdx.app.log("MyGdxGame", "File not found: " + filePath);
        }
    }
    @Override
    public void show() {
        if(soundPause != null)
            soundPause.loop(1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //font.draw(batch, "Scramble", 350, 400);
        styleFont.draw(batch, "Press 1 for level 1", 400, 200);
        styleFont.draw(batch, "Press 2 for level 2", 400, 250);
        styleFont.draw(batch, "Press 3 for level 3", 400, 300);
        styleFont.draw(batch, "?Help?", 40, 500);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        drawTextBorder("Press 1 for level 1", 400, 200);
        drawTextBorder("Press 2 for level 2", 400, 250);
        drawTextBorder("Press 3 for level 3", 400, 300);

        shapeRenderer.end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)){

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.setScreen(new GameScreen(game, map1, map2, map3, 1, styleFont)); // Changez vers l'écran de jeu lorsque ENTER est pressé
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            game.setScreen(new GameScreen(game, map1, map2, map3, 2, styleFont)); // Changez vers l'écran de jeu lorsque ENTER est pressé
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            game.setScreen(new GameScreen(game, map1, map2, map3, 3, styleFont)); // Changez vers l'écran de jeu lorsque ENTER est pressé
            dispose();
        }
    }

    private void drawTextBorder(String text, float x, float y) {
        // Utiliser GlyphLayout pour mesurer le texte
        layout.setText(styleFont, text);

        float width = layout.width;
        float height = layout.height;

        // Dessiner le rectangle autour du texte
        shapeRenderer.rect(x - 10, y - height - 10, width + 20, height + 20);

    }

    @Override
    public void resize(int width, int height) {
        map1.resize(width,height);
        map2.resize(width,height);
        map3.resize(width,height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        if(soundPause != null)
            soundPause.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
