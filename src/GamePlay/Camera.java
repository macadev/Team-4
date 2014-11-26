/**
 * Created by danielmacario on 14-11-02.
 */
package GamePlay;

import GameObject.Player;
import SystemController.GameController;
import java.io.Serializable;

public class Camera implements Serializable {

    private int posX;
    private Player player;

    public Camera(int posX, Player player) {
        this.posX = posX;
        this.player = player;
    }

    public void adjustPosition() {
        posX = -player.getPosX() + GameController.WIDTH/2 - 18;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

}
