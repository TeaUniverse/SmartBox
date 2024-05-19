package mvc;

import java.util.ArrayList;

public class Publisher {
    private ArrayList<Subscriber> subscriberlist = new ArrayList<>();
    public void subscribe(Subscriber sub){
        subscriberlist.add(sub);
    }
    public void unsubscribe(Subscriber sub){
        subscriberlist.remove(sub);
    }
    public void notifyObservers(){
        for(Subscriber s : subscriberlist){
            s.update();
        }
    }
}
