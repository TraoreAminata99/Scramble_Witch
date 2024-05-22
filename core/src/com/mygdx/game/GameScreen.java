package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameScreen implements Screen {
    MyGdxGame game;
    private Witch witch;
    private BitmapFont styleFont;

    private Array<Ball> balls;
    private Array<Rocket> rockets;
    private Array<Obstacle> obstalceStatic;
    private Array<Apple> apples;
    private OrthographicCamera camera;
    private Texture background;
    private SpriteBatch batch;
    private  Map map1;
    private  Map map2;
    private  Map map3;
    private Map currentMap;
    private int backgroundY;
    private float previousMouseX; // Position précédente de la souris en X
    private float previousMouseY; // Position précédente de la souris en Y
    final float SHOOT_INTERVAL = 0.1f; // Intervalle de tir en secondes
    private float timeSinceLastShot;
    float MONSTER_INTERVAL = 1.0f;
    private float timeSinceLastMonster = 0;
    private float timeSinceLastObstacle = 0;
    private float timeSinceLastApple = 0;

    private float OBSTACLE_INTERVAL = 1.0f;
    private static final float APPLE_INTERVAL = 5.0f;
    public final Sound soundPause;
    private final Sound gameSound;
    private final Sound soundBall;
    private final Sound soundMonsterDie;
    private final Sound boom;
    private final Sound nextlevel;
    private final Sound eat;
    private final Sound menu;
    public boolean gameOver = false;
    public boolean isPaused = false;
    private int score;
    private int level;
    private int live;
    private float deltaTime;

    public GameScreen(MyGdxGame game, Map map1, Map map2, Map map3, int level, BitmapFont styleFont) {
        this.game = game;
        camera = new OrthographicCamera();
        batch = game.batch;
        soundBall = Gdx.audio.newSound(Gdx.files.internal("music/pistoler.mp3"));
        soundPause = Gdx.audio.newSound(Gdx.files.internal("music/bataille.mp3"));
        soundMonsterDie = Gdx.audio.newSound(Gdx.files.internal("music/diedbyBall.mp3"));
        nextlevel = Gdx.audio.newSound(Gdx.files.internal("music/next_level.mp3"));
        gameSound = Gdx.audio.newSound(Gdx.files.internal("music/game.mp3"));
        eat = Gdx.audio.newSound(Gdx.files.internal("music/eat.mp3"));
        menu = Gdx.audio.newSound(Gdx.files.internal("music/menu.mp3"));
        boom = Gdx.audio.newSound(Gdx.files.internal("music/boom.mp3"));

        witch = new Witch(batch);

        deltaTime = Gdx.graphics.getDeltaTime();
        score = 0;
        this.level = level;
        live = 3;
        rockets = new Array<Rocket>();
        obstalceStatic = new Array<>();
        apples = new Array<Apple>();
        background = new Texture(Gdx.files.internal("images/badlogic.jpg"));
        backgroundY = 0;
        camera.setToOrtho(false, 800, 480);

        balls = new Array<Ball>();
        previousMouseX = Gdx.input.getX();
        previousMouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        this.styleFont = styleFont;


        this.map1 = map1;
        this.map2=map2;
        this.map3=map3;
        this.currentMap=map1;
        updateMap();
    }

    public void resetGame() {
        if (witch != null) {
            witch.dispose();
        }
        witch = new Witch(batch);
        if (balls != null) {
            for (Ball ball : balls) {
                ball.dispose();
            }
        }
        if (rockets != null) {
            for (Rocket rocket : rockets) {
                rocket.dispose();
            }
        }
        score = 0;
        balls = new Array<>();
        rockets = new Array<>();
        timeSinceLastShot = 0;
        timeSinceLastMonster = 0;
        gameOver = false;
        isPaused = false;
        live = 3;
        soundPause.pause();
    }

    @Override
    public void render(float delta) {
        if (gameOver) {
            gameSound.pause();
            batch.begin();
            styleFont.draw(batch, "Game Over", Gdx.graphics.getWidth()/2-30, 300);
            styleFont.draw(batch, "Score : " + score, Gdx.graphics.getWidth()/2-22, 270);
            styleFont.draw(batch, "Press 'R' to restart or 'H' to go to Home", Gdx.graphics.getWidth()/2-53-90, 240);
            batch.end();
            if (Gdx.input.isKeyJustPressed(Input.Keys.R))
                resetGame();

            if (Gdx.input.isKeyJustPressed(Input.Keys.H))
                game.setScreen(new MainMenuScreen(game, menu, styleFont));

            return;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            isPaused = true;
            soundPause.play();
            //gameSound.pause();
            return;
        }
        if (isPaused) {
            batch.begin();
            styleFont.draw(batch, "Game Paused", Gdx.graphics.getWidth()/2-30, 270);
            styleFont.draw(batch, "Press any key to resume", Gdx.graphics.getWidth()/2-53-30, 220);
            batch.end();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
                live--;
                isPaused = false;
                soundPause.stop();
            }
            return;
        }

        // Mettre à jour le temps écoulé
        timeSinceLastShot += deltaTime;
        timeSinceLastMonster += deltaTime;
        timeSinceLastApple += delta;
        float mouseX = witch.getX() + 90;
        float mouseY = witch.getY() + 5; // Inverser la coordonnée Y

        // Vérifier si le temps depuis le dernier tir est suffisant
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (timeSinceLastShot >= SHOOT_INTERVAL) {
                balls.add(Ball.shootBalls(mouseX, mouseY));
                timeSinceLastShot = 0;
                soundBall.play();
            }
        }

        if (timeSinceLastApple >= APPLE_INTERVAL) {
            apples.add(new Apple((float) Math.random() * Gdx.graphics.getWidth(), (float) Math.random() * Gdx.graphics.getHeight(), 32)); // Ajouter un nouveau monstre au bas de l'écran
            timeSinceLastApple = 0;
        }

        // Mettre à jour la position des balles
        for (Ball ball : balls) {
            ball.update();
        }

        // Générer un nouveau monstre si l'intervalle est écoulé
        if (timeSinceLastMonster >= MONSTER_INTERVAL) {
            float startX = (float) Math.random() * Gdx.graphics.getWidth(); // Position X aléatoire
            rockets.add(new Rocket(startX, 0, 32)); // Ajouter un nouveau monstre au bas de l'écran
            timeSinceLastMonster = 0;
        }
        timeSinceLastObstacle += delta;
        if (timeSinceLastObstacle >= OBSTACLE_INTERVAL) {
            timeSinceLastObstacle = 0;
            addRandomObstacle();
        }
        ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        currentMap.render();
        styleFont.draw(game.batch, "Score: " + score, 10, 490);
        if(live == 1)
            styleFont.draw(game.batch, "Life: " + 0, 10, 470);
        else
            styleFont.draw(game.batch, "Life: " + live, 10, 470);
        styleFont.draw(game.batch, "Level: " + level, 10, 450);
        styleFont.draw(batch, "Press 'P' to pause", 825, 490);
        witch.draw();
        witch.update();
        for (Ball ball : balls) {
            ball.draw(batch);
        }

        // Mettez à jour et dessinez les monstres
        for (Rocket rocket : rockets) {
            rocket.update(deltaTime, level * 100, level);
            rocket.draw(batch);
        }
        for (Obstacle obstacle : obstalceStatic) {
            obstacle.draw(batch);
            obstacle.update(deltaTime, level * 100);
        }
        for (Apple apple : apples) {
            apple.update(delta, level * 100);
            apple.draw(batch);
        }
        if(score >= 10  && score < 30 && level == 1) {
            level = 2;
            MONSTER_INTERVAL = 0.5f ;
            nextlevel.play();
            updateMap();
        }
        if(score >= 30 && level == 2) {
            level = 3;
            MONSTER_INTERVAL = 0.3f ;
            updateMap();
            nextlevel.play();
        }
        if(witch.getX() < - witch.getCollisionRectangle().width || witch.getX() > Gdx.graphics.getWidth()) {
            gameOver = true;
            soundPause.play();
        }
        collisionsBallRocket();
        checkCollisionWitchRockets(); // Vérifiez la collision entre la sorcière et les monstres
        checkCollisionWitchObstacles();
        collisionBallObstacles();
        collisionsAppleWitch();
        checkCollisionWithMapElements();
        Gdx.app.log("Y : ", "" + witch.getY());
        batch.end();
    }

    private void addRandomObstacle() {
        float x = random.nextFloat() * Gdx.graphics.getWidth();
        float y = random.nextFloat() * Gdx.graphics.getWidth();; // Position de départ en dehors de l'écran
        float size = 50 + random.nextFloat() * 50; // Taille aléatoire entre 50 et 100

        // Choisir aléatoirement un type d'obstacle
        int type = random.nextInt(2);
        Obstacle obstacle;
        if (type == 0) {
            obstacle = new Bomb(x, y, size);
        } else {
            obstacle = new Monster(x, y, size);
        }

        obstalceStatic.add(obstacle);
    }

    private void collisionsAppleWitch(){
        for(Apple apple : apples){
            if(apple.getCollisionRectangle().overlaps(witch.getCollisionRectangle())){
                live += 1;
                apples.removeValue(apple,true);
                eat.play();
            }
        }
    }
    private void collisionsBallRocket() {
        for (Ball ball : balls) {
            for (Rocket rocket : rockets) {
                if (ball.getCollisionRectangle().overlaps(rocket.getCollisionRectangle())) {
                    // Si une collision est détectée, marquez la balle et le monstre pour suppression
                    balls.removeValue(ball, true);
                    rockets.removeValue(rocket, true);
                    soundMonsterDie.play(1f);
                    score++;
                    break; // Sortir de la boucle pour éviter les erreurs de modification de collection
                }
            }
        }
    }

    private void checkCollisionWitchRockets() {
        for (Rocket rocket : rockets) {
            if (witch.getCollisionRectangle().overlaps(rocket.getCollisionRectangle())) {
                soundBall.pause();
                soundPause.play(1f);
                isPaused = true;// Mettre le jeu en pause si des vies restent
                rockets.removeValue(rocket,true);
                if(live == 1) {
                    gameOver = true;
                } // Terminer le jeu si aucune vie ne reste

                return; // Sortir de la boucle après la collision
            }
        }
    }
    private void checkCollisionWitchObstacles() {
        for (Obstacle obstacle : obstalceStatic) {

            if(witch.getCollisionRectangle().overlaps(obstacle.getCollisionRectangle())){
                if(obstacle instanceof Bomb)
                    live = 1;
                soundBall.pause();
                soundPause.play();
                isPaused = true;// Mettre le jeu en pause si des vies restent
                obstalceStatic.removeValue(obstacle, true);
                if (live == 1)
                    gameOver = true; // Terminer le jeu si aucune vie ne reste

                return; // Sortir de la boucle après la collision
            }

        }

    }
    private void collisionBallObstacles() {
        for (Ball ball : balls) {
            for (Obstacle obstacle : obstalceStatic) {
                if (ball.getCollisionRectangle().overlaps(obstacle.getCollisionRectangle())) {
                    // Si une collision est détectée, marquez la balle et le monstre pour suppression
                    balls.removeValue(ball, true);
                    obstalceStatic.removeValue(obstacle, true);
                    soundMonsterDie.play(1f);
                    if(obstacle instanceof Bomb) {
                        boom.play();
                        score += 2;
                    }
                    else
                        score++;
                    break; // Sortir de la boucle pour éviter les erreurs de modification de collection
                }
            }
        }
    }

    // Ajoutez cette méthode à votre classe GameScreen
    private void checkCollisionWithMapElements() {
        Array<Rectangle> mapElements = currentMap.getCollisionRectangles();
        Rectangle witchRect = witch.getCollisionRectangle();

        for (Rectangle rect : mapElements) {
            if (witchRect.overlaps(rect)) {
                float witchCenterX = witchRect.x + witchRect.width / 2;
                float witchCenterY = witchRect.y + witchRect.height / 2;
                float rectCenterX = rect.x + rect.width / 2;
                float rectCenterY = rect.y + rect.height / 2;

                float dx = witchCenterX - rectCenterX;
                float dy = witchCenterY - rectCenterY;

                float absDx = Math.abs(dx);
                float absDy = Math.abs(dy);

                if (absDx > absDy) {
                    if (dx > 0) {
                        //la brique est devant la sorcière
                        witch.x = (int) (rect.x + rect.width);
                    } else {
                        //la brique est derrière la brique
                        witch.x = (int) (rect.x - witchRect.width);
                    }
                } else {
                    if (dy > 0) {
                        // la sorcière est en dessous de la brique
                        witch.y = (int) (rect.y + rect.height);
                    } else {
                        // la sorcière est au dessus de la brique
                        witch.y = (int) (rect.y - witchRect.height);
                    }
                }
                // Update the collision rectangle after adjustment
                witchRect.set(witch.x, witch.y, witchRect.width, witchRect.height);
            }
        }
    }
    private void updateMap() {
        switch (level) {
            case 1:
                currentMap = map1;
                break;
            case 2:
                currentMap = map2;
                break;
            case 3:
                currentMap = map3;
                break;
        }
    }




    @Override
    public void show() {
        if(gameSound != null)
            gameSound.play(1);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        soundPause.stop();
    }

    @Override
    public void dispose() {
        soundPause.dispose();
        soundBall.dispose();
        batch.dispose();
        witch.dispose();
        for (Rocket rocket : rockets) {
            rocket.dispose();
        }
        for (Ball ball : balls) {
            ball.dispose();
        }
        for (Apple apple : apples) {
            apple.dispose();
        }
    }
}
