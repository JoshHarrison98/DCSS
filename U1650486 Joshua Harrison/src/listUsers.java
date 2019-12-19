import net.jini.core.entry.Entry;

public class listUsers implements Entry {
    // Variables

    public String username;
    public String password;


    // No arg contructor
    public listUsers() {
    }

    // Arg constructor
    public listUsers(String user, String pass) {

        username = user;
        password = pass;
    }
}
