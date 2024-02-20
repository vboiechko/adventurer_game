package openworld.characters;
import openworld.World;

public class NPCMove implements Runnable{
    public World world;
    public static boolean move = true;
    
    public NPCMove(World world){
        this.world = world;
    }
    @Override
    public void run(){
        move = true;
        while (move) {
        synchronized(this){
            world.nonPlayerCharactersMove();
            world.monsterMove();
        }
        try {
            Thread.sleep(1000);
        } 
        catch(InterruptedException a){
        } 
        
        if (Thread.interrupted()){
            return;
        }
    }
}

}