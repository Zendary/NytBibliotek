package DB;

import MyUtil.Input;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LånerMapper {
    protected static void sletLåner() {
        udskrivLånere();

        String sql = "delete from lånertabel where lånerid = ?";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            String lånerNavn = Input.getString("Skriv ID på låner der skal slettes");
            ps.setString(1, lånerNavn );


            int res = ps.executeUpdate();       //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            if (res > 0) {

                System.out.println("Kunden med navnet " + "\"" + lånerNavn + "\"" + " er nu blevet slettet");
            }else{
                System.out.println("en kunde med navnet " + "\"" + lånerNavn + "\"" + " fandtes ikke i listen");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        udskrivLånere();
    }

    protected static void udskrivLånere() {
        List<String> lånerListe = new ArrayList<>();

        String sql = "select * from lånertabel ";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement


            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                int id = resultSet.getInt("lånerid");
                String navn = resultSet.getString("Navn");
                String adresse = resultSet.getString("Adresse");
                String postNr = resultSet.getString("PostNr");

                lånerListe.add(id + " : " + navn + " : " + adresse + " : " + postNr);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : lånerListe) {
            System.out.println(s);
        }
    }

    protected static void opretLåner() {
        String sql = "INSERT INTO lånertabel (Navn, Adresse, PostNr ) VALUES (?, ?, ?)";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = ConnectionConfig.getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setString(1, Input.getString("Skriv et navn"));
            ps.setString(2, Input.getString("Skriv en adresse"));
            ps.setString(3, Input.getString("Skriv et postNr"));


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
