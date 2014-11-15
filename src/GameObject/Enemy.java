/**
 * Created by danielmacario on 14-11-12.
 */
package GameObject;

import GameObject.ArtificialIntelligence.ArtificialIntelligence;
import GameObject.ArtificialIntelligence.HighIntelligence;
import GameObject.ArtificialIntelligence.LowIntelligence;
import GameObject.ArtificialIntelligence.MediumIntelligence;
import GamePlay.Coordinate;

import javax.swing.*;

/**
 * Used to represent the enemy objects present in the game. Note that there are 8 different enemy types in the game.
 * Instead of creating 8 different classes to represent each enemy, we have a single dynamic structure that creates
 * an enemy object based on the EnemyType passed to the constructor.
 *
 * Enemies can have 4 different speed attributes, the capability of passing through walls, and also 3 different
 * levels of intelligence.
 */
public class Enemy extends MovableObject {

    private EnemyType enemyType;
    private Direction directionOfMovement;
    private ArtificialIntelligence intelligence = null;

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
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/balloom.png")).getImage();
                this.score = 100;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                this.intelligence = new MediumIntelligence();
                break;
            case DOLL:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/doll.png")).getImage();
                this.score = 400;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                this.intelligence = new LowIntelligence();
                break;
            case MINVO:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/minvo.png")).getImage();
                this.score = 800;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                this.intelligence = new MediumIntelligence();
                break;
            case OVAPI:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/ovapi.png")).getImage();
                this.score = 2000;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                this.intelligence = new MediumIntelligence();
                break;
            case ONEAL:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/oneal.png")).getImage();
                this.score = 200;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                this.intelligence = new MediumIntelligence();
                break;
            case PASS:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/pass.png")).getImage();
                this.score = 4000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = false;
                this.intelligence = new HighIntelligence();
                break;
            case PONTAN:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/pontan.png")).getImage();
                this.score = 8000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = true;
                this.intelligence = new HighIntelligence();
                break;
            case KONDORIA:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/kondoria.png")).getImage();
                this.score = 1000;
                //TODO: define 'slowest' speed
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                this.intelligence = new HighIntelligence();
                break;
        }

        this.intelligence = new MediumIntelligence();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public void move() {
        intelligence.move(this);
    }

    public void reverseDirection() {
        intelligence.reverseDirection(this);
    }

    public void chasePlayer(int playerPosX, int playerPosY) {
        intelligence.chasePlayer(playerPosX, playerPosY, this);
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
}

