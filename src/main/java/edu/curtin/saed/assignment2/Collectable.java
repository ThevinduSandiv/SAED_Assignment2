package edu.curtin.saed.assignment2;

public class Collectable implements MapObject
{
    private String name;
    private int row;
    private int col;
    private String msg;
    private String icon;
    private boolean isPresent;
    private boolean isVisible;

    public Collectable(String name, int row, int col, String msg, boolean isPresent, String icon)
    {
        this.name = name;
        this.row = row;
        this.col = col;
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
        player.getInventory().add(this);
        sim.addMsgToShow("You collected a " + name + "!");
        sim.addMsgToShow(msg);
        return true;
    }
}
