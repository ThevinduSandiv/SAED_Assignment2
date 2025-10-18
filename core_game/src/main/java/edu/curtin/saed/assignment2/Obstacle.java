package edu.curtin.saed.assignment2;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements MapObject
{
    private int row;
    private int col;
    private String icon;

    private boolean isPresent;
    private boolean isVisible;
    private boolean isUnbreakable;

    private List<String> requiredItems;

    public Obstacle(int row, int col, String icon, boolean isVisible, boolean isUnbreakable)
    {
        this.row = row;
        this.col = col;
        this.icon = icon;
        this.isVisible = isVisible;
        this.isUnbreakable = isUnbreakable;

        this.isPresent = true;
        this.requiredItems = new ArrayList<>();
    }

    public void addRequiredItem(String itemName)
    {
        requiredItems.add(itemName);
    }

    @Override
    public boolean isSolid()
    {
        return true;
    }

    @Override
    public boolean isVisible()
    {
        return isVisible;
    }

    @Override
    public boolean isPresentInGame()
    {
        return isPresent;
    }

    @Override
    public void makeVisible()
    {
        isVisible = true;
    }

    @Override
    public int getRowPosition()
    {
        return row;
    }

    @Override
    public int getColPosition()
    {
        return col;
    }

    @Override
    public String getMapIcon()
    {
        return icon;
    }

    @Override
    public boolean performAction(Player player, Simulation sim)
    {
        if(isUnbreakable)
        {
            sim.addMsgToShow(UIManager.getUIText("wall_hit"));
            GameExtensionPoint.getInstance().onMoveBlocked();
            return false;
        }

        List<Collectable> inventory = player.getInventory();
        boolean available = true;
        List<Collectable> removingItems = new ArrayList<>();

        for(String item : requiredItems)
        {
            boolean check = false;
            for(Collectable c : inventory)
            {
                if(c.getName().equals(item))
                {
                    check = true;
                    removingItems.add(c);
                    break;
                }
            }

            if(!check)
            {
                available = false;
                break;
            }
        }

        if(available)
        {
            inventory.removeAll(removingItems); // Remove the used-items
            sim.addMsgToShow(UIManager.getUIText("obstacle_pass"));
            sim.addMsgToShow(UIManager.getUIText("items_used_to_obstacle", getRequiredAsString()));
            GameExtensionPoint.getInstance().onObstacleTraverse(row, col);
            return true;
        }
        else
        {
            sim.addMsgToShow(UIManager.getUIText("obstacle_pass_req", getRequiredAsString()));
        }

        GameExtensionPoint.getInstance().onMoveBlocked();
        return false;
    }

    private String getRequiredAsString()
    {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (String item : requiredItems)
        {
            if(!first)
            {
                sb.append(", ");
            }
            first = false;
            sb.append("\"").append(item).append("\"");
        }
        return sb.toString();
    }
}
