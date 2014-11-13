/**
 * Created by danielmacario on 14-11-12.
 */
package GameObject;

/**
 * Defines the data contained in each of the 50 stages present in the game.
 */
public class Stages {

    //An array used to contain the data for all 50 stages present in the game.
    //Note that the first element is null, so that we can index the stages from 1.
    public static final StageData[] gameStages =
    {
        null,

        new StageData(
            new EnemySet[] { new EnemySet(EnemyType.BALLOOM, 6)},
            PowerUpType.FLAMES, false
        ),

        new StageData(
            new EnemySet[] { new EnemySet(EnemyType.BALLOOM, 3),
            new EnemySet(EnemyType.ONEAL, 3)},
            PowerUpType.BOMBS, false
        ),
        new StageData(
            new EnemySet[] { new EnemySet(EnemyType.BALLOOM, 2),
            new EnemySet(EnemyType.ONEAL, 2),
            new EnemySet(EnemyType.DOLL, 2)},
            PowerUpType.DETONATOR, false
        ),
        new StageData(new EnemySet[] {
            new EnemySet(EnemyType.BALLOOM, 1),
            new EnemySet(EnemyType.ONEAL, 1),
            new EnemySet(EnemyType.DOLL, 2),
            new EnemySet(EnemyType.MINVO, 2)},
            PowerUpType.SPEED, false
        ),
        new StageData(new EnemySet[] {
            new EnemySet(EnemyType.ONEAL, 4),
            new EnemySet(EnemyType.DOLL, 3)},
        PowerUpType.BOMBS, false
        ),
        new StageData(new EnemySet[] {
            new EnemySet(EnemyType.BALLOOM, 1)},
            null,
            true
        )
    };

}
