import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class saldos extends  JFrame {
    private JTextField txtSaldo;
    private JButton REGRESARALMENÚPRINCIPALButton;
    private JPanel panelsaldos;
    private String nombreUsuario;

    public void usuarios(String usuario) {
        this.nombreUsuario = usuario;
    }

    public saldos() {
        super("Saldo");
        setContentPane(panelsaldos);
        setVisible(true);
        setSize(400, 400);

        REGRESARALMENÚPRINCIPALButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu menu = new menu(nombreUsuario);
                menu.setVisible(true);
                dispose();
            }
        });

        // Visualizar el saldo después de que el marco sea visible y cuadroSaldo esté inicializado
        visualizarSaldo(nombreUsuario);
    }

    public void visualizarSaldo(String usuario) {
        String nombreUsuario = usuario;
        String saldo="0";
        conexionBase conexionBase = new conexionBase();
        String query = "SELECT saldo FROM usuarios WHERE nombre_usuario = ?";

        try (Connection connection = conexionBase.conexionBases();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, saldo);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (query.equals(nombreUsuario)) {
                    txtSaldo.setText(saldo);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la consulta: " + ex.getMessage());
        }
    }



}

