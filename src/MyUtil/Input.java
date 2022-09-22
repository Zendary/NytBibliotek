package MyUtil;

import java.util.Scanner;

public class Input {
    public static String getString(String s){
        System.out.println(s + " : ");
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine();
    }

    public static int getInt(String s){

        while (true) {
                    try {
                        int ans = Integer.parseInt(getString(s));
                        return ans;
                    } catch (NumberFormatException e) {
                        System.out.println("husk ikke et tal ord");
                    }
                }
        }
}
