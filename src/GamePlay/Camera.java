package GamePlay;

import GameObject.Player;
import SystemController.GameController;

/**
 * Created by danielmacario on 14-11-02.
 */
public class Camera {

    private int posX;
    private int posY;
    private Player player;

    public Camera(int posX, int posY, Player player) {
        this.posX = posX;
        this.posY = posY;
        this.player = player;
    }

    public void adjustPosition() {
        posX = -player.getPosX() + GameController.WIDTH/2;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }


}
