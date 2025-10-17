package edu.curtin.saed.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Player implements MapObject
{
    private static final Logger logger = Logger.getLogger(Player.class.getName());


    private int row; // Row
    private int col; // Column
    private boolean isVisible;

    private List<Collectable> inventory;

    public Player(int row, int col)
    {
        this.row = row;
        this.col = col;
        this.inventory = new ArrayList<>();
        this.isVisible = true;
    }

    public Player setRow(int row)
    {
        this.row = row;
        return this;
    }

    public Player setCol(int col)
    {
        this.col = col;
        return this;
    }

    public void move(int stepRow, int stepCol)
    {
        row += stepRow;
        col += stepCol;
        logger.info(String.format("Player moves the step -> row:%d ,col:%d", stepRow, stepCol));
    }

    public List<Collectable> getInventory()
    {
        return inventory;
    }

    public String getInventoryAsString()
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append("[");
        for(Collectable c : inventory)
        {
            if(!first)
            {
                sb.append(", ");
            }
            else
            {
                first = false;
            }
            sb.append("\"").append(c.getName()).append("\"");
        }
        sb.append("]");
        return sb.toString();
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
        return "P";
    }

    @Override
    public boolean performAction(Player player, Simulation sim)
    {
        // Do Nothing
        return true;
    }
}
