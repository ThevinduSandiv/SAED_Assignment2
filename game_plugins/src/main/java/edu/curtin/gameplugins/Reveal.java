package edu.curtin.gameplugins;

import edu.curtin.saed.gameapis.CollectListener;
import edu.curtin.saed.gameapis.GameAPI;

import java.util.List;

public class Reveal implements CollectListener
{
    private GameAPI api;
    public Reveal(GameAPI  api)
    {
        this.api = api;
    }
    @Override
    public void onCollect(String itemName, int row, int col)
    {
        if(itemName.equals("map")) // TODO use unicode later
        {
            api.removeFromInventory(itemName);

            // Reveal the goal
            int[] goalPos = api.getGoalPos();
            api.makePosVisible(goalPos[0], goalPos[1]);

            // Reveal the items
            List<int[]> itemsPos = api.getAllCollectablePos();
            for(int[] p : itemsPos)
            {
                api.makePosVisible(p[0], p[1]);
            }
        }
    }
}
