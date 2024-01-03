package ics3u.summative;

/**
 *
 * @author pc
 */
public class ICS3USummative {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	Attack attack1 = new AttackDirect();
    	Attack attack2 = new AttackAll();
        System.out.println("yo what the hell are you doing man");
        
        if (attack1 instanceof Attack) {
        	attack1.attack();
        }
    }
    
}
