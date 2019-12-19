import net.jini.core.entry.*;

public class UserStatus implements Entry{
    // Variables
    public Integer nextUser;

    // No arg contructor
    public UserStatus(){
    }

    public UserStatus(int n){
        // set count to n
        nextUser= n;
    }

    public void addUser(){
        nextUser++;
    }
}
