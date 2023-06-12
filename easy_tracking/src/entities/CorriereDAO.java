package entities;

import dbmanager.DatabaseManager;
import login.UtenteDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CorriereDAO {
    private Connection connection;
    public CorriereDAO() throws SQLException {
        connection = DatabaseManager.getInstance().getConnection();
    }

    public Corriere getById(int id) throws SQLException {
        String query = "SELECT * FROM corriere WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Corriere corriere = new Corriere();
                corriere.setId(resultSet.getInt("id"));
                corriere.setNome(resultSet.getString("nome"));
                corriere.setCognome(resultSet.getString("cognome"));
                corriere.setCap(resultSet.getInt("cap"));

                return corriere;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Corriere> getAll() {
        String query = "SELECT * FROM corriere";
        List<Corriere> corrieri = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Corriere corriere = new Corriere();
                corriere.setId(resultSet.getInt("id"));
                corriere.setNome(resultSet.getString("nome"));
                corriere.setCognome(resultSet.getString("cognome"));
                corriere.setCap(resultSet.getInt("cap"));

                corrieri.add(corriere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return corrieri;
    }

    public void save(Corriere corriere) {
        String query = "INSERT INTO corriere (nome, cognome, cap ) VALUES (?, ?, ?)";


        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, corriere.getNome());
            statement.setString(2, corriere.getCognome());
            statement.setInt(3, corriere.getCap());

            statement.executeUpdate();

            UtenteDAO uDao = new UtenteDAO();
            uDao.save(String.valueOf(corriere.getId()) + "co", "corriere");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Corriere corriere) {
        String query = "UPDATE corriere SET nome = ?, cognome = ?, cap = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, corriere.getNome());
            statement.setString(2, corriere.getCognome());
            statement.setInt(3, corriere.getCap());

            statement.setInt(4, corriere.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Corriere corriere) {
        String query = "DELETE FROM corriere WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, corriere.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIdCorriereFromCap(int capDestinazione) throws SQLException {
        int idCorriere = 0;
        MagazzinoDAO magDao = new MagazzinoDAO();
        int nearestCap = magDao.getNearestCap(capDestinazione, "corriere");
        String query = "SELECT id FROM corriere WHERE cap = ? ORDER BY id ASC LIMIT 1".replace("?", String.valueOf(nearestCap));
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, nearestCap);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                idCorriere = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idCorriere;
    }





}

