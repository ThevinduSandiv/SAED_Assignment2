package edu.curtin.gameplugins;

import edu.curtin.saed.gameapis.GameAPI;
import edu.curtin.saed.gameapis.MoveListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Penalty implements MoveListener
{
    private static final Logger logger = Logger.getLogger(Penalty.class.getName());

    private GameAPI api;
    private long preTime;

    public Penalty(GameAPI api)
    {
        this.api = api;
        this.preTime = System.currentTimeMillis();
        logger.info("Penalty Plugin Loaded");
    }

    private void checkPenalty(int playerRow, int playerCol)
    {
        long currentTime = System.currentTimeMillis();

        logger.info(() -> "Passed time between moves: " + (currentTime - preTime) + " ms");
        if(currentTime - preTime > 5000)
        {
            logger.info("Penalty Timed Out");
            // Getting all obstacles and collectables
            List<int[]> objectsPos = api.getAllObstaclePos();
            objectsPos.addAll(api.getAllCollectablePos());

            int adjacentCount = 0;
            List<int[]> unavailablePos = new ArrayList<>();

            for (int[] objPos : objectsPos)
            {
                int objRow = objPos[0];
                int objCol = objPos[1];

                // Check if an object is adjacent to the player (up, down, left, right)
                boolean isAdjacent =
                                (objRow == playerRow && objCol == playerCol - 1) || // Left
                                (objRow == playerRow && objCol == playerCol + 1) || // Right
                                (objRow == playerRow - 1 && objCol == playerCol) || // Up
                                (objRow == playerRow + 1 && objCol == playerCol);   // Down


                if (isAdjacent)
                {
                    adjacentCount++;
                    unavailablePos.add(objPos);
                }
            }

            if (adjacentCount < 3)
            {
                // add an object in an available adjacent position
                int[][] adj = {
                        {playerRow, playerCol - 1},  // Left
                        {playerRow, playerCol + 1},  // Right
                        {playerRow - 1, playerCol},  // Up
                        {playerRow + 1, playerCol}   // Down
                };
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
                    logger.info(() -> "Adding a penalty at: " + available.get(0)[0] + ", " + available.get(0)[1]);
                    int[] newPos = available.get(new Random().nextInt(available.size()));
                    api.addObstacle(newPos[0], newPos[1], "W", true, true);
                    //TODO: somethings off
                }
                logger.info("Penalty adding done (if valid)");
            }
        }
        preTime = System.currentTimeMillis();
    }

    @Override
    public void onMove(int row, int col)
    {
        checkPenalty(row, col);
    }

    @Override
    public void onObstacleTraverse(int row, int col)
    {
        checkPenalty(row, col);
    }

    @Override
    public void onMoveBlocked()
    {
        // Keep bumping into things will not register as a "Move"
    }
}
