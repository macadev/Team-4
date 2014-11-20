/**
 * Created by danielmacario on 14-11-12.
 * Created onwards from stage 6: Vasundhara Gupta November 14,2014
 */
package GameObject;

import java.io.Serializable;

/**
 * Defines the data contained in each of the 50 stages present in the game.
 */
public class Stages implements Serializable {

	// An array used to contain the data for all 50 stages present in the game.
	// Note that the first element is null, so that we can index the stages from
	// 1.
	public static final StageData[] gameStages = {
			null,

			new StageData(
					new EnemySet[] { new EnemySet(EnemyType.BALLOOM, 6) },
					PowerUpType.FLAMES, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.BALLOOM, 3),
					new EnemySet(EnemyType.ONEAL, 3) }, PowerUpType.BOMBS,
					false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.BALLOOM, 2),
					new EnemySet(EnemyType.ONEAL, 2),
					new EnemySet(EnemyType.DOLL, 2) }, PowerUpType.DETONATOR,
					false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.BALLOOM, 1),
					new EnemySet(EnemyType.ONEAL, 1),
					new EnemySet(EnemyType.DOLL, 2),
					new EnemySet(EnemyType.MINVO, 2) }, PowerUpType.SPEED,
					false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL, 4),
					new EnemySet(EnemyType.DOLL, 3) }, PowerUpType.BOMBS, false),

            new StageData(new EnemySet[] { new EnemySet(EnemyType.BALLOOM, 1) },
                    null, true),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL, 2),
					new EnemySet(EnemyType.DOLL, 3),
					new EnemySet(EnemyType.KONDORIA, 2) }, PowerUpType.FLAMES,
					false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL, 2),
					new EnemySet(EnemyType.DOLL, 3),
					new EnemySet(EnemyType.KONDORIA, 2) }, PowerUpType.FLAMES,
					false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL, 2),
					new EnemySet(EnemyType.DOLL, 3),
					new EnemySet(EnemyType.MINVO, 2) }, PowerUpType.BOMBS,
					false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL, 1),
					new EnemySet(EnemyType.DOLL, 2),
					new EnemySet(EnemyType.MINVO, 4) }, PowerUpType.DETONATOR,
					false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL, 1),
					new EnemySet(EnemyType.DOLL, 1),
					new EnemySet(EnemyType.MINVO, 4),
					new EnemySet(EnemyType.KONDORIA, 1) },
					PowerUpType.BOMBPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.OVAPI,1 ) },
					PowerUpType.WALLPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,3 ),
					new EnemySet(EnemyType.KONDORIA,1 ),
					new EnemySet(EnemyType.OVAPI,1 ) },
					PowerUpType.BOMBS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,4 ),
					new EnemySet(EnemyType.OVAPI,1 ) },
					PowerUpType.BOMBS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,3 ),
					new EnemySet(EnemyType.MINVO,3 ),
					new EnemySet(EnemyType.KONDORIA,3) },
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.OVAPI,7 ),
					new EnemySet(EnemyType.PASS,1 ) },
					PowerUpType.BOMBPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,3 ),
					new EnemySet(EnemyType.KONDORIA,3),
					new EnemySet(EnemyType.PASS,1 )},
					PowerUpType.FLAMES, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.MINVO,3 ),
					new EnemySet(EnemyType.KONDORIA,4 ),
					new EnemySet(EnemyType.PASS,1 )},
					PowerUpType.WALLPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,5 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.PASS,1 )},
					PowerUpType.BOMBS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.BALLOOM,3 ),
					new EnemySet(EnemyType.ONEAL,3 ),
					new EnemySet(EnemyType.PASS,2 )},
					PowerUpType.BOMBPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.BALLOOM,1 ),
					new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.DOLL,3 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,2) },
					PowerUpType.BOMBS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,2 )},
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.KONDORIA,4 ),
					new EnemySet(EnemyType.OVAPI,3 ),
					new EnemySet(EnemyType.PASS,2 ) },
					PowerUpType.BOMBPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,4 ),
					new EnemySet(EnemyType.MINVO,3 ),
					new EnemySet(EnemyType.KONDORIA,1 ),
					new EnemySet(EnemyType.PASS,1 )},
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,2 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,1 ) },
					PowerUpType.BOMBS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,4 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,1 ) },
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL,2 ),
					new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,1 )},
					PowerUpType.BOMBPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.BALLOOM,1 ),
					new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,1 )},
					PowerUpType.MYSTERY, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.BALLOOM,1 ),
					new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.KONDORIA,5),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,1 ) },
					PowerUpType.FLAMES, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.DOLL,3 ),
					new EnemySet(EnemyType.MINVO,3 ),
					new EnemySet(EnemyType.KONDORIA,1 ),
					new EnemySet(EnemyType.PASS,1 ) },
					PowerUpType.BOMBS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,5 ),
					new EnemySet(EnemyType.PASS,2 ) },
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,3 ),
					new EnemySet(EnemyType.MINVO,2 ),
					new EnemySet(EnemyType.KONDORIA,1 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,1 ) },
					PowerUpType.FLAMEPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL,2 ),
					new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,2 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,2 ) },
					PowerUpType.WALLPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.ONEAL,1 ),
					new EnemySet(EnemyType.DOLL,1),
					new EnemySet(EnemyType.MINVO,3 ),
					new EnemySet(EnemyType.KONDORIA,4 ),
					new EnemySet(EnemyType.PASS,1 ) },
					PowerUpType.BOMBS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,2 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,2 ) },
					PowerUpType.BOMBS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,3 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.PASS,2 ) },
					PowerUpType.MYSTERY, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,2 ) },
					PowerUpType.BOMBPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,2 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.PASS,3 )},
					PowerUpType.FLAMEPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,3 ) },
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,2 ),
					new EnemySet(EnemyType.MINVO,2 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.PASS,3 )},
					PowerUpType.FLAMES, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,1),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,4 ) },
					PowerUpType.WALLPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,2),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.PASS,4 ) },
					PowerUpType.MYSTERY, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.DOLL,1 ),
					new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,4 ) },
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,3 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,5 ) },
					PowerUpType.WALLPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,6 )},
					PowerUpType.BOMBPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.MINVO,1 ),
					new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,6 )},
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,6 )},
					PowerUpType.MYSTERY, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,6 )},
					PowerUpType.WALLPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,6 )},
					PowerUpType.BOMBPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.KONDORIA,2 ),
					new EnemySet(EnemyType.OVAPI,1 ),
					new EnemySet(EnemyType.PASS,6 ),
					new EnemySet(EnemyType.PONTAN,1 ) },
					PowerUpType.DETONATOR, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.KONDORIA,1 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,6 ),
					new EnemySet(EnemyType.PONTAN,1 ) },
					PowerUpType.FLAMEPASS, false),

			new StageData(new EnemySet[] { new EnemySet(EnemyType.KONDORIA,1 ),
					new EnemySet(EnemyType.OVAPI,2 ),
					new EnemySet(EnemyType.PASS,5 ),
					new EnemySet(EnemyType.PONTAN,2 ) },
					PowerUpType.MYSTERY, false),
	};

}
