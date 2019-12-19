import net.jini.core.entry.*;

public class listUser implements Entry{
    // Variables
    public Integer lotNumber;
    public String lotName;
    public String lotDesc;
    public String lotBuyNow;

    // No arg contructor
    public listUser(){
    }

    // Arg constructor
    public listUser(int lot, String nm, String desc, String buyNow){
        lotNumber = lot;
        lotName = nm;
        lotDesc = desc;
        lotBuyNow = buyNow;
    }
}


