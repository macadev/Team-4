package GameObject;

import Database.DatabaseController;
import GamePlay.GamePlayState;
import GamePlay.Spawner;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Created by danielmacario on 14-10-29.
 */
public class TileMap implements Serializable {

    public static final int TOTAL_WIDTH_OF_COLUMNS = 992;
    public static final int TOTAL_HEIGHT_OF_ROWS = 416;
    public static final int NUM_OF_COLS = 31;
    public static final int NUM_OF_ROWS = 13;
    public static final int WIDTH_OF_TILE = 32;
    public static final int HEIGHT_OF_TILE = 32;
    public static final int CAMERA_MOVING_LIMIT = 224;

    private Player player;
    private GameObject[][] walls;
    private ArrayList<Flame> flames;
    private ArrayList<Enemy> enemies;
    private PowerUp powerUp;
    private Door door;
    private Spawner spawner;
    private String userName;
    private int bombRadius;
    private boolean isBonusStage;
    private boolean harderSetAlreadyCreated;
    private boolean nextStageTransition;
    private boolean spawnHarderSet;
    private int timeToHarderSet = 20;
    private ArrayList<Enemy> newEnemySet;

    //keep track of the current stage
    private int currentStage;
    private int bonusStageCountDown;

    public TileMap(Player player, int selectedStage, String userName) {
        this.player = player;
        this.userName = userName;
        this.currentStage = selectedStage;
        this.bombRadius = 11;
        this.spawner = new Spawner(getCurrentStage());
        this.flames = new ArrayList<Flame>();
        this.isBonusStage = getCurrentStage().isBonusStage();
        this.bonusStageCountDown = 0;
        this.harderSetAlreadyCreated = false;
        populateGridWithBlocks();
        createEnemySet();
        generatePowerUp();
        generateDoor();
    }

    public void drawObjects(Graphics2D g) {
        if (spawnHarderSet) {
            timeToHarderSet--;
            if (timeToHarderSet == 0) {
                enemies = newEnemySet;
                timeToHarderSet = 20;
                spawnHarderSet = false;
            }
        }

        drawPowerUp(g);
        drawDoor(g);
        drawTiles(g);
        drawEnemies(g);
    }

    private void drawPowerUp(Graphics2D g) {
        if (powerUp == null){
            return;
        }
        //powerUp.setVisible(true);
        if (powerUp.isVisible()) powerUp.draw(g);
    }

    private void drawDoor(Graphics2D g) {
        if (powerUp == null) return;
        //door.setVisible(true);
        if (door.isVisible()) door.draw(g);
    }

    public void drawTiles(Graphics2D g) {

        //Draw the walls and the enemies
        for (GameObject[] row : walls) {
            for (GameObject object : row) {
                if (object != null && object.isVisible())
                    object.draw(g);
            }
        }

        Flame flame;
        for (Iterator<Flame> iterator = flames.iterator(); iterator.hasNext();) {
            flame = iterator.next();
            flame.incrementTimeOnGrid();
            if (!flame.isVisible()) {
                iterator.remove();
            } else {
                flame.draw(g);
            }
        }
    }

    public void drawEnemies(Graphics2D g) {
        if (enemies.isEmpty()) return;
        int enemyCount = enemies.size();
        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isVisible()) {
                enemy.draw(g);
            } else {
                enemies.remove(enemy);
                enemyCount = enemies.size();
            }
        }
    }

    public void nextStage() {
        currentStage++;

        updateUnlockedStage(currentStage, userName);

        //player completed the game
        if (currentStage == 53) {
            player.setCurrentGamePlayState(GamePlayState.FINISHEDGAME);
            return;
        }

        StageData newStage = Stages.gameStages[this.currentStage];
        this.harderSetAlreadyCreated = false;
        this.nextStageTransition = true;
        this.isBonusStage = newStage.isBonusStage();
        this.flames = new ArrayList<Flame>();
        spawner.nextStage(newStage);
        populateGridWithBlocks();
        createEnemySet();
        generatePowerUp();
        generateDoor();
    }

    private void updateUnlockedStage(int currentStage, String username) {
        try {
            DatabaseController.setLevelUnlocked(username, currentStage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void populateGridWithBlocks() {
        walls = spawner.generateWalls();
    }

    public void createEnemySet() {
        enemies = spawner.generateEnemies();
    }

    public void addNewEnemy() {
        Enemy bonusEnemy = spawner.createBonusStageEnemy();
        enemies.add(bonusEnemy);
    }

    public void generatePowerUp() {
        this.powerUp = spawner.generatePowerUp();
    }

    public void generateDoor() {
        this.door = spawner.generateDoor();
    }

    public void moveEnemies(int posX, int posY, boolean playerIsVisible) {
        for (Enemy enemy : enemies) {
            enemy.move();
            if (playerIsVisible){
                enemy.chasePlayer(posX, posY);
            }
        }
    }

    public void addFlames(int posX, int posY) {
        System.out.println("Flame x = " + posX + " y = " + posY);
        int posXOfExplosion = posX / 32;
        int posYOfExplosion = posY / 32;

        //Place a flame object at the center of the explosion
        //TODO: INTRODUCE CONSTANTS EVERYWHERE!
        flames.add(new Flame(posXOfExplosion * 32, posYOfExplosion * 32, true, posX, posY));

        boolean isConcreteWall;
        boolean isBrickWall;
        int posXofFlame;
        int posYofFlame;
        int posXofWall;
        int posYofWall;
        GameObject wall;
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction direction : directions) {
            for (int i = 1; i < bombRadius + 1; i++) {

                if (direction == Direction.EAST) {

                    posXofWall = posXOfExplosion + i;
                    posYofWall = posYOfExplosion;
                    wall = walls[posXofWall][posYofWall];
                    posXofFlame = (posXofWall) * 32;
                    posYofFlame = (posYofWall) * 32;

                } else if (direction == Direction.WEST) {

                    posXofWall = posXOfExplosion - i;
                    posYofWall = posYOfExplosion;
                    wall = walls[posXofWall][posYofWall];
                    posXofFlame = (posXofWall) * 32;
                    posYofFlame = (posYofWall) * 32;

                } else if (direction == Direction.NORTH) {

                    posXofWall = posXOfExplosion;
                    posYofWall = posYOfExplosion - i;
                    wall = walls[posXofWall][posYofWall];
                    posXofFlame = (posXofWall) * 32;
                    posYofFlame = (posYofWall) * 32;

                } else {

                    posXofWall = posXOfExplosion;
                    posYofWall = posYOfExplosion + i;
                    wall = walls[posXofWall][posYofWall];
                    posXofFlame = (posXofWall) * 32;
                    posYofFlame = (posYofWall) * 32;

                }

                isConcreteWall = wall instanceof ConcreteWall;
                isBrickWall = wall instanceof BrickWall;

                if (isBrickWall || isConcreteWall) {
                    if (isBrickWall) {
                        walls[posXofWall][posYofWall] = null;
                    }
                    break;
                } else if (!isConcreteWall) {
                    flames.add(new Flame(posXofFlame, posYofFlame, true, posX, posY));
                }
            }
        }
    }

    public void spawnSetOfHarderEnemies(int posX, int posY) {
        if (enemies.size() == 0) return;
        EnemyType harderEnemyType = determineHarderEnemyTypeToSpawn();
        newEnemySet = spawner.createSetOfHarderEnemies(harderEnemyType, posX, posY);
        spawnHarderSet = true;
    }

    public EnemyType determineHarderEnemyTypeToSpawn() {

        Collections.sort(enemies, new Comparator<Enemy>() {
            @Override
            public int compare(Enemy enemyA, Enemy enemyB) {
                return enemyA.getDifficultyRanking() - enemyB.getDifficultyRanking(); // Ascending
            }

        });

        //Get the most difficult enemy type present in the game
        int hardestTypeDifficulty = enemies.get(enemies.size() - 1).getDifficultyRanking();

        switch (hardestTypeDifficulty) {
            case 0:
                return EnemyType.ONEAL;
            case 1:
                return EnemyType.DOLL;
            case 2:
                return EnemyType.MINVO;
            case 3:
                return EnemyType.KONDORIA;
            case 4:
                return EnemyType.OVAPI;
            case 5:
                return EnemyType.PASS;
            case 6:
                return EnemyType.PONTAN;
            case 7:
                return EnemyType.PONTAN;
        }

        return null;

    }

    public void incrementBombRadius() {
        bombRadius++;
    }

    public StageData getCurrentStage() {
        return Stages.gameStages[this.currentStage];
    }

    public int getCurrentStageNumber() {
        return currentStage;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public GameObject[][] getObjects() {
        return walls;
    }

    public ArrayList<Flame> getFlames() {
        return flames;
    }

    public void setFlames(ArrayList<Flame> flames) {
        this.flames = flames;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public boolean isBonusStage() {
        return isBonusStage;
    }

    public void setBonusStage(boolean isBonusStage) {
        this.isBonusStage = isBonusStage;
    }

    public boolean isHarderSetAlreadyCreated() {
        return harderSetAlreadyCreated;
    }

    public void setHarderSetAlreadyCreated(boolean harderSetAlreadyCreated) {
        this.harderSetAlreadyCreated = harderSetAlreadyCreated;
    }

    public boolean isNextStageTransition() {
        return nextStageTransition;
    }

    public void setNextStageTransition(boolean nextStageTransition) {
        this.nextStageTransition = nextStageTransition;
    }
}
