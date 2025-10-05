package edu.curtin.saed.assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Simulation
{
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());

    private int gridH;
    private int gridW;

    private List<MapObject> map;

    public Simulation(int gridH, int gridW)
    {
        this.gridH = gridH;
        this.gridW = gridW;

        map = new ArrayList<>();
    }

    public String getDrawableMap()
    {
        int offset = 1; // shift everything right/down for negative walls
        String[][] mapStr = new String[gridH + 2][gridW + 2]; // +2 for walls

        // Fill map array with default empty space
        for (int y = 0; y < gridH + 2; y++)
        {
            for(int x = 0; x < gridW + 2; x++)
            {
                mapStr[y][x] = " "; // empty space
            }
        }

        int count = 1;
        // Place objects in map
        for (MapObject obj : map)
        {
            if (obj.isPresentInGame())
            {
                int drawX = obj.getXPosition() + offset;
                int drawY = obj.getYPosition() + offset;

                if (obj.isSolid())
                    mapStr[drawY][drawX] = "#"; // wall/obstacle
                else
                    mapStr[drawY][drawX] = "*"; // item
            }
            logger.info(String.format("Placed object %d at (%d, %d)", count, obj.getXPosition(), obj.getYPosition()));
            count++;
        }

        // Build string row by row
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < gridH + 2; y++)
        {
            for (int x = 0; x < gridW + 2; x++)
            {
                sb.append(mapStr[y][x]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
































    // TODO: Remove Later
    public void testMapGenerator()
    {
        this.gridH = 12;
        this.gridW = 13;

        // Collectables
        Collectable superTurquoiseMap = new Collectable("superlative turquoise map", 3, 0, true);
        Collectable glowingGreyBook = new Collectable("glowing grey book", 11, 1, true);
        Collectable ornateGreyPendant1 = new Collectable("ornate grey pendant", 8, 12, true);
        Collectable ornateGreyPendant2 = new Collectable("ornate grey pendant", 7, 11, true);
        Collectable ornateGreyPendant3 = new Collectable("ornate grey pendant", 7, 1, true);
        Collectable ornateGreyPendant4 = new Collectable("ornate grey pendant", 1, 1, true);
        Collectable lustrousYellowFlask1 = new Collectable("lustrous yellow flask", 3, 5, true);
        Collectable lustrousYellowFlask2 = new Collectable("lustrous yellow flask", 7, 10, true);
        Collectable lustrousYellowFlask3 = new Collectable("lustrous yellow flask", 1, 5, true);
        Collectable lustrousYellowFlask4 = new Collectable("lustrous yellow flask", 5, 12, true);
        Collectable ornatePendant1 = new Collectable("ornate pendant", 8, 0, true);
        Collectable ornatePendant2 = new Collectable("ornate pendant", 11, 3, true);
        Collectable lustrousMap1 = new Collectable("lustrous map", 7, 9, true);
        Collectable lustrousMap2 = new Collectable("lustrous map", 1, 12, true);
        Collectable lustrousMap3 = new Collectable("lustrous map", 11, 7, true);
        Collectable lustrousMap4 = new Collectable("lustrous map", 9, 7, true);
        Collectable wornCrown1 = new Collectable("worn crown", 2, 2, true);
        Collectable wornCrown2 = new Collectable("worn crown", 4, 4, true);
        Collectable wornCrown3 = new Collectable("worn crown", 4, 9, true);
        Collectable wornCrown4 = new Collectable("worn crown", 4, 0, true);
        Collectable antiqueRedCase1 = new Collectable("antique red case", 9, 2, true);
        Collectable antiqueRedCase2 = new Collectable("antique red case", 8, 3, true);
        Collectable antiqueRedCase3 = new Collectable("antique red case", 0, 2, true);
        Collectable antiqueRedCase4 = new Collectable("antique red case", 5, 10, true);
        Collectable antiqueRedCase5 = new Collectable("antique red case", 9, 0, true);
        Collectable charmedGem = new Collectable("charmed gem", 2, 9, true);

        // Add collectables to map
        map.add(superTurquoiseMap); map.add(glowingGreyBook);
        map.add(ornateGreyPendant1); map.add(ornateGreyPendant2); map.add(ornateGreyPendant3); map.add(ornateGreyPendant4);
        map.add(lustrousYellowFlask1); map.add(lustrousYellowFlask2); map.add(lustrousYellowFlask3); map.add(lustrousYellowFlask4);
        map.add(ornatePendant1); map.add(ornatePendant2);
        map.add(lustrousMap1); map.add(lustrousMap2); map.add(lustrousMap3); map.add(lustrousMap4);
        map.add(wornCrown1); map.add(wornCrown2); map.add(wornCrown3); map.add(wornCrown4);
        map.add(antiqueRedCase1); map.add(antiqueRedCase2); map.add(antiqueRedCase3); map.add(antiqueRedCase4); map.add(antiqueRedCase5);
        map.add(charmedGem);

        // Obstacles
        Obstacle o1 = new Obstacle(6,8); o1.addRequiredItem(superTurquoiseMap); o1.addRequiredItem(ornatePendant1); o1.addRequiredItem(lustrousMap1); o1.addRequiredItem(charmedGem);
        Obstacle o2 = new Obstacle(7,2); o2.addRequiredItem(superTurquoiseMap); o2.addRequiredItem(ornatePendant1); o2.addRequiredItem(lustrousMap1); o2.addRequiredItem(charmedGem);
        Obstacle o3 = new Obstacle(0,3); o3.addRequiredItem(superTurquoiseMap); o3.addRequiredItem(ornatePendant1); o3.addRequiredItem(lustrousMap1); o3.addRequiredItem(charmedGem);
        Obstacle o4 = new Obstacle(11,12); o4.addRequiredItem(superTurquoiseMap); o4.addRequiredItem(ornatePendant1); o4.addRequiredItem(lustrousMap1); o4.addRequiredItem(charmedGem);
        Obstacle o5 = new Obstacle(11,0); o5.addRequiredItem(superTurquoiseMap); o5.addRequiredItem(ornatePendant1); o5.addRequiredItem(lustrousMap1); o5.addRequiredItem(charmedGem);
        Obstacle o6 = new Obstacle(10,9); o6.addRequiredItem(superTurquoiseMap); o6.addRequiredItem(ornatePendant1); o6.addRequiredItem(lustrousMap1); o6.addRequiredItem(charmedGem);
        Obstacle o7 = new Obstacle(0,5); o7.addRequiredItem(superTurquoiseMap); o7.addRequiredItem(ornatePendant1); o7.addRequiredItem(lustrousMap1); o7.addRequiredItem(charmedGem);
        Obstacle o8 = new Obstacle(9,3); o8.addRequiredItem(superTurquoiseMap); o8.addRequiredItem(ornatePendant1); o8.addRequiredItem(lustrousMap1); o8.addRequiredItem(charmedGem);
        Obstacle o9 = new Obstacle(0,8); o9.addRequiredItem(superTurquoiseMap); o9.addRequiredItem(ornatePendant1); o9.addRequiredItem(lustrousMap1); o9.addRequiredItem(charmedGem);

        map.add(o1); map.add(o2); map.add(o3); map.add(o4); map.add(o5); map.add(o6); map.add(o7); map.add(o8); map.add(o9);

        // Add walls around the grid
        for (int x = -1; x <= gridW; x++)
        {
            map.add(new Obstacle(x, -1)); // top
            map.add(new Obstacle(x, gridH)); // bottom
        }
        for (int y = 0; y < gridH; y++)
        {
            map.add(new Obstacle(-1, y)); // left
            map.add(new Obstacle(gridW, y)); // right
        }

        logger.info("Test Map generated successfully!");
    }





}
