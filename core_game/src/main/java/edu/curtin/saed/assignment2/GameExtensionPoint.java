package edu.curtin.saed.assignment2;

import edu.curtin.saed.gameapis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GameExtensionPoint implements PluginRegister, GameAPI
{
    private static final Logger logger = Logger.getLogger(GameExtensionPoint.class.getName());

    private static GameExtensionPoint instance;

    private final List<MoveListener> moveListeners;
    private final List<CollectListener> collectListeners;
    private final List<MenuListener> menuListeners;
    private Simulation sim;


    public static GameExtensionPoint getInstance()
    {
        if(instance == null)
        {
            instance = new GameExtensionPoint();
        }
        return instance;
    }

    private GameExtensionPoint()
    {
        this.moveListeners = new ArrayList<>();
        this.collectListeners = new ArrayList<>();
        this.menuListeners = new ArrayList<>();
    }

    public void setSim(Simulation sim)
    {
        this.sim = sim;
    }

    // Registration
    @Override
    public void registerMoveListener(MoveListener listener)
    {
        moveListeners.add(listener);
    }

    @Override
    public void registerCollectListener(CollectListener listener)
    {
        logger.info("Registering Collect Listener");
        collectListeners.add(listener);
    }

    @Override
    public void registerMenuListener(MenuListener listener)
    {
        menuListeners.add(listener);
    }

    // Notify
    public void onMove(int row, int col)
    {
        for(MoveListener l : moveListeners)
        {
            l.onMove(row, col);
        }
    }

    public void onMoveBlocked()
    {
        for(MoveListener l : moveListeners)
        {
            l.onMoveBlocked();
        }
    }

    public void onObstacleTraverse(int row, int col)
    {
        for(MoveListener l : moveListeners)
        {
            l.onObstacleTraverse(row, col);
        }
    }

    public void onCollect(String itemName, int row, int col)
    {
        logger.info("Calling OnCollect Method");
        for(CollectListener l : collectListeners)
        {
            l.onCollect(itemName, row, col);
            logger.info("Calling the listener");
        }
    }

    public void onMenuOptionSelected(char option)
    {
        for(MenuListener l : menuListeners)
        {
            l.onMenuOptionSelected(option);
        }
    }

    // React
    @Override
    public int[] getPlayerPos()
    {
        return new int[]{sim.getPlayer().getRowPosition(), sim.getPlayer().getColPosition()};
    }

    @Override
    public void addToInventory(String name, String msg, String icon)
    {
        sim.getPlayer().getInventory().add(new Collectable(name, 0, 0, msg, false, icon)); // Position is fake
    }

    @Override
    public void removeFromInventory(String name)
    {
        List<Collectable> inventory = sim.getPlayer().getInventory();

        // Removes the first occurrence of the item
        for(Collectable c : inventory)
        {
            if(c.getName().equals(name)) //TODO: unicode
            {
                inventory.remove(c);
                break;
            }
        }
    }

    @Override
    public void setPlayerPos(int row, int col)
    {
        sim.getPlayer().setRow(row).setCol(col);
        sim.refreshMap();
    }

    @Override
    public void addObstacle(int row, int col, String icon, boolean isVisible, boolean unbreakable)
    {
        Obstacle obstacle = new Obstacle(row, col, icon, isVisible, unbreakable);
        sim.addObstacles(new ArrayList<>()
        {{
            add(obstacle);
        }});
    }

    @Override
    public void addCollectable(String name, int row, int col, String msg, String icon)
    {
        Collectable collectable = new Collectable(name, row, col, msg, true, icon);
        sim.addCollectables(new ArrayList<>()
        {{
            add(collectable);
        }});
    }

    @Override
    public List<int[]> getAllObstaclePos()
    {
        MapObject[][] map = sim.getMap();
        List<int[]> obstaclePositions = new ArrayList<>();
        for(MapObject[] mapObjects : map)
        {
            for(MapObject obj : mapObjects)
            {
                if(obj instanceof Obstacle)
                {
                    obstaclePositions.add(new int[]{obj.getRowPosition(), obj.getColPosition()});
                }
            }
        }
        return obstaclePositions;
    }

    @Override
    public List<int[]> getAllCollectablePos()
    {
        MapObject[][] map = sim.getMap();
        List<int[]> collectablePositions = new ArrayList<>();
        for(MapObject[] mapObjects : map)
        {
            for(MapObject obj : mapObjects)
            {
                if(obj instanceof Collectable)
                {
                    collectablePositions.add(new int[]{obj.getRowPosition(), obj.getColPosition()});
                }
            }
        }
        return collectablePositions;
    }

    @Override
    public int[] getGoalPos()
    {
        return new int[]{sim.getGoal().getRowPosition(), sim.getGoal().getColPosition()};
    }

    @Override
    public void makePosVisible(int row, int col)
    {
        MapObject[][] map = sim.getMap();
        map[row + Simulation.offset][col + Simulation.offset].makeVisible();
    }

    @Override
    public String getInventoryString()
    {
        return sim.getPlayer().getInventoryAsString();
    }
}
