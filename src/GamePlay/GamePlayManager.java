package GamePlay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GameObject.GameObject;
import SystemController.GameState;
import GameObject.TileMap;
import SystemController.GameStateManager;
import GameObject.Player;

/**
 * Created by danielmacario on 14-10-29.
 */
public class GamePlayManager extends GameState implements ActionListener {

    private TileMap tileMap;
    private Player player;

    public GamePlayManager(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = new Player(0, 0, true, 2);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        GamePlayState currentState = player.getCurrentGamePlayState();
        if (currentState == GamePlayState.PAUSE) {

            //Transfer to pause menu goes here!

        } else if (currentState == GamePlayState.INGAME) {
            player.move();
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), null);
        }
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
    public void actionPerformed(ActionEvent actionEvent) {
        //TODO: not 100% sure this will be used
    }
}
