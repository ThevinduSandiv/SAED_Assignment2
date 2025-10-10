package edu.curtin.saed.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Player implements MapObject
{
    private static final Logger logger = Logger.getLogger(Player.class.getName());


    private int x;
    private int y;
    private boolean isVisible;

    private List<Collectable> inventory;

    public Player(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.inventory = new ArrayList<>();
        this.isVisible = true;
    }

    public void move(int stepX, int stepY)
    {
        x += stepX;
        y += stepY;
        logger.info(String.format("Player moves the step -> x:%d ,y:%d", stepX, stepY));
    }

    public List<Collectable> getInventory()
    {
        return inventory;
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
        return true;
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
        return "P";
    }

    @Override
    public boolean performAction(Player player, Simulation sim)
    {
        // Do Nothing
        return true;
    }
}
