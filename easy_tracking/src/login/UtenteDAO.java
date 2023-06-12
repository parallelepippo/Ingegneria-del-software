package login;

import dbmanager.DatabaseManager;
import encrypt.StringEncryption;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {
    private Connection connection;
    public UtenteDAO() throws SQLException {
        connection = DatabaseManager.getInstance().getConnection();
    }

    public Utente getById(String id) throws SQLException {
        String query = "SELECT * FROM utente WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Utente utente = new Utente();
                utente.setId(resultSet.getString("id"));
                utente.setPassword(resultSet.getString("password"));
                utente.setRuolo(resultSet.getString("ruolo"));

                return utente;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Utente> getAll() {
        String query = "SELECT * FROM utente";
        List<Utente> utenti = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Utente utente = new Utente();
                utente.setId(resultSet.getString("id"));
                utente.setPassword(resultSet.getString("password"));
                utente.setRuolo(resultSet.getString("ruolo"));

                utenti.add(utente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utenti;
    }

    public void save(String id, String ruolo) {
        String query = "INSERT INTO utente (id, password, ruolo) VALUES (?, ?, ?)";


        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);
            statement.setString(2, StringEncryption.encrypt(String.valueOf(id)));
            statement.setString(3, ruolo);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(Utente utente) {
        String query = "UPDATE utente password = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, utente.getPassword());

            statement.setString(2, utente.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Utente utente) {
        String query = "DELETE FROM utente WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, utente.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






}

