package GamePlay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.Serializable;
import java.util.ArrayList;

import GameObject.*;
import SystemController.GameState;
import SystemController.GameStateManager;
import Menu.MenuState;

/**
 * Created by danielmacario on 14-10-29.
 */
public class GamePlayManager extends GameState implements ActionListener, Serializable {

    private enum CountDownNotification {
        NEXTSTAGENOTIFICATION, GAMEOVERNOTIFICATION
    }

    private TileMap tileMap;
    private Player player;
    private Camera camera;
    private CollisionManager collisionManager;
    private boolean cameraMoving;
    private boolean secondCameraRegion;
    private boolean gameOver;

    private int gameOverScreenCount = 50;
    private int nextStageTransitionCount = 50;
    public int bonusStageCountDown = 900;
    private int bonusStageNewEnemyCountDown = 30;

    //TODO: remove after demo, these are for temporary pause feature
    private Color titleColor = new Color(255, 0, 21);
    private Color hudColor = new Color(255, 255, 255);
    private Font titleFont = new Font("Gill Sans Ultra Bold", Font.PLAIN, 48);
    private Font hudFont = new Font("Gill Sans Ultra Bold", Font.PLAIN, 12);

    public GamePlayManager(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = new Player(35, 35, true, MovableObject.NORMALSPEED);
        this.cameraMoving = false;
        this.tileMap = new TileMap(player.getSpeed());
        this.collisionManager = new CollisionManager(player, tileMap);
        this.player.setTileMap(tileMap);
        this.camera = new Camera(player.getPosX(), player);
        this.gameOver = false;
    }

    @Override
    public void draw(Graphics2D g) {

        GamePlayState currentState = player.getCurrentGamePlayState();

        if (currentState == GamePlayState.GAMEOVER) {
            executeGameOverStateLogic(g);
        } else if (currentState == GamePlayState.PAUSE) {
            gsm.setState(gsm.MENUSTATE, MenuState.INGAME);
        } else if (currentState == GamePlayState.INGAME) {
            executeInGameLogic(g);
        }
    }

    private void executeGameOverStateLogic(Graphics2D g) {
        gameOver = true;

        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Game Over", 100, 200);

        boolean redirectToGameOverMenu = countDownToNotification(CountDownNotification.GAMEOVERNOTIFICATION);
        if (redirectToGameOverMenu) {
            //TODO: redirect to the gameover menu!
            gsm.setState(gsm.MENUSTATE, MenuState.GAMEOVER);
        }
    }

    public void executeInGameLogic(Graphics2D g) {
        if (tileMap.isNextStageTransition()) {
            inStageTransition(g);
            return;
        }

        if (tileMap.isBonusStage()) {
            initiateTimerToNextStage();
            initateTimeToSpawnEnemy();
        }

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
        drawHUD(g, tileMap.isBonusStage());
    }

    public void inStageTransition(Graphics2D g) {
        boolean redirectToNextStage = countDownToNotification(CountDownNotification.NEXTSTAGENOTIFICATION);
        if (redirectToNextStage) {
            tileMap.setNextStageTransition(false);

        } else {
            g.setColor(titleColor);
            g.setFont(titleFont);
            g.drawString("Next Stage!", 100, 200);
        }
    }


    public void drawHUD(Graphics2D g, boolean bonusStage) {
        String hudInformation;
        g.setColor(hudColor);
        g.setFont(hudFont);
        if (bonusStage) {
            hudInformation = "Time Remaining: " + bonusStageCountDown/30 +
                    " | Lives Left: " + player.getLifesRemaining() + " | Score: " + player.getScore();
            g.drawString(hudInformation, 160, 20);
        } else {
            hudInformation = "Lives Left: " + player.getLifesRemaining() + " | Score: " + player.getScore();
            g.drawString(hudInformation, 305, 20);
        }
    }

    public boolean countDownToNotification(CountDownNotification action) {
        if (action == CountDownNotification.GAMEOVERNOTIFICATION) {
            gameOverScreenCount--;
            if (gameOverScreenCount < 0) {
                gameOverScreenCount = 50;
                return true;
            }
            return false;
        } else {
            nextStageTransitionCount--;
            if (nextStageTransitionCount < 0) {
                nextStageTransitionCount = 50;
                return true;
            }
            return false;
        }
    }

    public void initiateTimerToNextStage() {
        if (bonusStageCountDown == 0) {
            bonusStageCountDown = 900;
            player.nextStage();
        }
        bonusStageCountDown--;
    }

    public void initateTimeToSpawnEnemy() {
        if (bonusStageNewEnemyCountDown == 0) {
            bonusStageNewEnemyCountDown = 30;
            tileMap.addNewEnemy();
        }
        bonusStageNewEnemyCountDown--;
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
        Door door = tileMap.getDoor();
        collisionManager.handleCollisions(objects,
                                          playerRectangle, enemies,
                                          bombsPlaced, flames,
                                          powerUp, door, this.tileMap.isBonusStage());

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

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setGamePlayStateToInGame() {
        this.player.setCurrentGamePlayState(GamePlayState.INGAME);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    public void setNextStageTransitionCount(int nextStageTransitionCount) {
        this.nextStageTransitionCount = nextStageTransitionCount;
    }
}
