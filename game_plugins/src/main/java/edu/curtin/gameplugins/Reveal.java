package edu.curtin.gameplugins;

import edu.curtin.saed.gameapis.CollectListener;
import edu.curtin.saed.gameapis.GameAPI;

public class Reveal implements CollectListener
{
    private GameAPI api;
    public Reveal(GameExtensionPoint  api)
    {
        this.api = api;
    }
    @Override
    public void onCollect(String itemName, int row, int col)
    {
        if(itemName.equals("map")) // TODO use unicode later
        {
            // Reveal the goal
            int[] goalPos = api.getGoalPos();
            api.makePosVisible(goalPos[0], goalPos[1]);
        }
    }
}
