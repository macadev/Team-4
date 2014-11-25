/**
 * Created by danielmacario on 14-10-29.
 */
package GameObject;

import Database.DatabaseController;
import GameObject.ArtificialIntelligence.HighIntelligence;
import GameObject.ArtificialIntelligence.PathFinder;
import GamePlay.Coordinate;
import GamePlay.GamePlayState;
import GamePlay.Spawner;

import java.awt.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * The TileMap class is used to represent the map or grid where GamePlay takes place.
 * It is a container for all the objects that are drawn on the grid during gamePlay,
 * including: walls, enemies, powerUps, and the door. This class is also in charge of
 * drawing all of these objects on the grid.
 */
public class TileMap implements Serializable {

    //Default constants for size of tiles and overall grid.
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
    private PathFinder pathFinder;
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

    /**
     * Initialize a TileMap object to hold and interact with all the game objects that
     * are present in a specified stage.
     * @param player A player instance used to interact with various logic elements present
     *               in this class
     * @param selectedStage An integer specifying the stage number that will be used as the
     *                      blueprint for enemy generation and powerUp generation.
     * @param userName
     */
    public TileMap(Player player, int selectedStage, String userName) {
        this.player = player;
        this.userName = userName;
        this.currentStage = selectedStage;
        this.bombRadius = 1;
        this.spawner = new Spawner(getCurrentStage());
        this.flames = new ArrayList<Flame>();
        this.isBonusStage = getCurrentStage().isBonusStage();
        this.pathFinder = new PathFinder();
        HighIntelligence.setPathFinder(pathFinder);
        this.bonusStageCountDown = 0;
        this.harderSetAlreadyCreated = false;
        populateGridWithBlocks();
        createEnemySet();
        generatePowerUp();
        generateDoor();
    }

    /**
     * Draws all the game objects contained within the TileMap instance on the grid.
     * We check if the spawn harder enemies flag is true to generate new enemies before drawing them.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    public void drawObjects(Graphics2D g) {
        determineIfShouldSpawnHarderEnemies();
        drawPowerUp(g);
        drawDoor(g);
        drawTiles(g);
        drawEnemies(g);
    }

    /**
     * Function called when a bomb explosion collides with a powerUp or the door.
     * If this is case, we wait 20 frames for the flames to disappear and then
     * generate the harder set of enemies.
     */
    public void determineIfShouldSpawnHarderEnemies() {
        if (spawnHarderSet) {
            timeToHarderSet--;
            if (timeToHarderSet == 0) {
                //Populate the enemies array with the new harder enemy set.
                enemies = newEnemySet;
                //reset the timer to its default of 20
                timeToHarderSet = 20;
                spawnHarderSet = false;
            }
        }
    }

    /**
     * Draws the powerUp object on the grid.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    private void drawPowerUp(Graphics2D g) {
        //we check if the powerUp is null because during bonus stages there is no powerUp present.
        if (powerUp == null) return;
        if (powerUp.isVisible()) powerUp.draw(g);
    }

    /**
     * Draws the door on the grid.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    private void drawDoor(Graphics2D g) {
        //we check if the door is null because during bonus stages there is no powerUp present.
        if (powerUp == null) return;
        if (door.isVisible()) door.draw(g);
    }

    /**
     * Draws the wall and flame objects placed on the different tiles of the grid.
     * Note that this information is held by the two dimension walls array, which
     * represents the 12 by 31 map, and specifies which wall types are held by
     * different tiles.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    public void drawTiles(Graphics2D g) {
        //Draw the walls and the enemies
        for (GameObject[] row : walls) {
            for (GameObject object : row) {
                if (object != null && object.isVisible())
                    if (!(isBonusStage && (object instanceof BrickWall))) {
                        object.draw(g);
                    }
            }
        }

        Flame flame;
        for (Iterator<Flame> iterator = flames.iterator(); iterator.hasNext();) {
            flame = iterator.next();
            flame.incrementTimeOnGrid();
            if (!flame.isVisible()) {
                //Remove the flames object from the flames ArrayList when they are
                //no longer visible.
                iterator.remove();
            } else {
                flame.draw(g);
            }
        }
    }

    /**
     * Draws the enemies on the grid.
     * @param g Graphics object corresponding to the JPanel where the game play state is rendered.
     */
    public void drawEnemies(Graphics2D g) {
        if (enemies.isEmpty()) return;

        int enemyCount = enemies.size();
        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isVisible()) {
                enemy.draw(g);
            } else {
                //if the enemy is not visible we remove it from the enemies ArrayList
                enemies.remove(enemy);
                enemyCount = enemies.size();
            }
        }
    }

    /**
     * Advances to the next stage in the progression of the game.
     * If the player has completed the last stage, it initiates the
     * finished game logic.
     */
    public void nextStage() {
        currentStage++;

        //player completed the game
        if (currentStage == 61) {
            player.setCurrentGamePlayState(GamePlayState.FINISHEDGAME);
            return;
        }

        //Update the unlocked stage column in the database for the user playing the game.
        updateUnlockedStage(currentStage, userName);

        //Load the new stage blueprint used for enemy generation.
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

    /**
     * Update the unlocked stage column in the database for the user playing the game.
     * @param currentStage The integer specifying the stage number completed by the user.
     * @param username The username of the player used to find the row in the database
     *                 to be updated.
     */
    private void updateUnlockedStage(int currentStage, String username) {
        try {
            DatabaseController.setLevelUnlocked(username, currentStage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the walls two dimensional array with the specific information about the walls
     * present in the current grid. This is done by means of the Spawner class.
     */
    public void populateGridWithBlocks() {
        walls = spawner.generateWalls();
        pathFinder.updateGraph(walls);
    }

    /**
     * Populates the enemies ArrayList with a new set of enemies that matches the blueprint given by
     * the current stage.
     */
    public void createEnemySet() {
        enemies = spawner.generateEnemies();
    }

    /**
     * Adds a new enemy to the enemies ArrayList. This function is used during bonus stages,
     * where the enemies keep spawning infinitely for 30 seconds.
     */
    public void addNewEnemy() {
        Enemy bonusEnemy = spawner.createBonusStageEnemy();
        enemies.add(bonusEnemy);
    }

    /**
     * Generate a powerUp following the blueprint given by the current stage.
     */
    public void generatePowerUp() {
        this.powerUp = spawner.generatePowerUp();
    }

    /**
     * Generate a door object to be placed randomly on the grid.
     */
    public void generateDoor() {
        this.door = spawner.generateDoor();
    }

    /**
     * Populates the flames ArrayList with a new set of flames. This function
     * is called when a bomb explodes.
     * @param bombPosX Integer representing the x coordinate where the bomb exploded.
     * @param bombPosY Integer representing the y coordinate where the bomb exploded.
     */
    public void addFlames(int bombPosX, int bombPosY) {
        int posXOfExplosion = bombPosX / 32;
        int posYOfExplosion = bombPosY / 32;

        //Place a flame object at the center of the explosion
        //TODO: INTRODUCE CONSTANTS EVERYWHERE!
        //We create a flame object directly on the location where the bomb exploded.
        flames.add(new Flame(posXOfExplosion * 32, posYOfExplosion * 32, true, bombPosX, bombPosY));

        boolean isConcreteWall;
        boolean isBrickWall;
        int posXofFlame;
        int posYofFlame;
        int posXofWall;
        int posYofWall;
        GameObject wall;
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        //We expand in the radius of explosion of the bomb going in the cardinal directions.
        //We add flame objects at each tile until we reach the limit of the radius or we hit
        //a concrete wall.
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
                    flames.add(new Flame(posXofFlame, posYofFlame, true, bombPosX, bombPosY));
                }
            }
        }
    }

    /**
     * Creates a new set of enemies of one difficulty higher than the current
     * most difficult enemy present on the grid.
     * @param spawnPosX Integer representing the x coordinate where the new enemies
     *             will spawn.
     * @param spawnPosY Integer representing the y coordinate where the new enemies
     *             will spawn.
     */
    public void spawnSetOfHarderEnemies(int spawnPosX, int spawnPosY) {
        if (enemies.size() == 0) return;
        EnemyType harderEnemyType = determineHarderEnemyTypeToSpawn();
        newEnemySet = spawner.createSetOfHarderEnemies(harderEnemyType, spawnPosX, spawnPosY);
        spawnHarderSet = true;
    }

    /**
     * Displaces the enemies on the grid, and initiates their artificial intelligence chase mode
     * if the player is at a specified distance.
     * @param playerPosX Integer specifying the x position of the player on the grid.
     * @param playerPosY Integer specifying the y position of the player on the grid.
     * @param playerIsVisible Boolean specifying whether the player is visible or not.
     */
    public void moveEnemies(int playerPosX, int playerPosY, boolean playerIsVisible) {
        //We update the pathFinder timer to determine if we should refresh the graph
        //used to find the shortest path between the enemy and the player by the
        //high intelligence AI.
        pathFinder.updateGraphRefreshTimer();

        for (Enemy enemy : enemies) {
            enemy.move();

            //Calculate the distance between the player and the enemy to determine
            //whether the enemy should chase the player or not. We set the distance
            //to a default of 100 which would not result in a chase for any of
            //the intelligence types.
            int distanceBetweenPlayerAndEnemy = 100;
            if (playerIsVisible) {
                Coordinate centerOfPlayerObject = new Coordinate(playerPosX + 15, playerPosY + 15);
                Coordinate centerOfEnemyObject = new Coordinate(enemy.getPosX() + 15, enemy.getPosY() + 15);
                distanceBetweenPlayerAndEnemy = centerOfPlayerObject.distanceTo(centerOfEnemyObject);
            }

            //Updating the graph is an expensive operation, we want to do it once for all the enemies.
            //Also, we only want to update it when a chase will take place, which only happens when the distance
            //between the player and the enemy is less than 85 pixels (2 tiles).
            if (enemy.getIntelligence() instanceof HighIntelligence && distanceBetweenPlayerAndEnemy < 85 && pathFinder.getRefreshGraph()) {
                //TODO: remove this println
                System.out.println("Enemy has high intelligence");
                pathFinder.updateGraph(walls);
                pathFinder.setRefreshGraph(false);
            }

            if (playerIsVisible && !player.isInvincibilityEnabled() && !isBonusStage){
                enemy.chasePlayer(playerPosX, playerPosY, distanceBetweenPlayerAndEnemy);
            }
        }
    }

    /**
     * Determines the hardest enemy type that follows the current hardest enemy type present on the grid.
     * @return An EnemyType object specifying th
     */
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

    public GameObject[][] getWalls() {
        return walls;
    }
}
