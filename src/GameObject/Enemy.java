/**
 * Created by danielmacario on 14-11-12.
 */
package GameObject;

import GameObject.ArtificialIntelligence.*;
import GamePlay.Coordinate;

import javax.swing.*;
import java.io.Serializable;

/**
 * Used to represent the enemy objects present in the game. Note that there are 8 different enemy types in the game.
 * Instead of creating 8 different classes to represent each enemy, we have a single dynamic structure that creates
 * an enemy object based on the EnemyType passed to the constructor.
 *
 * Enemies can have 4 different speed attributes, the capability of passing through walls, and also 3 different
 * levels of intelligence.
 */
public class Enemy extends MovableObject implements Serializable {

    private EnemyType enemyType;
    private Direction directionOfMovement;
    private ArtificialIntelligence intelligence = null;
    private PathFinder pathFinder;
    private int difficultyRanking;
    private boolean hitByFlames = false;

    /**
     * Dynamically generate the enemy object based on the EnemyType passed as argument.
     * @param type EnemyType to be created
     * @param posX x position of the enemy on the grid
     * @param posY y position of the enemy on the grid
     */
    public Enemy(EnemyType type, int posX, int posY) {

        this.enemyType = type;
        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.previousY = posY;
        this.visible = true;
        //Randomly select a direction of movement upon creation
        this.directionOfMovement = Direction.getRandomDirection();

        //Depending on the EnemyTyped passed, we select a score attribute, image, level of intelligence, speed,
        //and whether the enemy can pass through walls or not.
        switch (type) {
            case BALLOOM:
                this.imagePath = "../resources/Enemies/balloom.png";
                this.score = 100;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                this.intelligence = new LowIntelligence();
                this.difficultyRanking = 0;
                break;
            case ONEAL:
                this.imagePath = "../resources/Enemies/oneal.png";
                this.score = 200;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                this.intelligence = new MediumIntelligence();
                this.difficultyRanking = 1;
                break;
            case DOLL:
                this.imagePath = "../resources/Enemies/doll.png";
                this.score = 400;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                this.intelligence = new LowIntelligence();
                this.difficultyRanking = 2;
                break;
            case MINVO:
                this.imagePath = "../resources/Enemies/minvo.png";
                this.score = 800;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                this.intelligence = new MediumIntelligence();
                this.difficultyRanking = 3;
                break;
            case KONDORIA:
                this.imagePath = "../resources/Enemies/kondoria.png";
                this.score = 1000;
                //TODO: define 'slowest' speed
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                this.intelligence = new HighIntelligence();
                this.difficultyRanking = 4;
                break;
            case OVAPI:
                this.imagePath = "../resources/Enemies/ovapi.png";
                this.score = 2000;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                this.intelligence = new MediumIntelligence();
                this.difficultyRanking = 5;
                break;
            case PASS:
                this.imagePath = "../resources/Enemies/pass.png";
                this.score = 4000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = false;
                this.intelligence = new HighIntelligence();
                this.difficultyRanking = 6;
                break;
            case PONTAN:
                this.imagePath = "../resources/Enemies/pontan.png";
                this.score = 8000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = true;
                this.intelligence = new HighIntelligence();
                this.difficultyRanking = 7;
                break;
        }
        this.image = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void move() {
        intelligence.move(this);
    }

    public void reverseDirection() {
        intelligence.reverseDirection(this);
    }

    public void chasePlayer(int playerPosX, int playerPosY, int distanceBetweenEnemyAndPlayer) {
        intelligence.chasePlayer(playerPosX, playerPosY, distanceBetweenEnemyAndPlayer, this);
    }

    /**
     * Disable the visibility of the object. This method gets called once the enemy has been killed
     * by a bomb explosion.
     */
    public void death() {
        this.visible = false;
    }

    public Direction getDirectionOfMovement() {
        return directionOfMovement;
    }

    public void setDirectionOfMovement(Direction directionOfMovement) {
        this.directionOfMovement = directionOfMovement;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public Coordinate getCenterOfEnemyAsCoordinate() {
        return new Coordinate(posX + 15, posY + 15);
    }

    public boolean isHitByFlames() {
        return hitByFlames;
    }

    public void setHitByFlames(boolean hitByFlames) {
        this.hitByFlames = hitByFlames;
    }

    public int getDifficultyRanking() {
        return difficultyRanking;
    }

    public ArtificialIntelligence getIntelligence() {
        return intelligence;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public void setPathFinder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

}

