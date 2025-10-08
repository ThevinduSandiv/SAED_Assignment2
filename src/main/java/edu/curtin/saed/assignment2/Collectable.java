package edu.curtin.saed.assignment2;

public class Collectable implements MapObject
{
    private String name;
    private int x;
    private int y;
    private String msg;
    private String icon;
    private boolean isPresent;
    private boolean isVisible;

    public Collectable(String name, int x, int y, String msg, boolean isPresent, String icon)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.msg = msg;
        this.icon = icon;
        this.isPresent = isPresent;
        this.isVisible = false;
    }

    public String getName()
    {
        return name;
    }

    public String getMsg()
    {
        return msg;
    }

    @Override
    public boolean isSolid()
    {
        return false;
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
        // Do other stuff
        return true;
    }
}
