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

public class Help implements Screen {
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

    public Help(MyGdxGame game, Sound soundPause, BitmapFont styleFont) {
        this.game = game;
        this.soundPause = soundPause;
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("images/acceuil.png"));  // Ajoutez une image de fond pour le menu

        this.styleFont = styleFont;
        shapeRenderer = new ShapeRenderer();
        layout = new GlyphLayout();



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

        styleFont.draw(batch, "Retour", 40, 500);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        drawTextBorder("Retour", 400, 200);


        shapeRenderer.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.setScreen(new GameScreen(game, map1, map2, map3, 1, styleFont)); // Changez vers l'écran de jeu lorsque ENTER est pressé
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
