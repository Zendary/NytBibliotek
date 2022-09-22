package DB;

import MyUtil.Input;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Udlån {
    protected static void udlånBog() {
        String sql = "INSERT INTO udlåntabel (Bogid, Lånerid) VALUES (?, ?)";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = ConnectionConfig.getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            int bogid = Input.getInt("Skriv Bog ID");
            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setInt(1, bogid);
            if (tjekLager(bogid) > 0) {
                ps.setInt(2, Input.getInt("Skriv Låner ID"));


                ps.executeUpdate();    //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

                ResultSet ids = ps.getGeneratedKeys();
                ids.next();
                int id = ids.getInt(1);
                System.out.println("Vi er nået til række : " + id);
            } else {
                System.out.println("Bogen er ikke på lager");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected static void udskrivUdlånsID(){

        List<String> bogListe = new ArrayList<>();

        String sql = "select * from udlånsoversigt ";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement


            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                String udlånsID = resultSet.getString("Udlånsid");
                String navn = resultSet.getString("Navn");
                String title = resultSet.getString("Title");

                bogListe.add("Udlåns ID " + udlånsID + " : " + navn + " : " + title);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : bogListe) {
            System.out.println(s);
        }
    }

    protected static void afleverBog() {
        String sql = "DELETE FROM udlåntabel WHERE Udlånsid = ?";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = ConnectionConfig.getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            udskrivUdlånsID();

            int udlånsid = Input.getInt("Skriv Udlåns ID");
            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setInt(1, udlånsid);
            returnerTilLager(1,udlånsid);

            ps.executeUpdate();    //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

        } catch (Exception e) {
            e.printStackTrace();
        }
        udskrivUdlånsID();
    }

    protected static void byOversigt() {
        List<String> byListe = new ArrayList<>();

        String sql = "select * from udlånsOversigt where ByNavn = ?";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement

            ps.setString(1, Input.getString("Skriv et bynavn"));

            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                String Navn = resultSet.getString("Navn");
                String ByNavn = resultSet.getString("ByNavn");
                String Title = resultSet.getString("Title");


                byListe.add(Navn + " : " + ByNavn + " : " + Title);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : byListe) {
            System.out.println(s);
        }
    }

    protected static void navnOversigt() {
        List<String> byListe = new ArrayList<>();

        String sql = "select * from udlånsOversigt where Navn = ?";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement

            ps.setString(1, Input.getString("Skriv et udlåner navn"));

            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                String Navn = resultSet.getString("Navn");
                String ByNavn = resultSet.getString("ByNavn");
                String Title = resultSet.getString("Title");


                byListe.add(Navn + " : " + ByNavn + " : " + Title);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : byListe) {
            System.out.println(s);
        }
    }

    protected static void returnerTilLager(int antal, int udlånsid) {
        String sql = "UPDATE udlåntabel join bogtabel b on b.Bogid = udlåntabel.Bogid SET Antal = Antal+? WHERE Udlånsid = ?";

        try (Connection con = ConnectionConfig.getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ) {

            // her klargøres mit prepared statement ved at min parametre indsættes.

            ps.setInt(1, antal);
            ps.setInt(2, udlånsid);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static int tjekLager(int bogid) {
        String sql = "SELECT Antal From bogtabel WHERE bogid = ?";
        int Lager = 0;

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement

            ps.setInt(1, bogid);

            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                Lager = resultSet.getInt("Antal");

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return Lager;
    }
}
