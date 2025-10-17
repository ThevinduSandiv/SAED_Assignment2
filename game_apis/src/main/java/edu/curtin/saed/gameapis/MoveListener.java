package edu.curtin.saed.gameapis;

public interface MoveListener
{
    void onMove(int row, int col);
    void onObstacleTraverse();
    void onMoveBlocked();

}
