package main;

import entities.*;
import login.LoginManager;
import login.Utente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;


public class GUI {
    private static JFrame loginFrame = new JFrame("Login");
    private static JFrame mainFrame = new JFrame("Easy Tracking");
    private static BorderLayout borderLayout = new BorderLayout();
    static int idSearch;
    static DefaultTableModel tableModel = new DefaultTableModel();
    private static Utente utente;

    public static void openLoginWindow() {
        loginFrame.setLayout(new GridBagLayout());

        JLabel idLabel = new JLabel("Id: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JTextField idField = new JTextField(15);
        JTextField passwordField = new JTextField(15);
        JButton loginButton = new JButton("Login");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        loginFrame.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        loginFrame.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        loginFrame.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        loginFrame.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        loginFrame.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utente utente = new Utente();
                utente.setId(idField.getText());
                idField.setText("");

                utente.setPassword(passwordField.getText());
                passwordField.setText("");


                try {
                    LoginManager.getInstance().login(utente);
                    if (!LoginManager.getInstance().isLoggedIn()) {
                        JOptionPane.showMessageDialog(loginFrame, "Utente o password errati: riprovare!");
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        loginFrame.setSize(350, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);

        loginFrame.setVisible(true);
    }

    public static void openMainWindow() throws SQLException {
        JPanel panel = new JPanel();

        JRadioButton radioSpedizioni = new JRadioButton("Spedizioni");
        radioSpedizioni.setSelected(true);
        radioSpedizioni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createTableSpedizioni();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JRadioButton radioClienti = new JRadioButton("Clienti");
        radioClienti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createTableClienti();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        JRadioButton radioCorrieri = new JRadioButton("Corrieri");
        radioCorrieri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createTableCorrieri();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioSpedizioni);
        radioSpedizioni.setActionCommand("spedizioni");
        buttonGroup.add(radioClienti);
        radioClienti.setActionCommand("clienti");
        buttonGroup.add(radioCorrieri);
        radioCorrieri.setActionCommand("corrieri");

        mainFrame.setLayout(borderLayout);
        mainFrame.add(panel, BorderLayout.PAGE_START);

        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(radioSpedizioni, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(radioClienti, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(radioCorrieri, gbc);

        loginFrame.setVisible(false);

        mainFrame.setSize(800, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);


        if(utente.getRuolo().equals("admin")) {
            JPanel buttonPanel = new JPanel();
            mainFrame.add(buttonPanel, BorderLayout.PAGE_END);
            buttonPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc2 = new GridBagConstraints();
            JButton createButton = new JButton("Crea");
            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String object = buttonGroup.getSelection().getActionCommand();
                    try {
                        createWindowGetId("crea", object);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            JButton modifyButton = new JButton("Modifica");
            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String object = buttonGroup.getSelection().getActionCommand();
                    try {
                        createWindowGetId("modifica", object);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            JButton deleteButton = new JButton("Elimina");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String object = buttonGroup.getSelection().getActionCommand();
                    try {
                        createWindowGetId("elimina", object);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            gbc2.gridx = 0;
            gbc2.gridy = 0;
            gbc2.weightx = 0;
            gbc2.weighty = 0;
            buttonPanel.add(createButton, gbc2);

            gbc2.gridx = 1;
            gbc2.gridy = 0;
            gbc2.weightx = 0;
            gbc2.weighty = 0;
            buttonPanel.add(modifyButton, gbc2);

            gbc2.gridx = 2;
            gbc2.gridy = 0;
            gbc2.weightx = 0;
            gbc2.weighty = 0;
            buttonPanel.add(deleteButton, gbc2);


        createTableSpedizioni();
        }

        if(utente.getRuolo().equals("cliente")) {
            createTableSpedizioni();
            radioClienti.setVisible(false);
            radioCorrieri.setVisible(false);
        }

        if(utente.getRuolo().equals("corriere")) {
            JPanel buttonPanel = new JPanel();
            mainFrame.add(buttonPanel, BorderLayout.PAGE_END);
            buttonPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc2 = new GridBagConstraints();
            JButton updateStatoButton = new JButton("Aggiorna Stato");
            updateStatoButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   String object = buttonGroup.getSelection().getActionCommand();
                   try {
                       createWindowGetId("updateStato", object);
                   } catch (SQLException ex) {
                       throw new RuntimeException(ex);
                   }
               }
            });

            gbc2.gridx = 0;
            gbc2.gridy = 0;
            gbc2.weightx = 0;
            gbc2.weighty = 0;
            buttonPanel.add(updateStatoButton, gbc2);

            createTableSpedizioni();
            radioClienti.setVisible(false);
            radioCorrieri.setVisible(false);
        }
    }

    public static void createWindowGetId(String action, String object) throws SQLException {
        JFrame idFrame = new JFrame("Inserisci Id");
        idFrame.setSize(350, 200);

        idFrame.setLayout(new GridBagLayout());

        JLabel idLabel = new JLabel("Id: ");
        JTextField idGetField = new JTextField(15);
        JButton actionButton = new JButton(action.toUpperCase());
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idSearch = Integer.parseInt(idGetField.getText());
                if (object.equalsIgnoreCase("spedizioni")) {
                    try {
                        SpedizioneDAO spedizioneDAO = new SpedizioneDAO();
                        Spedizione spedizione = spedizioneDAO.getById(idSearch);
                        switch (action) {
                            case "elimina":
                                spedizioneDAO.delete(spedizione);
                                createTableSpedizioni();
                            case "modifica":
                                createModificaSpedizione(spedizione, action);
                            case "crea":
                                createModificaSpedizione(spedizione, action);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
                if (object.equalsIgnoreCase("clienti")) {
                    try {
                        ClienteDAO clienteDAO = new ClienteDAO();
                        Cliente cliente = clienteDAO.getById(idSearch);
                        switch (action) {
                            case "elimina":
                                clienteDAO.delete(cliente);
                                createTableClienti();
                            case "modifica":
                                createModificaCliente(cliente, action);
                            case "crea":
                                createModificaCliente(cliente, action);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (object.equalsIgnoreCase("corrieri")) {
                    try {
                        CorriereDAO corriereDAO = new CorriereDAO();
                        Corriere corriere = corriereDAO.getById(idSearch);
                        switch (action) {
                            case "elimina":
                                corriereDAO.delete(corriere);
                                createTableCorrieri();
                            case "modifica":
                                createModificaCorriere(corriere, action);
                            case "crea":
                                createModificaCorriere(corriere, action);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (action.equalsIgnoreCase("updateStato")) {
                    try {
                        SpedizioneDAO spedizioneDAO = new SpedizioneDAO();
                        Spedizione spedizione = spedizioneDAO.getById(idSearch);
                        Corriere corriere = new Corriere();
                        corriere.update_stato(spedizione);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }

                idFrame.setVisible(false);

            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idGetField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(actionButton, gbc);

        idFrame.setLocationRelativeTo(null);
        idFrame.setResizable(false);
        idFrame.setVisible((true));
    }


    private static void createModificaCorriere(Corriere corriere, String action) throws SQLException {
        JFrame idFrame = new JFrame("Modifica Spedizione");
        CorriereDAO corriereDAO = new CorriereDAO();

        idFrame.setSize(800, 500);

        idFrame.setLayout(new GridBagLayout());

        JLabel idLabel = new JLabel("Id: ");
        JLabel nomeLabel = new JLabel("Nome: ");
        JLabel cognomeLabel = new JLabel("Cogome: ");
        JLabel capLabel = new JLabel("CAP: ");
        JTextField idField = new JTextField(15);
        JTextField nomeField = new JTextField(15);
        JTextField cognomeField = new JTextField(15);
        JTextField capField = new JTextField(15);

        JButton saveButton = new JButton("Salva");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {Corriere newCorriere = new Corriere(Integer.valueOf(idField.getText()),nomeField.getText(),
                        cognomeField.getText(), Integer.valueOf(capField.getText()));
                    if (action.equalsIgnoreCase("modifica")) {
                        corriereDAO.update(newCorriere);
                    }
                    if (action.equalsIgnoreCase("crea")) {
                        corriereDAO.save(newCorriere);
                    }
                    createTableCorrieri();
                    idFrame.setVisible(false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(nomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(cognomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(capLabel, gbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(nomeField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(cognomeField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(capField, gbc);


        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(saveButton, gbc);

        idFrame.setLocationRelativeTo(null);
        idFrame.setResizable(false);
        idFrame.setVisible((true));
    }

    private static void createModificaCliente(Cliente cliente, String action) throws SQLException {
        JFrame idFrame = new JFrame("Modifica Cliente");
        ClienteDAO clienteDAO = new ClienteDAO();

        idFrame.setSize(800, 500);

        idFrame.setLayout(new GridBagLayout());

        JLabel idClienteLabel = new JLabel("Id Cliente: ");
        JLabel nomeLabel = new JLabel("Nome: ");
        JLabel cognomeLabel = new JLabel("Cogome: ");
        JLabel comuneLabel = new JLabel("Comune: ");
        JLabel capLabel = new JLabel("CAP: ");
        JLabel indirizzoLabel = new JLabel("Indirizzo: ");
        JLabel numeroLabel = new JLabel("Numero: ");
        JLabel emailLabel = new JLabel("Email: ");
        JLabel codiceFiscaleLabel = new JLabel("Codice Fiscale: ");
        JTextField idClienteField = new JTextField(15);
        JTextField nomeField = new JTextField(15);
        JTextField cognomeField = new JTextField(15);
        JTextField comuneField = new JTextField(15);
        JTextField capField = new JTextField(15);
        JTextField indirizzoField = new JTextField(15);
        JTextField numeroField = new JTextField(15);
        JTextField emailField = new JTextField(15);
        JTextField codiceFiscaleField = new JTextField(15);

        JButton saveButton = new JButton("Salva");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {Cliente newCliente = new Cliente(Integer.valueOf(idClienteField.getText()),nomeField.getText(),
                        cognomeField.getText(), comuneField.getText(),indirizzoField.getText(), Integer.valueOf(capField.getText()),  numeroField.getText(),
                        emailField.getText(), codiceFiscaleField.getText());
                    if (action.equalsIgnoreCase("modifica")) {
                        clienteDAO.update(newCliente);
                    }
                    if (action.equalsIgnoreCase("crea")) {
                        clienteDAO.save(newCliente);
                    }
                    createTableClienti();
                    idFrame.setVisible(false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idClienteLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(nomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(cognomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(comuneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(capLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(indirizzoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(numeroLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(emailLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(codiceFiscaleLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idClienteField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(nomeField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(cognomeField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(comuneField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(capField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(indirizzoField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(numeroField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(codiceFiscaleField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(saveButton, gbc);

        idFrame.setLocationRelativeTo(null);
        idFrame.setResizable(false);
        idFrame.setVisible(true);
    }

    private static void createModificaSpedizione(Spedizione spedizione, String action) throws SQLException {
        JFrame idFrame = new JFrame("Modifica Spedizione");
        ClienteDAO clienteDAO = new ClienteDAO();
        SpedizioneDAO spedizioneDAO = new SpedizioneDAO();

        idFrame.setSize(800, 500);

        idFrame.setLayout(new GridBagLayout());

        JLabel idMittenteLabel = new JLabel("Id Mittente: ");
        JLabel idDestinatarioLabel = new JLabel("Id Destinatario: ");
        JLabel pesoLabel = new JLabel("Peso: ");
        JLabel tipoSpedizioneLabel = new JLabel("Tipo Spedizione: ");
        JLabel dataSpedizioneLabel = new JLabel("Data Spedizione: ");
        JTextField idMittenteField = new JTextField(15);
        JTextField idDestinatarioField = new JTextField(15);
        JTextField pesoField = new JTextField(15);
        JTextField tipoSpedizioneoField = new JTextField(15);
        JTextField dataSpedizioneField = new JTextField(15);

        JButton saveButton = new JButton("Salva");
        Spedizione newSpedizione = new Spedizione();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    newSpedizione.creaSpedizione(spedizione.getTrackingCode(), clienteDAO.getById(Integer.valueOf(idMittenteField.getText())), clienteDAO.getById(Integer.valueOf(idDestinatarioField.getText())),
                            Double.valueOf(pesoField.getText()), tipoSpedizioneoField.getText(), Date.valueOf(dataSpedizioneField.getText()));
                    if (action.equalsIgnoreCase("modifica")) {
                        spedizioneDAO.update(newSpedizione);
                    }
                    if (action.equalsIgnoreCase("crea")) {
                        spedizioneDAO.save(newSpedizione);
                    }
                    createTableSpedizioni();
                    idFrame.setVisible(false);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idMittenteLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idDestinatarioLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(pesoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(tipoSpedizioneLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(dataSpedizioneLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idMittenteField, gbc);


        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(idDestinatarioField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(pesoField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(tipoSpedizioneoField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(dataSpedizioneField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.weightx = 0;
        gbc.weighty = 0;
        idFrame.add(saveButton, gbc);

        idFrame.setLocationRelativeTo(null);
        idFrame.setResizable(false);
        idFrame.setVisible((true));
    }

    public static ActionListener createTableSpedizioni() throws SQLException {
        String[] columnNames = {"Tracking Code", "ID Mittente", "ID Destinatario", "ID Deposito", "ID Corriere", "Data Spedizione", "Data Consegne", "Stato", "Codice QR", "Peso Pacco", "Prezzo", "Tipo Spedizione"};
        while (tableModel.getRowCount()>0)
        {
            tableModel.removeRow(0);
        }
        tableModel.fireTableDataChanged();
        SpedizioneDAO spedizioneDAO = new SpedizioneDAO();
        String filtro = "";
        if (utente.getRuolo().equals("cliente")) {
            filtro = " where idDestinatario = '" + utente.getId().replace("cl", "") + "'";
        }
        if (utente.getRuolo().equals("corriere")) {
            filtro = " where idCorriere = '" + utente.getId().replace("co", "") + "'";
        }
        java.util.List<Spedizione> spedizioni = spedizioneDAO.getAll(filtro);

        Object[][] data = new Object[spedizioni.size()][columnNames.length];

        for (int i = 0; i < spedizioni.size(); i++) {
            Spedizione spedizione = spedizioni.get(i);
            data[i][0] =  spedizione.getTrackingCode();
            data[i][1] =  spedizione.mittente.getId();
            data[i][2] =  spedizione.destinatario.getId();
            data[i][3] =  spedizione.deposito.getIdMagazzino();
            data[i][4] =  spedizione.corriere.getId();
            data[i][5] =  spedizione.getDataSpedizione();
            data[i][6] =  spedizione.getDataConsegna();
            data[i][7] =  spedizione.getStato();
            data[i][8] =  spedizione.getCodiceQR();
            data[i][9] =  spedizione.getPesoPacco();
            data[i][10] =  spedizione.getPrezzo();
            data[i][11] =  spedizione.getTipoSpedizione();
        }

        tableModel = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(tableModel);
        tableModel.fireTableDataChanged();
        table.validate();
        mainFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        mainFrame.setVisible(true);
        return null;
    }


    public static ActionListener createTableClienti() throws SQLException {
        String[] columnNames = {"Id", "Nome", "Cognome", "Comune", "CAP", "Numero", "Email", "Indirizzo", "Codice Fiscale"};
        ClienteDAO clienteDAO = new ClienteDAO();
        tableModel.fireTableDataChanged();
        while (tableModel.getRowCount()>0)
        {
            tableModel.removeRow(0);
        }
        List<Cliente> clienti = clienteDAO.getAll();

        Object[][] data = new Object[clienti.size()][columnNames.length];

        for (int i = 0; i < clienti.size(); i++) {
            Cliente cliente = clienti.get(i);
            data[i][0] =  cliente.getId();
            data[i][1] =  cliente.getNome();
            data[i][2] =  cliente.getCognome();
            data[i][3] =  cliente.getComune();
            data[i][4] =  cliente.getCap();
            data[i][5] =  cliente.getNumero();
            data[i][6] =  cliente.getEmail();
            data[i][7] =  cliente.getIndirizzo();
            data[i][8] =  cliente.getCodiceFiscale();
        }

        tableModel = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(tableModel);
        tableModel.fireTableDataChanged();
        table.validate();

        mainFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        mainFrame.setVisible(true);
        return null;
    }

    public static ActionListener createTableCorrieri() throws SQLException {
        String[] columnNames = {"Id", "Nome", "Cognome", "CAP"};
        CorriereDAO corriereDAO = new CorriereDAO();
        tableModel.fireTableDataChanged();
        while (tableModel.getRowCount()>0)
        {
            tableModel.removeRow(0);
        }
        java.util.List<Corriere> corrieri = corriereDAO.getAll();

        Object[][] data = new Object[corrieri.size()][columnNames.length];

        for (int i = 0; i < corrieri.size(); i++) {
            Corriere corriere = corrieri.get(i);
            data[i][0] =  corriere.getId();
            data[i][1] =  corriere.getNome();
            data[i][2] =  corriere.getCognome();
            data[i][3] =  corriere.getCap();
        }

        tableModel = new DefaultTableModel(data, columnNames);
        tableModel.fireTableDataChanged();
        JTable table = new JTable(tableModel);
        table.validate();
        mainFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        mainFrame.setVisible(true);
        return null;
    }



    public static void setUtente(Utente utente) {
        GUI.utente = utente;
    }
}
