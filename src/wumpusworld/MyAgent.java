package wumpusworld;

import java.util.Random;

/**
 * Contains starting code for creating your own Wumpus World agent. Currently
 * the agent only make a random decision each turn.
 *
 * @author Johan Hagelbäck
 */
public class MyAgent implements Agent {

    private World w;
    int rnd;

    private double[][] Q = QTable.getInstance();

    private int currentPos;

    /**
     * Creates a new instance of your solver agent.
     *
     * @param world Current world state
     */
    public MyAgent(World world) {
        w = world;

        currentPos = 11;

    }

    @Override
    public void train() {
        
        // Keep old position
        int oldPos = currentPos;

        // Select an action 
        int action = selectAction();
        
        // Execute the selected action
        executeAction(action);

        

        // Take the action, and observe the reward, r, as well as the new state, s'.
        int newPos = currentPos;
        double reward = Q[oldPos][0];

        double learningRate = 0.5;
        double discountFactor = 0.9;
        double maxQValueOfNextState = QTable.getMaxQValue(newPos);
        // Update the Q-value for the state using the observed reward and the maximum reward possible for the next state. 
        Q[oldPos][action] = Q[oldPos][action] + learningRate * (reward + (discountFactor * maxQValueOfNextState) - Q[oldPos][action]);
        
        
        // Debug
        int ox = w.getPlayerX();
        int oy = w.getPlayerY();
        
    }

    public int selectAction() {
        
        Random rand = new Random();
        if (Math.random() < 0.5) {
            // Select a random action
            return rand.nextInt(4) + 1;
        } else {
            return QTable.getMaxQValueAction(currentPos);
        }

    }

    public void executeAction(int action) {
        
        // Check for pits, gold, wumpus
        checkState();
        
        
        //Location of the player
        int x = w.getPlayerX();
        int y = w.getPlayerY();
               

        // Right
        if (action == 1) {

            if (w.isValidPosition(x + 1, y)) {
                currentPos = (x + 1) * 10 + y;
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
            } else {
            }

        }
        // South
        if (action == 2) {

            if (w.isValidPosition(x, y - 1)) {
                
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPos = x * 10 + (y - 1);
            } else {
            }

        }
        // Left
        if (action == 3) {

            if (w.isValidPosition(x - 1, y)) {
                
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPos = (x - 1) * 10 + y;
            } else {
            }

        }
        // North
        if (action == 4) {
            if (w.isValidPosition(x, y + 1)) {
                
                switch (w.getDirection()) {
                    case World.DIR_RIGHT:
                        w.doAction(World.A_TURN_LEFT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_DOWN:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_LEFT:
                        w.doAction(World.A_TURN_RIGHT);
                        w.doAction(World.A_MOVE);
                        break;
                    case World.DIR_UP:
                        w.doAction(World.A_MOVE);
                        break;
                    default:
                        break;
                }
                currentPos = x * 10 + (y + 1);
            } else {
            }             
        }

    }

    public void checkState() {
        int x = w.getPlayerX();
        int y = w.getPlayerY();
        if (w.gameOver()) {
            if (w.hasWumpus(x, y)) {
                Q[currentPos][0] = -1.0;
            }
        } else if (w.hasGlitter(x, y)) {
            Q[currentPos][0] = 1.0;
            w.doAction(World.A_GRAB);
        } else if (w.hasPit(x, y)) {
            Q[currentPos][0] = -1.0;
            w.doAction(World.A_CLIMB);
        }
    }

    /**
     * Asks your solver agent to execute an action.
     */
    @Override
    public void doAction() {

        int action = QTable.getMaxQValueAction(currentPos);
        
        System.out.println("Q-learning said that Q[" + currentPos + "] had the best value of: " + Q[currentPos][action]);
        System.out.println("Executing action: " + action);
        executeAction(action);

        int x = w.getPlayerX();
        int y = w.getPlayerY();

        if (w.hasWumpus(x, y)) {
            System.out.println("Wumpus is here");
        } 
        else if (w.hasGlitter(x, y)) {
            System.out.println("Win win win!");
            w.doAction(World.A_GRAB);
        } else if (w.hasPit(x, y)) {
            System.out.println("Shouldn't be here...");
            
        }

    }

    
    public void doAction2() {

        //Location of the player
        int cX = w.getPlayerX();
        int cY = w.getPlayerY();

        System.out.println("X-LOCATION: " + cX);
        System.out.println("Y-LOCATION: " + cY);

        //Basic action:
        //Grab Gold if we can.
        if (w.hasGlitter(cX, cY)) {
            w.doAction(World.A_GRAB);
            return;
        }

        //Basic action:
        //We are in a pit. Climb up.
        if (w.isInPit()) {
            w.doAction(World.A_CLIMB);
            return;
        }

        //Test the environment
        if (w.hasBreeze(cX, cY)) {
            System.out.println("I am in a Breeze");
        }
        if (w.hasStench(cX, cY)) {
            System.out.println("I am in a Stench");
        }
        if (w.hasPit(cX, cY)) {
            System.out.println("I am in a Pit");
        }
        if (w.getDirection() == World.DIR_RIGHT) {
            System.out.println("I am facing Right");
        }
        if (w.getDirection() == World.DIR_LEFT) {
            System.out.println("I am facing Left");
        }
        if (w.getDirection() == World.DIR_UP) {
            System.out.println("I am facing Up");
        }
        if (w.getDirection() == World.DIR_DOWN) {
            System.out.println("I am facing Down");
        }

        //decide next move
        rnd = decideRandomMove();
        if (rnd == 0) {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        }

        if (rnd == 1) {
            w.doAction(World.A_MOVE);
        }

        if (rnd == 2) {
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_TURN_LEFT);
            w.doAction(World.A_MOVE);
        }

        if (rnd == 3) {
            w.doAction(World.A_TURN_RIGHT);
            w.doAction(World.A_MOVE);
        }

    }
     
    /**
     * Genertes a random instruction for the Agent.
     */
    public int decideRandomMove() {
        return (int) (Math.random() * 4);
    }

}
