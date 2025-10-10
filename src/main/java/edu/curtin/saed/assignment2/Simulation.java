package edu.curtin.saed.assignment2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class Simulation
{
    private static final Logger logger = Logger.getLogger(Simulation.class.getName());

    private final boolean CHEAT = true;
    private final int offset = 1;

    private int gridH;
    private int gridW;

    private MapObject[][] map;
    private Player player;

    private List<String> messages;
    private int[] goalPos;

    public Simulation(int gridH, int gridW)
    {
        this.gridH = gridH;
        this.gridW = gridW;

        map = new MapObject[gridH + 2][gridW + 2];
        initializeMapWithPaths();

        this.player = new Player(0, 0);
        this.messages = new LinkedList<>();
        goalPos = new int[2];
        goalPos[0] = 0;
        goalPos[1] = 0;
    }

    public void addObstacles(List<Obstacle> obstacleList)
    {
        for (Obstacle o : obstacleList)
        {
            int x = o.getXPosition() + offset;
            int y = o.getYPosition() + offset;

            if (y >= 0 && y < gridH + 2 && x >= 0 && x < gridW + 2)
            {
                map[y][x] = o;
            }
        }
        logger.info("Obstacles added successfully.");
    }

    public void addCollectables(List<Collectable> collectableList)
    {
        for (Collectable c : collectableList)
        {
            int x = c.getXPosition() + offset;
            int y = c.getYPosition() + offset;

            if (y >= 0 && y < gridH + 2 && x >= 0 && x < gridW + 2)
            {
                map[y][x] = c;
            }
        }
        logger.info("Collectables added successfully.");
    }

    public void addWalls()
    {
        for (int x = -1; x <= gridW; x++)
        {
            map[0][x + offset] = new Obstacle(x, -1,"#", true, true);      // top
            map[gridH + 1][x + offset] = new Obstacle(x, gridH,"#", true, true); // bottom
        }

        for (int y = 0; y < gridH; y++)
        {
            map[y + offset][0] = new Obstacle(-1, y,"#", true, true);      // left
            map[y + offset][gridW + 1] = new Obstacle(gridW, y,"#", true, true); // right
        }

        // Adding Player
        int x = player.getXPosition() + offset;
        int y = player.getYPosition() + offset;
        map[y][x] = player;
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
                        if(CHEAT)
                        {
                            sb.append(obj.getMapIcon());
                        }
                        else
                        {
                            sb.append("~");
                        }
                    }
                }
                sb.append(" ");
            }
            sb.append("\n");
        }

        logger.info("Drawable map generated successfully.");
        return sb.toString();
    }

    public void addMsgToShow(String msg)
    {
        messages.add(msg);
        logger.info("Message added successfully.");
    }

    public String getMessagesToShow()
    {
        StringBuilder sb = new StringBuilder();

        for (String message : messages) {
            sb.append(message);
            sb.append("\n"); // Add newline after each message
        }
        messages.clear();

        logger.info("Messages to show generated successfully.");
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
            if(target.performAction(player, this))
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

































    // TODO: Remove/Update Later
    public void testMapGenerator()
    {
        int offset = 1; // for border walls

        // --- Collectables ---
        List<Collectable> collectables = new ArrayList<>();
        collectables.add(new Collectable("superlative turquoise map", 3, 0, "A map glowing with turquoise brilliance", true, "I"));
        collectables.add(new Collectable("glowing grey book", 11, 1, "An ancient book emitting a faint grey glow", true, "I"));
        collectables.add(new Collectable("ornate grey pendant", 8, 12, "A finely crafted grey pendant with intricate designs", true, "I"));
        collectables.add(new Collectable("ornate grey pendant", 7, 11, "A finely crafted grey pendant with intricate designs", true, "I"));
        collectables.add(new Collectable("ornate grey pendant", 7, 1, "A finely crafted grey pendant with intricate designs", true, "I"));
        collectables.add(new Collectable("ornate grey pendant", 1, 1, "A finely crafted grey pendant with intricate designs", true, "I"));
        collectables.add(new Collectable("lustrous yellow flask", 3, 5, "A small flask shining with yellow luster", true, "I"));
        collectables.add(new Collectable("lustrous yellow flask", 7, 10, "A small flask shining with yellow luster", true, "I"));
        collectables.add(new Collectable("lustrous yellow flask", 1, 5, "A small flask shining with yellow luster", true, "I"));
        collectables.add(new Collectable("lustrous yellow flask", 5, 12, "A small flask shining with yellow luster", true, "I"));
        collectables.add(new Collectable("ornate pendant", 8, 0, "A beautiful ornate pendant of unknown origin", true, "I"));
        collectables.add(new Collectable("ornate pendant", 11, 3, "A beautiful ornate pendant of unknown origin", true, "I"));
        collectables.add(new Collectable("lustrous map", 7, 9, "A map with a mysterious lustrous sheen", true, "I"));
        collectables.add(new Collectable("lustrous map", 1, 12, "A map with a mysterious lustrous sheen", true, "I"));
        collectables.add(new Collectable("lustrous map", 11, 7, "A map with a mysterious lustrous sheen", true, "I"));
        collectables.add(new Collectable("lustrous map", 9, 7, "A map with a mysterious lustrous sheen", true, "I"));
        collectables.add(new Collectable("worn crown", 2, 2, "An old crown showing signs of wear", true, "I"));
        collectables.add(new Collectable("worn crown", 4, 4, "An old crown showing signs of wear", true, "I"));
        collectables.add(new Collectable("worn crown", 4, 9, "An old crown showing signs of wear", true, "I"));
        collectables.add(new Collectable("worn crown", 4, 0, "An old crown showing signs of wear", true, "I"));
        collectables.add(new Collectable("antique red case", 9, 2, "A red case with antique charm", true, "I"));
        collectables.add(new Collectable("antique red case", 8, 3, "A red case with antique charm", true, "I"));
        collectables.add(new Collectable("antique red case", 0, 2, "A red case with antique charm", true, "I"));
        collectables.add(new Collectable("antique red case", 5, 10, "A red case with antique charm", true, "I"));
        collectables.add(new Collectable("antique red case", 9, 0, "A red case with antique charm", true, "I"));
        collectables.add(new Collectable("charmed gem", 2, 9, "A small gem radiating magical charm", true, "I"));

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
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(6,8,"#", false, false));
        obstacles.add(new Obstacle(7,2,"#", false, false));
        obstacles.add(new Obstacle(0,3,"#", false, false));
        obstacles.add(new Obstacle(11,12,"#", false, false));
        obstacles.add(new Obstacle(11,0,"#", false, false));
        obstacles.add(new Obstacle(10,9,"#", false, false));
        obstacles.add(new Obstacle(0,5,"#", false, false));
        obstacles.add(new Obstacle(9,3,"#", false, false));
        obstacles.add(new Obstacle(0,8,"#", false, false));

        // Add requirements
        Collectable superTurquoiseMap = collectables.get(0);
        Collectable ornatePendant1 = collectables.get(10);
        Collectable lustrousMap1 = collectables.get(12);
        Collectable charmedGem = collectables.get(25);

        for (Obstacle o : obstacles)
        {
            o.addRequiredItem(superTurquoiseMap.getName());
            o.addRequiredItem(ornatePendant1.getName());
            o.addRequiredItem(lustrousMap1.getName());
            o.addRequiredItem(charmedGem.getName());

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
            map[0][x + offset] = new Obstacle(x, -1,"#", true, true);      // top
            map[gridH + 1][x + offset] = new Obstacle(x, gridH,"#", true, true); // bottom
        }

        for (int y = 0; y < gridH; y++)
        {
            map[y + offset][0] = new Obstacle(-1, y,"#", true, true);      // left
            map[y + offset][gridW + 1] = new Obstacle(gridW, y,"#", true, true); // right
        }

        // Adding Player
        int x = player.getXPosition() + offset;
        int y = player.getYPosition() + offset;
        map[y][x] = player;

        logger.info("Test Map generated successfully!");
    }






}
