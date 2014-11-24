/**
 * Created by danielmacario on 14-11-17.
 */
package GamePlay;
import Database.DatabaseController;
import GameObject.KillSet;

import java.io.Serializable;
import java.util.*;

public class ScoreManager implements Serializable {

    public int determineScoreFromKills(ArrayList<KillSet> enemiesKilled) {

        System.out.println(enemiesKilled.get(0).getScore());
        if (enemiesKilled.size() == 1) return enemiesKilled.get(0).getScore();

        Hashtable<Coordinate, ArrayList<KillSet>> killsPerBomb = new Hashtable<Coordinate, ArrayList<KillSet>>();

        for (KillSet deadEnemy : enemiesKilled) {

            Coordinate locationOfBomb = deadEnemy.getLocationOfBomb();

            System.out.println("Bomb location COL = " + locationOfBomb.getCol() + " ROW = " + locationOfBomb.getRow());

            if (killsPerBomb.containsKey(locationOfBomb)) {
                System.out.println("Adding to existing set!");
                ArrayList<KillSet> bombKills = killsPerBomb.get(locationOfBomb);
                bombKills.add(deadEnemy);
            } else {
                System.out.println("New kill set");
                ArrayList<KillSet> bombKills = new ArrayList<KillSet>();
                bombKills.add(deadEnemy);
                killsPerBomb.put(locationOfBomb, bombKills);
            }

        }

        Set<Coordinate> keys = killsPerBomb.keySet();

        int score = 0;
        for (Coordinate key: keys){
            ArrayList<KillSet> kills = killsPerBomb.get(key);
            score += calculateScore(kills);
        }

        return score;

    }

    private int calculateScore(ArrayList<KillSet> kills) {
        int score = 0;

        //Sort the kills based on distance to the bomb
        Collections.sort(kills, new Comparator<KillSet>() {
            public int compare(KillSet o1, KillSet o2) {
                return o1.getDistanceToBombOrigin() - o2.getDistanceToBombOrigin();
            }
        });

        for (KillSet kill : kills) {
            System.out.println("Is distance sorted : " + kill.getDistanceToBombOrigin());
        }


        if (kills.size() == 2) {
            System.out.println("Killed two enemies");
            score = determineScoreForTwoKills(kills.get(0), kills.get(1));
        } else {
            score = determineScoreForManyKills(kills);
        }

        return score;
    }

    private int determineScoreForManyKills(ArrayList<KillSet> kills) {

        int score = 0;
        int multiplier = 0;
        for (KillSet deadEnemy : kills) {
            if (multiplier == 0) {
                score += deadEnemy.getScore();
                multiplier = 1;
            } else {
                score += (deadEnemy.getScore() * multiplier);
            }
            multiplier *= 2;
        }


        return score;
    }

    private int determineScoreForTwoKills(KillSet killedEnemyA, KillSet killedEnemyB) {
        int killedEnemyAScore = killedEnemyA.getScore();
        int killedEnemyBScore = killedEnemyB.getScore();
        if (killedEnemyAScore < killedEnemyBScore) {
            return killedEnemyAScore * 2 + killedEnemyBScore;
        }
        return killedEnemyBScore * 2 + killedEnemyAScore;
    }

}
