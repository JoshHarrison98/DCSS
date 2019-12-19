import net.jini.core.entry.*;

public class UserQueStatus implements Entry{
    // Variables
    public Integer nextUser;

    // No arg contructor
    public UserQueStatus(){
    }

    public UserQueStatus(int n){
        // set count to n
        nextUser = n;
    }

    public void addUser(){
        nextUser++;
    }
}
