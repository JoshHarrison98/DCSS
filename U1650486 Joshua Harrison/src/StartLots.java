import net.jini.core.lease.Lease;
import net.jini.space.JavaSpace;

public class StartLots {
    private static final long ONESECOND = 1000;  // one thousand milliseconds

    public static void main(String[] args){

        JavaSpace space = SpaceUtils.getSpace();

        if (space == null){
            System.err.println("Failed to find the javaspace");
            System.exit(1);
        }

        LotStatus template = new LotStatus();
        try {
            LotStatus returnedObject = (LotStatus)space.readIfExists(template,null, ONESECOND);
            if (returnedObject == null) {
                // there is no object in the space, so create one
                try {
                    LotStatus qs = new LotStatus(0);
                    space.write(qs, null, Lease.FOREVER);
                    System.out.println("lotStatus object added to space");
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // there is already an object available, so don't create one
                System.out.println("lotStatus object is already in the space");
                System.exit(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        UserQueStatus template2 = new UserQueStatus();
        try {
            UserQueStatus returnedObject = (UserQueStatus)space.readIfExists(template2,null, ONESECOND);
            if (returnedObject == null) {
                // there is no object in the space, so create one
                try {
                    UserQueStatus qs = new UserQueStatus(0);
                    space.write(qs, null, Lease.FOREVER);
                    System.out.println("UserQueStatus object added to space");
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // there is already an object available, so don't create one
                System.out.println("UserQueStatus object is already in the space");
                System.exit(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
