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
    private Texture vie;
    private Texture monster;
    private Texture rocket;
    private Texture direction;
    private Texture pomme;
    private Texture bomb;
    private BitmapFont styleFont;
    private SpriteBatch batch;
    public Sound soundPause;

    public Help(MyGdxGame game, BitmapFont styleFont) {
        this.game = game;
        this.styleFont = styleFont;
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("images/images.jpeg")); // Remplacez par votre image de fond
        vie = new Texture(Gdx.files.internal("images/vie.png"));
        monster = new Texture(Gdx.files.internal("images/monster.png"));
        rocket = new Texture(Gdx.files.internal("images/rocket.png"));
        bomb = new Texture(Gdx.files.internal("images/bomb.png"));
        direction = new Texture(Gdx.files.internal("images/direction.png"));
        pomme = new Texture(Gdx.files.internal("images/apple.png"));
        soundPause = Gdx.audio.newSound(Gdx.files.internal("music/menu.mp3"));
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

        batch.draw(bomb,150, 80, 120, 150);
        batch.draw(rocket,100, 300 - 90, 200, 200);
        batch.draw(monster,700, 300, 200, 100);
        batch.draw(pomme,700, 80, 90, 90);
        styleFont.draw(batch, "------> score +1 or ", 340 , 345);
        styleFont.draw(batch, "------> score +2", 280 , 150);
        styleFont.draw(batch, "------> ", 280 , 110);
        batch.draw(vie,518, 320, 45, 45);
        styleFont.draw(batch, " = 0", 390 , 110);
        batch.draw(vie,350, 90, 40, 20);
        styleFont.draw(batch, " -1 <------ ", 570 , 345);
        styleFont.draw(batch, " +1 <------ ", 590 , 130);
        batch.draw(vie,552, 115, 40, 20);
        styleFont.draw(batch, " Movement : ", 450 , 280);
        batch.draw(direction,436,136,140,150);
        styleFont.draw(batch, " Press esc to exit", 2 , 20);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game, soundPause, styleFont));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
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
