package edu.curtin.saed.assignment2;

public interface MapObject
{
    boolean isSolid();
    boolean isVisible();
    boolean isPresentInGame();

    int getXPosition();
    int getYPosition();

    void performAction(Player player);
}
