package edu.curtin.saed.assignment2;

public interface MapObject
{
    boolean isSolid();
    boolean isVisible();
    boolean isPresentInGame();
    void makeVisible();

    int getXPosition();
    int getYPosition();
    String getMapIcon();

    boolean performAction(Player player, Simulation sim);
}
