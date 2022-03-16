import java.sql.*;

public class JbdcDemoZusatz {
    public static void main(String[] args) {
        selectAllDemo();
        insertAdresseDemo("Wien", 1050);
        selectAllDemo();
        updateAdresseDemo(1, "Rum", 5010);
        selectAllDemo();
        findAllByPlzLike(6020);
        deleteAdresseDemo(3);
        selectAllDemo();
    }


    public static void findAllByPlzLike(int pattern) {
        System.out.println();
        System.out.println("FindAllByNameLike Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password)) {
            System.out.println("Verbindung zur Datenbank hergestellt");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "Select * FROM `adresse` WHERE `adresse`.`plz` LIKE  ?");
            prepareStatement.setString(1, "%" + pattern + "%");
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ort = rs.getString("ort");
                int plz = rs.getInt("plz");
                System.out.println("Student aus der DB: [ID] " + id + ", [PLZ] " + plz + ", [ORT] " + ort);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }


    public static void deleteAdresseDemo(int studentId) {
        System.out.println();
        System.out.println("Delete Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String pwd = "";
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, pwd)) {
            System.out.println("Verbindung zur Datenbank hergestellt");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "DELETE FROM `adresse` WHERE `adresse`.`id` = ?");
            try {
                prepareStatement.setInt(1, studentId);
                int rowAffected = prepareStatement.executeUpdate();
                System.out.println("Anzahl der gelöschten Datensätze: " + rowAffected);
            } catch (SQLException ex) {
                System.out.println("Fehler im SQL-Delete Statement : " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void updateAdresseDemo(int adresseId, String neuerOrtsName, int neuePLZ) {
        System.out.println();
        System.out.println("Update Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String pwd = "";
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, pwd)) {
            System.out.println("Verbindung zur Datenbank hergestellt");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "UPDATE `adresse` SET `ort` = ?, `plz` = ? WHERE `adresse`.`id` = ?");
            try {
                prepareStatement.setString(1, neuerOrtsName);
                prepareStatement.setInt(2, neuePLZ);
                prepareStatement.setInt(3, adresseId);
                int rowAffected = prepareStatement.executeUpdate();
                System.out.println("Anzahl der aktualisierten Datensätze: " + rowAffected);
            } catch (SQLException ex) {
                System.out.println("Fehler im SQL-Update Statement : " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void insertAdresseDemo(String ort, int PLZ) {
        System.out.println();
        System.out.println("Insert Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String pwd = "";
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, pwd)) {
            System.out.println("Verbindung zur Datenbank hergestellt");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "INSERT INTO `adresse` (`id`, `ort`, `plz`) VALUES (NULL, ?, ?)");
            try {
                prepareStatement.setString(1, ort);
                prepareStatement.setInt(2, PLZ);
                int rowAffected = prepareStatement.executeUpdate();
                System.out.println(rowAffected + " Datensätze eingefügt");
            } catch (SQLException ex) {
                System.out.println("Fehler im SQL-Insert Statement : " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }

    public static void selectAllDemo() {
        System.out.println();
        System.out.println("Select Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String pwd = "";

        //try catch weil immer eine SQLException auftreten kann!
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, pwd)) //DB-connect auf Basis der url+user+pwd
        {

            System.out.println("Verbindung zur Datenbank hergestellt");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "SELECT * FROM `adresse`");
            ResultSet rs = prepareStatement.executeQuery();  //DB-Abfrage, das Ergebnis wird in rs(resultset) gespeichert

            //solange es einen Datensatz zum auslesen gibt ("Zeigermethode") wird die Schleife ausgeführt - bis rs.next false liefert
            while (rs.next()) {
                int id = rs.getInt("id");
                int plz = rs.getInt("plz");
                String ort = rs.getString("ort");
                System.out.println("Student aus der DB: [ID] " + id + ", [PLZ] " + plz + ", [ORT] " + ort);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }
}
