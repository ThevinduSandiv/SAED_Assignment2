package edu.curtin.saed.gameapis;

public interface MoveListener
{
    void onMove(int row, int col);
    void onObstacleTraverse(int row, int col);
    void onMoveBlocked();

}
