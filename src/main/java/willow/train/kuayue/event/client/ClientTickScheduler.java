package willow.train.kuayue.event.client;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import java.util.ArrayList;

public class ClientTickScheduler {
    public static ArrayList<Runnable> runnableList = new ArrayList<>();

    public static void onClientEarlyTick(ClientTickEvent.Pre tick){
        for (Runnable runnable : runnableList) {
            try{
                runnable.run();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        runnableList.clear();
    }

    public static void onNextTick(Runnable runnable) {
        runnableList.add(runnable);
    }
}
