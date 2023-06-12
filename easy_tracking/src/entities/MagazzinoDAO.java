package entities;

import dbmanager.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MagazzinoDAO {
    private Connection connection;
    public MagazzinoDAO() throws SQLException {
        connection = DatabaseManager.getInstance().getConnection();
    }

    public Magazzino getById(int id) throws SQLException {
        String query = "SELECT * FROM magazzino WHERE idmagazzino = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Magazzino magazzino = new Magazzino();
                magazzino.setIdMagazzino(resultSet.getInt("idmagazzino"));
                magazzino.setIndirizzo(resultSet.getString("indirizzo"));
                magazzino.setComune(resultSet.getString("comune"));
                magazzino.setCap(resultSet.getInt("cap"));
                magazzino.setPacchiInGiacenza(resultSet.getInt("pacchiInGiacenza"));
                magazzino.setCapienzaPacchi(resultSet.getInt("capienzaPacchi"));

                return magazzino;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Magazzino> getAll() {
        String query = "SELECT * FROM magazzino";
        List<Magazzino> magazzini = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Magazzino magazzino = new Magazzino();
                magazzino.setIdMagazzino(resultSet.getInt("idmagazzino"));
                magazzino.setIndirizzo(resultSet.getString("indirizzo"));
                magazzino.setComune(resultSet.getString("comune"));
                magazzino.setCap(resultSet.getInt("cap"));
                magazzino.setPacchiInGiacenza(resultSet.getInt("pacchiInGiacenza"));
                magazzino.setCapienzaPacchi(resultSet.getInt("capienzaPacchi"));

                magazzini.add(magazzino);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return magazzini;
    }

    public void save(Magazzino magazzino) {
        String query = "INSERT INTO magazzino (indirizzo, comune, cap, pacchiInGiacenza, capienzaPacchi ) VALUES (?, ?, ?, ?, ?)";


        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, magazzino.getIndirizzo());
            statement.setString(2, magazzino.getComune());
            statement.setInt(3, magazzino.getCap());
            statement.setInt(4, magazzino.getPacchiInGiacenza());
            statement.setInt(5, magazzino.getCapienzaPacchi());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Magazzino magazzino) {
        String query = "UPDATE magazzino SET indirizzo = ?, comune = ?, cap = ?, pacchiInGiacenza = ?, capienzaPacchi = ? WHERE idmagazzino = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, magazzino.getIndirizzo());
            statement.setString(2, magazzino.getComune());
            statement.setInt(3, magazzino.getCap());
            statement.setInt(4, magazzino.getPacchiInGiacenza());
            statement.setInt(5, magazzino.getCapienzaPacchi());

            statement.setInt(6, magazzino.getIdMagazzino());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Magazzino magazzino) {
        String query = "DELETE FROM magazzino WHERE idmagazzino = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, magazzino.getIdMagazzino());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIdMagazzinoFromCap(int capDestinazione) throws SQLException {
        int idMagazzino = 0;
        int nearestCap = getNearestCap(capDestinazione, "magazzino");
        String query = "SELECT idMagazzino FROM magazzino WHERE (capienzaPacchi - pacchiInGiacenza) > 0 and cap = ? ORDER BY idMagazzino ASC LIMIT 1".replace("?", String.valueOf(nearestCap));


        try (PreparedStatement statement = connection.prepareStatement(query)) {

             //statement.setInt(1, nearestCap);
             ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                idMagazzino = resultSet.getInt("idMagazzino");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idMagazzino;
    }


    public int getNearestCap(int capDestinazione, String table) {
        String query = "SELECT distinct cap FROM corriere." + table;
        int capDB = 0;
        List<Integer> all_cap = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                capDB = resultSet.getInt("cap");
                all_cap.add(capDB);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int capVicino = all_cap.get(0);

        for (int cap : all_cap) {
            int distance = Math.abs(cap - capDestinazione);
            int minDistance = Math.abs(capVicino - capDestinazione);

            if (distance < minDistance || (distance == minDistance && cap < capVicino)) {
                capVicino = cap;
            }
        }

        return capVicino;
    }





}

