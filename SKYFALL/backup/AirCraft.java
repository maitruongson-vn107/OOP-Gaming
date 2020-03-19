import javafx.scene.Parent;

public class AirCraft extends Parent {
    public int model;
    public boolean vertical;
    public int HP;

    public AirCraft(int model_type, boolean vertical){
        this.model = model_type;
        this.vertical = vertical;
        this.HP = model_type*100;
    }

    public void shoot_neighbor(){
        HP = HP - 50;
    }

    public void shoot_center(){
        HP = HP - 100;
    }

    public boolean isAlive(){
        if (HP > 0) return true;
        return false;
    }
}
