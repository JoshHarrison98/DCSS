import net.jini.core.entry.Entry;

public class checkUser implements Entry {
    // Variables

    public String username;
    public String password;


    // No arg contructor
    public checkUser() {
    }

    // Arg constructor
    public checkUser(String user, String pass) {

        username = user;
        password = pass;
    }
}
