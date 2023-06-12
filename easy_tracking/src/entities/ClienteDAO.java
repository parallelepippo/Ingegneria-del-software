package entities;

import dbmanager.DatabaseManager;
import login.UtenteDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;
    public ClienteDAO() throws SQLException {
        connection = DatabaseManager.getInstance().getConnection();
    }

    public Cliente getById(int id) throws SQLException {
        String query = "SELECT * FROM cliente WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCognome(resultSet.getString("cognome"));
                cliente.setIndirizzo(resultSet.getString("indirizzo"));
                cliente.setComune(resultSet.getString("comune"));
                cliente.setCap(resultSet.getInt("cap"));
                cliente.setNumero(resultSet.getString("numero"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setCodiceFiscale(resultSet.getString("codiceFiscale"));

                return cliente;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Cliente> getAll() {
        String query = "SELECT * FROM cliente";
        List<Cliente> clienti = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCognome(resultSet.getString("cognome"));
                cliente.setIndirizzo(resultSet.getString("indirizzo"));
                cliente.setComune(resultSet.getString("comune"));
                cliente.setCap(resultSet.getInt("cap"));
                cliente.setNumero(resultSet.getString("numero"));
                cliente.setEmail(resultSet.getString("email"));
                cliente.setCodiceFiscale(resultSet.getString("codiceFiscale"));

                clienti.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clienti;
    }

    public void save(Cliente cliente) {
        String query = "INSERT INTO cliente (nome, cognome, indirizzo, comune, cap, numero, email, codiceFiscale ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


        try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, cliente.getNome());
        statement.setString(2, cliente.getCognome());
        statement.setString(3, cliente.getIndirizzo());
        statement.setString(4, cliente.getComune());
        statement.setInt(5, cliente.getCap());
        statement.setString(6, cliente.getNumero());
        statement.setString(7, cliente.getEmail());
        statement.setString(8, cliente.getCodiceFiscale());

        statement.executeUpdate();

        UtenteDAO uDao = new UtenteDAO();
        uDao.save(String.valueOf(cliente.getId()) + "cl", "cliente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Cliente cliente) {
        String query = "UPDATE cliente SET nome = ?, cognome = ?, indirizzo = ?, comune = ?, cap = ?, numero = ?, email = ?, codiceFiscale = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getCognome());
            statement.setString(3, cliente.getIndirizzo());
            statement.setString(4, cliente.getComune());
            statement.setInt(5, cliente.getCap());
            statement.setString(6, cliente.getNumero());
            statement.setString(7, cliente.getEmail());
            statement.setString(8, cliente.getCodiceFiscale());

            statement.setInt(9, cliente.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Cliente cliente) {
        String query = "DELETE FROM cliente WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, cliente.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

