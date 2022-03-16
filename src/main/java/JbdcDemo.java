import java.sql.*;

public class JbdcDemo {

    public static void main(String[] args) {
        System.out.println("JDBC Demo");

        selectAllDemo();
        insertStudentDemo("Name des Studenten", "email@prov.at");
        selectAllDemo();
        updateStudentDemo("Neuer Name", "neueemail@provider.at", 4);
        selectAllDemo();
        deleteStudentDemo(12);
        selectAllDemo();
        findAllByNameLike("Maria");
    }


    private static void findAllByNameLike(String pattern) {

        System.out.println("FindAllByNameLike Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password)) {
            System.out.println("Verbindung zur Datenbank hergestellt");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "Select * FROM `student` WHERE `student`.`name` LIKE  ?");
            prepareStatement.setString(1, "%" + pattern + "%");
            ResultSet rs = prepareStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [ID] " + id + ", [NAME] " + name + ", [EMAIL] " + email);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verindung zur Datenbank: " + e.getMessage());
        }
    }

    public static void deleteStudentDemo(int studentId) {
        System.out.println("Delete Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password)) {
            System.out.println("Verbindung zur DB hergestellt");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "DELETE FROM `student` WHERE `student`.`id` = ?");
            try {
                prepareStatement.setInt(1, studentId);
                int rowAffected = prepareStatement.executeUpdate();
                System.out.println("Anzahl der gelöschten Datensätze: " + rowAffected);
            } catch (SQLException ex) {
                System.out.println("Fehler im SQL-Delete Statement : " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verindung zur DB: " + e.getMessage());
        }
    }



    public static void updateStudentDemo(String neuerName, String neueEmail, int studentId) {
        System.out.println("Update Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password))
        {
            System.out.println("Verbindung zur DB hergestellt");
            PreparedStatement prepareStatement = conn.prepareStatement(
                    "UPDATE `student` SET `name` = ?, `email` = ? WHERE `student`.`id` = ?");
            try {
                prepareStatement.setString(1, neuerName);
                prepareStatement.setString(2, neueEmail);
                prepareStatement.setInt(3, studentId);
                int affectedRows = prepareStatement.executeUpdate();
                System.out.println("Anzahl der aktualisierten Datensätze: " + affectedRows);
            } catch (SQLException ex) {
                System.out.println("Fehler im SQL-Update Statement : " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verindung zur DB: " + e.getMessage());
        }
    }


    public static void insertStudentDemo(String name, String email) {
        System.out.println("Insert Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "";

        //Verbindungsaufbau, connection wird durch denn try/catch nach dem Ausführen auto. geschlossen
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, password)) {
            System.out.println("Verbindung zur DB hergestellt");

            //kompiliert die DB vor, mit setString werden dann die Daten
            //als String übergeben und nicht als SQL-Statement, sicherheitsrelevant und schneller!
            //das selbe Statement kann immmer wieder mit versch. Parameter hergenommen werden!
            PreparedStatement prepareStatement = conn.prepareStatement
                    ("INSERT INTO `student` (`id`, `name`, `email`) VALUES (NULL, ?, ?)");

            //ein zweiter try/catch damit man die Fehlerquelle unterscheiden kann
            try {
                prepareStatement.setString(1, name); // 1 für das erste ? im prepareStatement
                prepareStatement.setString(2, email);
                //liefert die Anzahl der betroffenen Datensätze zurück
                int affectedRows = prepareStatement.executeUpdate();
                System.out.println(affectedRows + " Datensätze eingefügt");
            } catch (SQLException ex) {
                System.out.println("Fehler im SQL-INSERT Statement : " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verindung zur DB: " + e.getMessage());
        }
    }

    public static void selectAllDemo() {
        System.out.println("Select Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String pwd = "";

        //try catch weil immer eine SQLException auftreten kann!
        try (Connection conn = DriverManager.getConnection(connectionUrl, username, pwd)) {     //DB-connect auf Basis der url+user+pwd

            System.out.println("Verbindung zur DB hergestellt!");
            PreparedStatement prepareStatement = conn.prepareStatement("SELECT * FROM `student`"); //SQL Verbindungsstatement
            ResultSet rs = prepareStatement.executeQuery();         //DB-Abfrage, das Ergebnis wird in rs(resultset) gespeichert

            //solange es einen Datensatz zum auslesen gibt ("Zeigermethode") wird die Schleife ausgeführt - bis rs.next false liefert
            while (rs.next())
            {
                int id = rs.getInt("id");                //die Spalten id mit getInt holen, in integer umwandeln und im int id speichern
                String name = rs.getString("name");      //den Text als String aus der Spalte holen
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [id] " + id + ", [name] " + name + ", [email] " + email);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aufbau der Verbindung zur DB: " + e.getMessage());
        }
    }


}
