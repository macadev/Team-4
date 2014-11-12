package GameObject;

import javax.swing.*;

/**
 * Created by danielmacario on 14-11-12.
 */
public class Enemy extends MovableObject {

    private EnemyType enemyType;

    public Enemy(EnemyType type, int posX, int posY) {

        this.posX = posX;
        this.posY = posY;
        this.previousX = posX;
        this.previousY = posY;
        this.visible = true;

        switch (type) {
            case BALLOOM:
                this.image = new ImageIcon(this.getClass().getResource("../resources/Enemies/balloom.png")).getImage();
                this.score = 100;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                break;
            case DOLL:
                this.score = 400;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = false;
                break;
            case MINVO:
                this.score = 800;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                break;
            case OVAPI:
                this.score = 2000;
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                break;
            case ONEAL:
                this.score = 200;
                this.speed = MovableObject.NORMALSPEED;
                this.wallPass = false;
                break;
            case PASS:
                this.score = 4000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = false;
                break;
            case PONTAN:
                this.score = 8000;
                this.speed = MovableObject.FASTSPEED;
                this.wallPass = true;
                break;
            case KONDORIA:
                this.score = 1000;
                //TODO: define 'slowest' speed
                this.speed = MovableObject.SLOWSPEED;
                this.wallPass = true;
                break;
        }

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

    }




}
