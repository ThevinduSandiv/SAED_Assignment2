package edu.curtin.saed.assignment2;


public class Path implements MapObject
{
    // Doesn't use a coordinate system - For the sake of simplicity

    private boolean isVisible;

    public Path(boolean isVisible)
    {
        this.isVisible = isVisible;
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
        return true;
    }

    @Override
    public void makeVisible()
    {
        isVisible = true;
    }

    @Override
    public int getRowPosition()
    {
        return 0; // Fake position
    }

    @Override
    public int getColPosition()
    {
        return 0; // Fake position
    }

    @Override
    public String getMapIcon()
    {
        return " ";
    }

    @Override
    public boolean performAction(Player player, Simulation sim)
    {
        GameExtensionPoint.getInstance().onMove(player.getRowPosition(), player.getColPosition());
        return true;
    }
}
