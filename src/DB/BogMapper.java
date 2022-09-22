package DB;

import MyUtil.Input;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BogMapper {
    protected static void udskrivBøger() {
        List<String> bogListe = new ArrayList<>();

        String sql = "select * from bogtabel ";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement


            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                int id = resultSet.getInt("bogid");
                String navn = resultSet.getString("Forfatter");
                String adresse = resultSet.getString("Title");
                String postNr = resultSet.getString("UdgivelsesÅr");
                String antal = resultSet.getString("Antal");

                bogListe.add(id + " : " + navn + " : " + adresse + " : " + postNr + " : " + antal);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : bogListe) {
            System.out.println(s);
        }
    }

    protected static void sletBog() {
        udskrivBøger();

        String sql = "delete from bogtabel where Bogid = ?";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            String bogTitle = Input.getString("Skriv ID på den bog der skal slettes");
            ps.setString(1, bogTitle );


            int res = ps.executeUpdate();       //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            if (res > 0) {

                System.out.println("Kunden med navnet " + "\"" + bogTitle + "\"" + " er nu blevet slettet");
            }else{
                System.out.println("en kunde med navnet " + "\"" + bogTitle + "\"" + " fandtes ikke i listen");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        udskrivBøger();
    }

    protected static void opretBog() {
        String sql = "INSERT INTO bogtabel (Forfatter, Title, UdgivelsesÅr, Antal ) VALUES (?, ?, ?, ?)";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = ConnectionConfig.getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setString(1, Input.getString("Skriv forfatterens navn"));
            ps.setString(2, Input.getString("Skriv bogens title"));
            ps.setInt(3, Input.getInt("Skriv bogens udgivelsesår"));
            ps.setInt(4, Input.getInt("Skriv antal bøger der indehaves"));


            ps.executeUpdate();    //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            ResultSet ids = ps.getGeneratedKeys();
            ids.next();
            int id = ids.getInt(1);
            System.out.println("Vi er nået til række : " +id);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
