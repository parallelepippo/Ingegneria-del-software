package login;

import dbmanager.DatabaseManager;
import main.GUI;

import java.sql.Connection;
import java.sql.SQLException;


public class LoginManager {
    private static LoginManager instance;
    private boolean loggedIn;
    private Connection connection;

    public LoginManager() throws SQLException {
        connection = DatabaseManager.getInstance().getConnection();
        loggedIn = false;
    }



    public static LoginManager getInstance() throws SQLException {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void login(Utente utenteLog) throws SQLException {
        String ruolo = "";
        if (utenteLog.getId().substring(0,2).equals("cl")) {
            ruolo = "cliente";
        } else if (utenteLog.getId().substring(0,2).equals("co")) {
            ruolo = "corriere";
        } else if (utenteLog.getId().substring(0,2).equals("ad")) {
            ruolo = "admin";
        }

        UtenteDAO uDao = new UtenteDAO();
        Utente utente = uDao.getById(utenteLog.getId());

        if (utente==null) {
            loggedIn = false;
            return;
        }
        if (utenteLog.getId().equals(utente.getId()) && utenteLog.getPassword().equals(utente.getPassword()) && ruolo.equalsIgnoreCase(utente.getRuolo())) {
            loggedIn = true;
            GUI.setUtente(utente);
            GUI.openMainWindow();
        }
        else {
            loggedIn = false;
        }

    }

    public void logout() {
        loggedIn = false;
    }
}

