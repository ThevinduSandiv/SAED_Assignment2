package edu.curtin.saed.assignment2;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements MapObject
{
    private int x;
    private int y;

    private boolean isPresent;

    private List<Collectable> requiredItems;

    public Obstacle(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.isPresent = true;
        this.requiredItems = new ArrayList<>();
    }

    public void addRequiredItem(Collectable item)
    {
        requiredItems.add(item);
    }

    @Override
    public boolean isSolid()
    {
        return true;
    }

    @Override
    public boolean isVisible()
    {
        return false;
    }

    @Override
    public boolean isPresentInGame()
    {
        return isPresent;
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
    public void performAction(Player player)
    {

    }
}
