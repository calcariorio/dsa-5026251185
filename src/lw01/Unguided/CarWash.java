package lw01.Unguided;

public class CarWash extends WashService {

    public CarWash(String id, int days){
        super(id, days);
    }
    
    @Override
    public int calculateCharge() {
        int days = getDays();
        int cost = 0;
        if (days <= 3){
            cost=days*35000;
        } else {
            cost = (3 * 35000) + ((days - 3)*25000);
        }
        return cost + 15000;
    }

    @Override
    public String label(){
        return "Car";
    }
}
