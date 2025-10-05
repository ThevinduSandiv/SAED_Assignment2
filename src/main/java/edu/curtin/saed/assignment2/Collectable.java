package edu.curtin.saed.assignment2;

public class Collectable implements MapObject
{
    private String name;
    private int x;
    private int y;
    private boolean isPresent;

    public Collectable(String name, int x, int y, boolean isPresent)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.isPresent = isPresent;
    }

    @Override
    public boolean isSolid()
    {
        return false;
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
