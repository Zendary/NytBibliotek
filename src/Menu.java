import DB.Facade;
import DB.Udlån;
import MyUtil.Input;

public class Menu {
    public static void menu() {
        String svar;

        svar = Input.getString("\n\nHvad ønsker du? \n" +
                "Opret Låner (1) / Udskriv Låner (2) / Slet Låner (3) \n" +
                "Opret By (4) / Udskriv Byer (5)\n" +
                "Opret Bog (6) / Udskriv bøger (7) / Slet bog (8) \n" +
                "Udlån bog (9) / Aflever bog (10) / Udskriv udlåns ID (11)\n" +
                "Udlånt ud fra by (12) / Udlånt ud fra lånernavn (13)");

        switch (svar) {
            case "1":
                Facade.opretLåner();
                break;
            case "2":
                Facade.udskrivLåner();
                break;
            case "3":
                Facade.sletLåner(); //kan ikke slette hvis der er udlånt bog til dem
                break;
            case "4":
                Facade.opretBy();
                break;
            case "5":
                Facade.udskrivBy();
                break;
            case "6":
                Facade.opretBog();
                break;
            case "7":
                Facade.udskrivBøger();
                break;
            case "8":
                Facade.sletBog(); //kan ikke slette bog der er udlånt
                break;
            case "9":
                Facade.udlånBog();
                break;
            case "10":
                Facade.afleverBog();
                break;
            case "11":
                Facade.udskrivUdlånsID();
                break;
            case "12":
                Facade.byOversigt();
                break;
            case "13":
                Facade.navnOversigt();
                break;

            default:
                System.out.println("ugyldigt valg, prøv igen");
                break;
        }
        menu();
    }
}
