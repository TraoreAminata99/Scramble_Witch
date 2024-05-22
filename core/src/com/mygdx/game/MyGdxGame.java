package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/*import java.io.BufferedReader;
import java.io.IOException;
*/
public class MyGdxGame extends Game {
	SpriteBatch batch;

	BitmapFont font;
	BitmapFont styleFont;


	@Override
	public void create() {
		Sound soundPause = Gdx.audio.newSound(Gdx.files.internal("music/menu.mp3"));
		batch = new SpriteBatch();


		// Générer la police avec FreeTypeFontGenerator
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/roboto/Roboto-Black.ttf"));
			// Générer la police stylisée pour Score, Lives et Level
		FreeTypeFontGenerator.FreeTypeFontParameter styleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		styleParameter.size = 16; // Taille de la police stylisée
		styleParameter.borderWidth = 2; // Épaisseur de la bordure
		styleParameter.borderColor = Color.GOLDENROD; // Couleur de la bordure
		styleParameter.color = Color.BLACK; // Couleur du texte
		styleFont = generator.generateFont(styleParameter);

		this.setScreen(new MainMenuScreen(this, soundPause, styleFont));
	}




	@Override
	public void render() {
		super.render(); // Important de garder cette ligne pour que l'écran actuel soit rendu
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
