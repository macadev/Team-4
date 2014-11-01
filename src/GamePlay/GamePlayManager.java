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
    private Color titleColor;
    private Font titleFont;

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

            //This section is just for the demo.
            //TODO: remove after demo
            titleColor = new Color(230, 200, 0);
            titleFont = new Font("Century Gothic", Font.PLAIN, 28);
            g.setColor(titleColor);
            g.setFont(titleFont);
            g.setPaint(new Color(255,255,255));
            g.drawString("Game Paused", 80, 70);

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
