package edu.curtin.saed.assignment2;

public class Goal implements MapObject
{
    private int row;
    private int col;
    private boolean isVisible;

    public Goal(int row, int col)
    {
        this.row = row;
        this.col = col;
        this.isVisible = false;
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
        return "G";
    }

    @Override
    public boolean performAction(Player player, Simulation sim)
    {
        sim.addMsgToShow(UITranslator.get("won"));
        sim.endGame();
        return true;
    }
}
