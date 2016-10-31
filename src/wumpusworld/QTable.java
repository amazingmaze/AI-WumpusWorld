package wumpusworld;

/**
 *
 * @author matte
 */
public class QTable {
    
    
    private static double[][] Q = null;
    
    public static double[][] getInstance(){
        
        if(Q == null){
            Q = new double[45][5];
            
            for(int x = 1; x < 5; x++){
                
                for(int y = 1;y < 5; y++){
                    
                    for(int r = 0; r < 5; r++){
                        
                        int pos = x * 10 + y;
                        
                        Q[pos][r] = 0;
                        
                    }
                    
                }
                
            }
            
        }
        
        return Q;
            
        
    }
    
    public static int getMaxQValueAction(int pos){
        
        int bestAction = 1;
        for(int i = 1; i < 5; i++){
            if(Q[pos][bestAction] < Q[pos][i]){
                bestAction = i;
            }
        }
        return bestAction;
    }
    
    public static double getMaxQValue(int pos){
        
        double bestValue = Q[pos][1];
        for(int i = 1; i < 5; i++){
            if(bestValue < Q[pos][i]){
                bestValue = Q[pos][i];
            }
        }
        return bestValue;
    }
    
    public static void printQTable(){
        for(int x = 1; x < 5; x++){ 
                for(int y = 1;y < 5; y++){   
                    
                    System.out.println(x * 10 + y +  " \nAction: ");
                    for(int r = 0; r < 5; r++){                     
                        int pos = x * 10 + y;
                        
                        if(r == 0){
                            System.out.println("Reward: " + Q[pos][r]);
                        }
                        System.out.println(Q[pos][r]);                      
                    }                  
                }               
        }
    }
    
}
