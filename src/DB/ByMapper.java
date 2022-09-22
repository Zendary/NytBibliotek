package DB;

import MyUtil.Input;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ByMapper {
   protected static void udskrivByer() {
        List<String> byListe = new ArrayList<>();

        String sql = "select * from postnrtabel ";

        try (Connection con = ConnectionConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {           // https://en.wikipedia.org/wiki/Prepared_statement


            ResultSet resultSet = ps.executeQuery();   //https://javaconceptoftheday.com/difference-between-executequery-executeupdate-execute-in-jdbc/

            while (resultSet.next()) {
                int id = resultSet.getInt("Postnrid");
                String PostNr = resultSet.getString("PostNr");
                String ByNavn = resultSet.getString("ByNavn");

                byListe.add(id + " : " + PostNr + " : " + ByNavn);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        for (String s : byListe) {
            System.out.println(s);
        }
    }

    protected static void opretBy() {
        String sql = "INSERT INTO postnrtabel (PostNr, ByNavn) VALUES (?, ?)";


        // se lige try-with-resources f.eks. her  https://www.baeldung.com/java-try-with-resources
        try (Connection con = ConnectionConfig.getConnection();  // får en connection

             // se evt. https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
        ) {
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // her klargøres mit prepared statement ved at min parametre indsættes.
            ps.setInt(1, Input.getInt("Skriv et postNr"));
            ps.setString(2, Input.getString("Skriv en by"));


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
