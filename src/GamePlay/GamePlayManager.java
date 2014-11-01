package GamePlay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import GameObject.ConcreteWall;
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
    private ArrayList<ConcreteWall> concreteWalls;
    private Spawner spawner;

    //Temporary variables
    //TODO: remove after demo
    private Color titleColor;
    private Font titleFont;

    public GamePlayManager(GameStateManager gsm) {
        this.gsm = gsm;
        this.player = new Player(50, 50, true, 2);
        this.spawner = new Spawner();
        /*
        * TODO: we should call populate within the init method,
        * but there seems to be a race condition that I can't understand
        */
        populateGridWithBlocks();
    }

    public void drawBlocks(Graphics2D g) {
        for (ConcreteWall concreteWall : concreteWalls) {
            g.drawImage(concreteWall.getImage(), concreteWall.getPosX(), concreteWall.getPosY(), null);
        }
    }

    public void drawPlayer(Graphics2D g) {
        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), null);
    }

    public void populateGridWithBlocks() {
        concreteWalls = spawner.generateConcreteWalls();
    }

    @Override
    public void init() {
        System.out.println("Init called boi");

        //Populate the blocks arrayLists present in the game;
        //populateGridWithBlocks();

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

            //Move the player each time we render again
            //Movement depends on the deltaX and deltaY
            //values of the MovableObject class
            player.move();

            drawPlayer(g);
            drawBlocks(g);
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
