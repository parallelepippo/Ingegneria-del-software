package entities;

import dbmanager.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpedizioneDAO {
    private Connection connection;
    public SpedizioneDAO() throws SQLException {
        connection = DatabaseManager.getInstance().getConnection();
    }

    public Spedizione getById(int id) throws SQLException {
        String query = "SELECT * FROM spedizione WHERE trackingcode = ?".replace("?", String.valueOf(id));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            //statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Spedizione spedizione = new Spedizione();
                ClienteDAO clienteDAO = new ClienteDAO();
                spedizione.setTrackingCode(resultSet.getInt("trackingCode"));
                spedizione.setMittente(clienteDAO.getById(resultSet.getInt("idMittente")));
                spedizione.setDestinatario(clienteDAO.getById(resultSet.getInt("idDestinatario")));
                spedizione.setStato(resultSet.getString("stato"));
                spedizione.setCodiceQR(resultSet.getString("codiceQR"));
                spedizione.setDeposito();
                spedizione.setCorriere();
                spedizione.setDataSpedizione(resultSet.getDate("dataSpedizione"));
                spedizione.setDataConsegnaStatic(resultSet.getDate("dataConsegna"));
                spedizione.setPesoPacco(resultSet.getDouble("pesoPacco"));
                spedizione.setPrezzo(resultSet.getDouble("prezzo"));
                spedizione.setTipoSpedizione(resultSet.getString("tipoSpedizione"));

                return spedizione;
            }
        } catch(SQLException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Spedizione> getAll(String filtro) {
        String query = "SELECT * FROM spedizione " + filtro;
        List<Spedizione> spedizioni = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Spedizione spedizione = new Spedizione();
                ClienteDAO clienteDAO = new ClienteDAO();
                spedizione.setTrackingCode(resultSet.getInt("trackingCode"));
                spedizione.setMittente(clienteDAO.getById(resultSet.getInt("idMittente")));
                spedizione.setDestinatario(clienteDAO.getById(resultSet.getInt("idDestinatario")));
                spedizione.setStato(resultSet.getString("stato"));
                spedizione.setCodiceQR(resultSet.getString("codiceQR"));
                spedizione.setDeposito();
                spedizione.setCorriere();
                spedizione.setDataSpedizione(resultSet.getDate("dataSpedizione"));
                spedizione.setDataConsegnaStatic(resultSet.getDate("dataConsegna"));
                spedizione.setPesoPacco(resultSet.getDouble("pesoPacco"));
                spedizione.setPrezzo(resultSet.getDouble("prezzo"));
                spedizione.setTipoSpedizione(resultSet.getString("tipoSpedizione"));

                spedizioni.add(spedizione);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return spedizioni;
    }

    public void save(Spedizione spedizione) {
        String query = "INSERT INTO spedizione (trackingCode, codiceQR, idMittente, idDestinatario, " +
                "idDeposito, idCorriere, pesoPacco, prezzo, stato, dataSpedizione, dataConsegna, tipospedizione ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            java.sql.Date dataSpedizione = new java.sql.Date(spedizione.getDataSpedizione().getTime());
            java.sql.Date dataConsegna = new java.sql.Date(spedizione.getDataConsegna().getTime());

            statement.setDouble(1, spedizione.getTrackingCode());
            statement.setString(2, spedizione.getCodiceQR());
            statement.setInt(3, spedizione.mittente.getId());
            statement.setInt(4, spedizione.destinatario.getId());
            statement.setInt(5,    spedizione.deposito.getIdMagazzino());
            statement.setInt(6, spedizione.corriere.getId());
            statement.setDouble(7, spedizione.getPesoPacco());
            statement.setDouble(8, spedizione.getPrezzo());
            statement.setString(9, spedizione.getStato());
            statement.setDate(10, dataSpedizione);
            statement.setDate(11, dataConsegna);
            statement.setString(12, spedizione.getTipoSpedizione());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Spedizione spedizione) {
        String query = "UPDATE spedizione SET trackingCode = ?, codiceQR = ?, idMittente = ?, idDestinatario = ?, " +
                " idDeposito = ?, idCorriere = ?, pesoPacco = ?, prezzo = ?, stato = ?, dataSpedizione = ?, " +
                "dataConsegna = ?, tipoSpedizione = ? WHERE trackingCode = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            java.sql.Date dataSpedizione = new java.sql.Date(spedizione.getDataSpedizione().getTime());
            java.sql.Date dataConsegna = new java.sql.Date(spedizione.getDataConsegna().getTime());

            statement.setDouble(1, spedizione.getTrackingCode());
            statement.setString(2, spedizione.getCodiceQR());
            statement.setInt(3, spedizione.mittente.getId());
            statement.setInt(4, spedizione.destinatario.getId());
            statement.setInt(5,    spedizione.deposito.getIdMagazzino());
            statement.setInt(6, spedizione.corriere.getId());
            statement.setDouble(7, spedizione.getPesoPacco());
            statement.setDouble(8, spedizione.getPrezzo());
            statement.setString(9, spedizione.getStato());
            statement.setDate(10, dataSpedizione);
            statement.setDate(11, dataConsegna);
            statement.setString(12, spedizione.getTipoSpedizione());

            statement.setInt(13, spedizione.getTrackingCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Spedizione spedizione) {
        String query = "DELETE FROM spedizione WHERE trackingCode = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, spedizione.getTrackingCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

