package GameObject.ArtificialIntelligence;

import GameObject.Enemy;

import java.io.Serializable;

/**
 *Class used to establish the logic implemented by low intelligence enemies.Low intelligence enemies move in one
 * direction until they hit an obstacle. Once these type of enemies hit an obstacle, they will then move in the
 * opposite direction. This class inherits methods from the ArtificalIntelligence class.
 */
public class LowIntelligence extends ArtificialIntelligence implements Serializable{

    @Override
    /**
     * Controls the movement of the low-intelligence enemy on the grid.Uses the attribute moveEnemyOnBoard so that
     * enemy does move.
     * @param enemy Enemy object to be moved on the grid.
     */
    public void move(Enemy enemy) {
        moveEnemyOnBoard(enemy);
    }

    /**This method is utilised so that the enemy can chase the player
     * @param playerPosX x coordinate of the player object on the grid.
     * @param playerPosY y coordinate of the player object on the grid.
     * @param distanceFromEnemyToPlayer
     * @param enemy Enemy object that will start chasing the player.
     */
    @Override
    public void chasePlayer(int playerPosX, int playerPosY, int distanceFromEnemyToPlayer, Enemy enemy) {

    }

}
