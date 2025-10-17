package edu.curtin.gameplugins;

import edu.curtin.saed.gameapis.GameAPI;
import edu.curtin.saed.gameapis.MoveListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Penalty implements MoveListener
{
    private GameAPI api;
    private long preTime;

    public Penalty(GameAPI api)
    {
        this.api = api;
        this.preTime = System.currentTimeMillis();
    }

    private void checkPenalty()
    {
        long currentTime = System.currentTimeMillis();

        if(currentTime - preTime > 5000)
        {
            int[] playerPos = api.getPlayerPos();
            int playerX = playerPos[0];
            int playerY = playerPos[1];

            // Getting all obstacles and collectables
            List<int[]> objectsPos = api.getAllObstaclePos();
            objectsPos.addAll(api.getAllCollectablePos());

            int adjacentCount = 0;
            List<int[]> unavailablePos = new ArrayList<>();

            for (int[] objPos : objectsPos)
            {
                int objX = objPos[0];
                int objY = objPos[1];

                // Check if an object is adjacent to the player (up, down, left, right)
                boolean isAdjacent =
                                (objX == playerX && objY == playerY - 1) || // Up
                                (objX == playerX && objY == playerY + 1) || // Down
                                (objX == playerX - 1 && objY == playerY) || // Left
                                (objX == playerX + 1 && objY == playerY);   // Right


                if (isAdjacent)
                {
                    adjacentCount++;
                    unavailablePos.add(objPos);
                }
            }

            if (adjacentCount < 3)
            {
                // add an object in an available adjacent position
                int[][] adj = {{playerX,playerY-1},{playerX,playerY+1},{playerX-1,playerY},{playerX+1,playerY}};
                List<int[]> available = new ArrayList<>();

                for(int[] pos : adj) {
                    boolean found = false;
                    for(int[] unavail : unavailablePos)
                    {
                        if(unavail[0] == pos[0] && unavail[1] == pos[1])
                        {
                            found = true;
                        }
                    }
                    if(!found)
                    {
                        available.add(pos);
                    }
                }

                if(!available.isEmpty()) {
                    int[] newPos = available.get(new Random().nextInt(available.size()));
                    api.addObstacle(newPos[0], newPos[1], "W", true, true);
                    //TODO: on next move or timer, and i think its
                }
            }
        }

        preTime = System.currentTimeMillis();
    }

    @Override
    public void onMove(int row, int col)
    {
        checkPenalty();
    }

    @Override
    public void onObstacleTraverse()
    {
        checkPenalty();
    }

    @Override
    public void onMoveBlocked()
    {
        checkPenalty();
    }
}
