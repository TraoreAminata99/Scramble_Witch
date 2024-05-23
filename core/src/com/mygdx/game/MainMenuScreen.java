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
import com.badlogic.gdx.math.Rectangle;

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
    private Rectangle level1Rect;
    private Rectangle level2Rect;
    private Rectangle level3Rect;
    private Rectangle helpRect;

    public MainMenuScreen(MyGdxGame game, Sound soundPause, BitmapFont styleFont) {
        this.game = game;
        this.soundPause = soundPause;
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("images/font1.jpg"));  // Ajoutez une image de fond pour le menu

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

        layout.setText(styleFont, "Press 1 for level 1");
        level1Rect = new Rectangle(400, 200 - layout.height, layout.width, layout.height);

        layout.setText(styleFont, "Press 2 for level 2");
        level2Rect = new Rectangle(400, 250 - layout.height, layout.width, layout.height);

        layout.setText(styleFont, "Press 3 for level 3");
        level3Rect = new Rectangle(400, 300 - layout.height, layout.width, layout.height);

        layout.setText(styleFont, "Press 0 for Help");
        helpRect = new Rectangle(40, 500 - layout.height, layout.width, layout.height);

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
        styleFont.draw(batch, "Level 1", 450, 200);
        styleFont.draw(batch, "Level 2", 450, 250);
        styleFont.draw(batch, "Level 3", 450, 300);
        styleFont.draw(batch, "click for Help", 40, 500);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        drawTextBorder("Level 1", 450, 200);
        drawTextBorder("Level 2", 450, 250);
        drawTextBorder("Level 3", 450, 300);

        shapeRenderer.end();
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inverser la coordonnée Y

            if (level1Rect.contains(mouseX, mouseY)) {
                game.setScreen(new GameScreen(game, map1, map2, map3, 1, styleFont));
                dispose();
            } else if (level2Rect.contains(mouseX, mouseY)) {
                game.setScreen(new GameScreen(game, map1, map2, map3, 2, styleFont));
                dispose();
            } else if (level3Rect.contains(mouseX, mouseY)) {
                game.setScreen(new GameScreen(game, map1, map2, map3, 3, styleFont));
                dispose();
            } else if (helpRect.contains(mouseX, mouseY)) {
                game.setScreen(new Help(game, styleFont));
                dispose();
            }
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
