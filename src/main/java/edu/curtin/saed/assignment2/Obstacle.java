package edu.curtin.saed.assignment2;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements MapObject
{
    private int x;
    private int y;
    private String icon;

    private boolean isPresent;
    private boolean isVisible;
    private boolean isUnbreakable;

    private List<String> requiredItems;

    public Obstacle(int x, int y, String icon, boolean isVisible, boolean isUnbreakable)
    {
        this.x = x;
        this.y = y;
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
    public int getXPosition()
    {
        return x;
    }

    @Override
    public int getYPosition()
    {
        return y;
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
            sim.addMsgToShow("Its a wall, you idiot!"); //TODO: change to a better msg.
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
            sim.addMsgToShow("Hooray!! You pass the obstacle.");
            sim.addMsgToShow("You have used items " + getRequiredAsString());
            return true;
        }
        else
        {
            sim.addMsgToShow(String.format("You need to collect %s to pass this obstacle!", getRequiredAsString()));
        }


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
