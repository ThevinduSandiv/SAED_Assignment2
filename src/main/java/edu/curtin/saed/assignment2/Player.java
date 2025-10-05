package edu.curtin.saed.assignment2;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    private int x;
    private int y;

    private List<Collectable> inventory;

    public Player(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.inventory = new ArrayList<>();
    }
}
