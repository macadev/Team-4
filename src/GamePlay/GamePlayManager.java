package GamePlay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import GameObject.*;
import SystemController.GameState;
import SystemController.GameStateManager;
import Menu.MenuState;

/**
 * Created by danielmacario on 14-10-29.
 */
public class GamePlayManager extends GameState implements ActionListener {

    private TileMap tileMap;
    private Player player;
    private Camera camera;
    private CollisionManager collisionManager;
    private boolean cameraMoving;
    private boolean secondCameraRegion;
    private boolean gameOver;
    private int gameOverScreenCount = 0;


    //TODO: remove after demo, these are for temporary pause feature
    private Color titleColor = new Color(255, 0, 21);
    private Color hudColor = new Color(255, 255, 255);
    private Font titleFont = new Font("Gill Sans Ultra Bold", Font.PLAIN, 48);
    private Font hudFont = new Font("Gill Sans Ultra Bold", Font.PLAIN, 12);

    public GamePlayManager(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = new Player(35, 35, true, MovableObject.NORMALSPEED);
        this.collisionManager = new CollisionManager(player);
        this.cameraMoving = false;
        this.tileMap = new TileMap(player.getSpeed());
        this.player.setTileMap(tileMap);
        this.camera = new Camera(player.getPosX(), player);
        this.gameOver = false;
    }

    @Override
    public void draw(Graphics2D g) {
        GamePlayState currentState = player.getCurrentGamePlayState();

        if (currentState == GamePlayState.GAMEOVER) {
            gameOver = true;

            g.setColor(titleColor);
            g.setFont(titleFont);
            g.drawString("Game Over", 100, 200);

            boolean redirectToGameOverMenu = updateGameOverScreenCount();
            if (redirectToGameOverMenu) {
                //TODO: redirect to the gameover menu!
                gsm.setState(gsm.MENUSTATE, MenuState.MAIN);
            }
        } else if (currentState == GamePlayState.PAUSE) {

            gsm.setState(gsm.MENUSTATE, MenuState.INGAME);

        } else if (currentState == GamePlayState.INGAME) {

            //Move the player each time we render again
            //Movement depends on the deltaX and deltaY
            //values of the MovableObject class
            //updateCamera();

            player.move();
            tileMap.moveEnemies(player.getPosX(), player.getPosY(), player.isVisible());

            checkCollisions();
            updateCamera();
            camera.adjustPosition();
            if (cameraMoving) {
                g.translate(camera.getPosX(), 0);
                player.drawBombs(g);
                tileMap.drawObjects(g);
                player.draw(g);
                g.translate(-camera.getPosX(), 0);
            } else {
                if (secondCameraRegion) {
                    g.translate(-(tileMap.TOTAL_WIDTH_OF_COLUMNS - 15 * tileMap.WIDTH_OF_TILE), 0);
                    player.drawBombs(g);
                    tileMap.drawObjects(g);
                    player.draw(g);
                    g.translate(tileMap.TOTAL_WIDTH_OF_COLUMNS - 15 * tileMap.WIDTH_OF_TILE, 0);
                } else {
                    player.drawBombs(g);
                    tileMap.drawObjects(g);
                    player.draw(g);
                }
            }
            drawHUD(g);
        }
    }

    private void drawHUD(Graphics2D g) {

        String hudInformation = "Lives Left: " + player.getLifesRemaining() + " | Score: " + player.getScore();
        g.setColor(hudColor);
        g.setFont(hudFont);
        g.drawString(hudInformation, 305, 20);

    }

    private boolean updateGameOverScreenCount() {
        gameOverScreenCount++;
        if (gameOverScreenCount > 50) {
            return true;
        }
        return false;
    }

    public void updateCamera() {
        int playerPosX = player.getPosX();
        int secondCameraThreshold = 733;
        if (playerPosX > tileMap.CAMERA_MOVING_LIMIT && playerPosX < secondCameraThreshold) {
            cameraMoving = true;
        } else {
            cameraMoving = false;
            secondCameraRegion = false;
            if (playerPosX >= secondCameraThreshold) {
                secondCameraRegion = true;
            }
        }
    }

    public void checkCollisions() {
        Rectangle playerRectangle = player.getBounds();
        GameObject[][] objects = tileMap.getObjects();
        ArrayList<Bomb> bombsPlaced = player.getBombsPlaced();
        ArrayList<Enemy> enemies = tileMap.getEnemies();
        ArrayList<Flame> flames = tileMap.getFlames();
        PowerUp powerUp = tileMap.getPowerUp();

        collisionManager.handleCollisions(objects,
                                          playerRectangle, enemies,
                                          bombsPlaced, flames,
                                          powerUp);

    }

    @Override
    public void keyPressed(int k) {
        this.player.keyPressed(k);
    }

    @Override
    public void keyReleased(int k) {
        this.player.keyReleased(k);
    }

    @Override
    public void init() {

        //Populate the blocks arrayLists present in the game;
        //populateGridWithBlocks();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //TODO: not 100% sure this will be used
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
