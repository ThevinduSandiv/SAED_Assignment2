package edu.curtin.gameplugins;

import edu.curtin.saed.gameapis.CollectListener;
import edu.curtin.saed.gameapis.GameAPI;
import edu.curtin.saed.gameapis.MoveListener;

import java.util.logging.Logger;

public class Prize implements CollectListener, MoveListener
{
    private static final Logger logger = Logger.getLogger(Prize.class.getName());

    private int numOfItems;
    private int numOfObstaclesTraversed;
    private boolean added;
    private GameAPI api;

    public Prize(GameAPI api)
    {
        this.numOfItems = 0;
        this.numOfObstaclesTraversed = 0;
        this.added = false;
        this.api = api;
        logger.info("Prize plugin loaded.");
    }

    private void checkCondition()
    {
        logger.info(() -> "Checking condition: " + numOfItems + " items, " + numOfObstaclesTraversed + " obstacles traversed");
        if(numOfItems + numOfObstaclesTraversed >= 5 && !added)
        {
            api.addToInventory("Shiny Infinity Stone", "Restore humanity!", "$");
            added = true;
        }
    }

    @Override
    public void onCollect(String itemName, int row, int col)
    {
        numOfItems++;
        checkCondition();
    }

    @Override
    public void onMove(int row, int col)
    {
        // Do Nothing
    }

    @Override
    public void onObstacleTraverse(int row, int col)
    {
        numOfObstaclesTraversed++;
        checkCondition();
    }

    @Override
    public void onMoveBlocked()
    {
        // Do nothing
    }
}
