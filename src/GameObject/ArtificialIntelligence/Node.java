/**
 * Created by danielmacario on 14-11-22.
 */
package GameObject.ArtificialIntelligence;

import java.util.ArrayList;
import java.util.List;

/**
 * Node object used to represent tiles in the graph that the high intelligence
 * enemies use to determine the shortest path to the player.
 */
public class Node {

    private List<Node> neighbors = new ArrayList<Node>();
    private Node parent;
    private int overallCost;
    private int costToReachNodeFromStart;
    private int estimatedCostToReachDestination;
    private int x;
    private int y;
    private int cost;
    private boolean isObstacle;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.cost = 1;
    }

    public void addNeighbors(Node[] arr) {
        for (Node node : arr) {
            neighbors.add(node);
        }
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getOverallCost() {
        return overallCost;
    }

    public void setOverallCost(int overallCost) {
        this.overallCost = overallCost;
    }

    public int getCostToReachNodeFromStart() {
        return costToReachNodeFromStart;
    }

    public void setCostToReachNodeFromStart(int costToReachNodeFromStart) {
        this.costToReachNodeFromStart = costToReachNodeFromStart;
    }

    public int getEstimatedCostToReachDestination() {
        return estimatedCostToReachDestination;
    }

    public void setEstimatedCostToReachDestination(int estimatedCostToReachDestination) {
        this.estimatedCostToReachDestination = estimatedCostToReachDestination;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }
}
