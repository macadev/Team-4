package GameObject;

/**
 * Created by danielmacario on 14-11-12.
 */
public class Stages {

    public static final StageData[] gameStages =
    {
        null,

        new StageData(
            new Tuple[] { new Tuple(EnemyType.BALLOOM, 6)},
            PowerUpType.FLAMES, false
        ),

        new StageData(
            new Tuple[] { new Tuple(EnemyType.BALLOOM, 3),
            new Tuple(EnemyType.ONEAL, 3)},
            PowerUpType.BOMBS, false
        ),
        new StageData(
            new Tuple[] { new Tuple(EnemyType.BALLOOM, 2),
            new Tuple(EnemyType.ONEAL, 2),
            new Tuple(EnemyType.DOLL, 2)},
            PowerUpType.DETONATOR, false
        ),
        new StageData(new Tuple[] {
            new Tuple(EnemyType.BALLOOM, 1),
            new Tuple(EnemyType.ONEAL, 1),
            new Tuple(EnemyType.DOLL, 2),
            new Tuple(EnemyType.MINVO, 2)},
            PowerUpType.SPEED, false
        ),
        new StageData(new Tuple[] {
            new Tuple(EnemyType.ONEAL, 4),
            new Tuple(EnemyType.DOLL, 3)},
        PowerUpType.BOMBS, false
        ),
        new StageData(new Tuple[] {
            new Tuple(EnemyType.BALLOOM, 1)},
            null,
            true
        )
    };

}
