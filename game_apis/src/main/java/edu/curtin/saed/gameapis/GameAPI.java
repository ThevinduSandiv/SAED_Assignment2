package edu.curtin.saed.gameapis;

import java.util.List;

public interface GameAPI
{
    void setPlayerPos(int row, int col);

    void addObstacle(int row, int col, String icon, boolean isVisible);

    void addCollectable(String name, int row, int col, String msg, String icon);

    List<int[]> getAllObstaclePos();

    List<int[]> getAllCollectablePos();

    void makePosVisible(int row, int col);

    String getInventoryString();

    // Set player pos directly
    // add map objects
    // get all map objects OR at least collectables
    // get inventory
}
