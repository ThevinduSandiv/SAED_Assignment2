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

    private List<Collectable> requiredItems;

    public Obstacle(int x, int y, String icon, boolean isVisble)
    {
        this.x = x;
        this.y = y;
        this.icon = icon;
        this.isVisible = isVisble;

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
    public boolean performAction(Player player)
    {
        return false;
    }
}
