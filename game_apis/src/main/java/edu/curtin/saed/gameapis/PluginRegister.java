package edu.curtin.saed.gameapis;

public interface PluginRegister
{
    void registerMoveListener(MoveListener listener);

    void registerCollectListener(CollectListener listener);

    void registerMenuListener(MenuListener listener);
}
