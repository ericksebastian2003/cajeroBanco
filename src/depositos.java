import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class depositos extends JFrame {
    private JTextField txtDeposito;
    private JButton depositarButton;
    private JButton regresarAlMenuPrincipalButton;
    private JPanel panel1;
    private String nombreUsuario;
    public void usuarios(String usuario) {
        this.nombreUsuario = usuario;

    }

    public depositos() {
        super("Depósitos");
        setContentPane(panel1);
        setSize(400,400);
        setVisible(true);

        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String montoDeposito = txtDeposito.getText();
                depositar(montoDeposito, nombreUsuario);
            }
        });
        regresarAlMenuPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu menu=new menu(nombreUsuario);
                menu.setVisible(true);
                dispose();
            }
        });
    }

    public void depositar(String montoEnviado, String nombreUsuario) {
        conexionBase conexionBase = new conexionBase();
        String monto = montoEnviado;
        String tipoTransaccion = "Depósito";
        String queryUserId = "SELECT idUser FROM usuarios WHERE nombre_usuario = ?";
        try (Connection connection = conexionBase.conexionBases();
             PreparedStatement preparedStatementUserId = connection.prepareStatement(queryUserId)) {
            preparedStatementUserId.setString(1, nombreUsuario);

            try (ResultSet resultSetUserId = preparedStatementUserId.executeQuery()) {
                if (resultSetUserId.next()) {
                    // Obtener el ID del usuario
                    int idUsuario = resultSetUserId.getInt("idUser");

                    // Consulta para realizar la inserción en la tabla de transacciones
                    String queryInsert = "INSERT INTO transacciones (idUserFK, tipoTransaccion, monto, fecha_hora) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
                    try (PreparedStatement preparedStatementInsert = connection.prepareStatement(queryInsert)) {
                        // Establecer los parámetros de la consulta de inserción
                        preparedStatementInsert.setInt(1, idUsuario); // ID del usuario
                        preparedStatementInsert.setString(2, tipoTransaccion); // Tipo de transacción
                        preparedStatementInsert.setString(3, monto); // Monto de la transacción

                        // Ejecutar la consulta para insertar los datos
                        int filasInsertadas = preparedStatementInsert.executeUpdate();
                        if (filasInsertadas > 0) {
                            JOptionPane.showMessageDialog(null, "Usted ha depositado realizado correctamente."+monto+" dolares");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo realizar el depósito.");
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error al insertar datos en la base de datos: " + e.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la consulta: " + ex.getMessage());
        }
    }

}
