package edu.curtin.saed.assignment2;

import java.util.logging.Logger;

public class Simulation
{
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());

    private int gridH;
    private int gridW;

    private MapObject[][] map;
    private Player player;
    private int[] goalPos;

    public Simulation(int gridH, int gridW)
    {
        this.gridH = gridH;
        this.gridW = gridW;

        map = new MapObject[gridH + 2][gridW + 2];
        initializeMapWithPaths();

        this.player = new Player(0, 0);
        goalPos = new int[2];
        goalPos[0] = 0;
        goalPos[1] = 0;
    }

    private void initializeMapWithPaths()
    {
        for (int y = 0; y < map.length; y++)
        {
            for (int x = 0; x < map[y].length; x++)
            {
                map[y][x] = new Path(false);
            }
        }

    }

    public String getDrawableMap()
    {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < gridH + 2; y++)
        {
            for (int x = 0; x < gridW + 2; x++)
            {
                MapObject obj = map[y][x];

                if (obj != null)
                {
                    if(obj.isVisible())
                    {
                        sb.append(obj.getMapIcon());
                    }
                    else
                    {
                        sb.append("~");
                    }
                }
                sb.append(" ");
            }
            sb.append("\n");
        }

        logger.info("Drawable map generated successfully.");
        return sb.toString();
    }

    public void movePlayer(int stepX, int stepY)
    {
        // Position where the player wants to move on to
        int checkingX = player.getXPosition() + 1 + stepX;
        int checkingY = player.getYPosition() + 1 + stepY;
        MapObject target = map[checkingY][checkingX];
        logger.info(String.format("Player Pos: at x:%d, y:%d", player.getXPosition() + 1, player.getYPosition() + 1));
        logger.info(String.format("Target Pos: at x:%d, y:%d", checkingX, checkingY));

        if(target == null)
        {
            map[player.getYPosition() + 1][player.getXPosition() + 1] = new Path(true);
            map[checkingY][checkingX] = player;

            player.move(stepX, stepY);
        }
        else
        {
            if(target.performAction(player))
            {
                map[player.getYPosition() + 1][player.getXPosition() + 1] = new Path(true);
                map[checkingY][checkingX] = player;

                player.move(stepX, stepY);
            }
        }
        showSurrounding();
    }

    private void showSurrounding()
    {
        int x = player.getXPosition() + 1;
        int y = player.getYPosition() + 1;

        // Define directions: up, down, left, right
        int[][] directions = {
                {0, -1},  // up
                {0, 1},   // down
                {-1, 0},  // left
                {1, 0}    // right
        };

        // Reveal tiles in all four directions
        for (int[] dir : directions)
        {
            int revealX = x + dir[0];
            int revealY = y + dir[1];

            // Boundary check
            if (revealX >= 0 && revealY >= 0 && revealX < gridW + 2 && revealY < gridH + 2)
            {
                if (map[revealY][revealX] != null)
                {
                    map[revealY][revealX].makeVisible();
                }
            }
        }
    }

































    // TODO: Remove Later
    public void testMapGenerator()
    {
        int offset = 1; // for border walls

        // --- Collectables ---
        Collectable[] collectables = {
                new Collectable("superlative turquoise map", 3, 0, true, "I"),
                new Collectable("glowing grey book", 11, 1, true, "I"),
                new Collectable("ornate grey pendant", 8, 12, true, "I"),
                new Collectable("ornate grey pendant", 7, 11, true, "I"),
                new Collectable("ornate grey pendant", 7, 1, true, "I"),
                new Collectable("ornate grey pendant", 1, 1, true, "I"),
                new Collectable("lustrous yellow flask", 3, 5, true, "I"),
                new Collectable("lustrous yellow flask", 7, 10, true, "I"),
                new Collectable("lustrous yellow flask", 1, 5, true, "I"),
                new Collectable("lustrous yellow flask", 5, 12, true, "I"),
                new Collectable("ornate pendant", 8, 0, true, "I"),
                new Collectable("ornate pendant", 11, 3, true, "I"),
                new Collectable("lustrous map", 7, 9, true, "I"),
                new Collectable("lustrous map", 1, 12, true, "I"),
                new Collectable("lustrous map", 11, 7, true, "I"),
                new Collectable("lustrous map", 9, 7, true, "I"),
                new Collectable("worn crown", 2, 2, true, "I"),
                new Collectable("worn crown", 4, 4, true, "I"),
                new Collectable("worn crown", 4, 9, true, "I"),
                new Collectable("worn crown", 4, 0, true, "I"),
                new Collectable("antique red case", 9, 2, true, "I"),
                new Collectable("antique red case", 8, 3, true, "I"),
                new Collectable("antique red case", 0, 2, true, "I"),
                new Collectable("antique red case", 5, 10, true, "I"),
                new Collectable("antique red case", 9, 0, true, "I"),
                new Collectable("charmed gem", 2, 9, true, "I")
        };

        for (Collectable c : collectables)
        {
            int x = c.getXPosition() + offset;
            int y = c.getYPosition() + offset;

            if (y >= 0 && y < gridH + 2 && x >= 0 && x < gridW + 2)
            {
                map[y][x] = c;
            }
        }

        // --- Obstacles ---
        Obstacle[] obstacles = {
                new Obstacle(6,8,"#", false), new Obstacle(7,2,"#", false), new Obstacle(0,3,"#", false),
                new Obstacle(11,12,"#", false), new Obstacle(11,0,"#", false), new Obstacle(10,9,"#", false),
                new Obstacle(0,5,"#", false), new Obstacle(9,3,"#", false), new Obstacle(0,8,"#", false)
        };

        // Add requirements
        Collectable superTurquoiseMap = collectables[0];
        Collectable ornatePendant1 = collectables[10];
        Collectable lustrousMap1 = collectables[12];
        Collectable charmedGem = collectables[25];

        for (Obstacle o : obstacles)
        {
            o.addRequiredItem(superTurquoiseMap);
            o.addRequiredItem(ornatePendant1);
            o.addRequiredItem(lustrousMap1);
            o.addRequiredItem(charmedGem);

            int x = o.getXPosition() + offset;
            int y = o.getYPosition() + offset;

            if (y >= 0 && y < gridH + 2 && x >= 0 && x < gridW + 2)
            {
                map[y][x] = o;
            }
        }

        // --- Border Walls ---
        for (int x = -1; x <= gridW; x++)
        {
            map[0][x + offset] = new Obstacle(x, -1,"#", true);      // top
            map[gridH + 1][x + offset] = new Obstacle(x, gridH,"#", true); // bottom
        }

        for (int y = 0; y < gridH; y++)
        {
            map[y + offset][0] = new Obstacle(-1, y,"#", true);      // left
            map[y + offset][gridW + 1] = new Obstacle(gridW, y,"#", true); // right
        }

        // Adding Player
        int x = player.getXPosition() + offset;
        int y = player.getYPosition() + offset;
        map[y][x] = player;

        logger.info("Test Map generated successfully!");
    }





}
