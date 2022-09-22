package DB;

public class Facade {

    public static void opretBog() {
        BogMapper.opretBog();
    }

    public static void sletBog() {
        BogMapper.sletBog();
    }

    public static void udskrivBøger() {
        BogMapper.udskrivBøger();
    }

    public static void opretBy() {
        ByMapper.opretBy();
    }

    public static void udskrivBy() {
        ByMapper.udskrivByer();
    }

    public static void opretLåner() {
        LånerMapper.opretLåner();
    }

    public static void udskrivLåner() {
        LånerMapper.udskrivLånere();
    }

    public static void sletLåner() {
        LånerMapper.sletLåner();
    }

    public static void byOversigt() {
        Udlån.byOversigt();
    }

    public static void navnOversigt() {
        Udlån.navnOversigt();
    }

    public static void udlånBog() {
        Udlån.udlånBog();
    }

    public static void afleverBog(){
        Udlån.afleverBog();
    }

    public static void udskrivUdlånsID(){
        Udlån.udskrivUdlånsID();
    }
}
