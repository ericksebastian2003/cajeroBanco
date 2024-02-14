import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class inicio extends JFrame {
    private JLabel label1;
    private JLabel label2;
    private JTextField txtuser;
    private JButton inicarsesionButton;
    private JTextField txtpass;
    private JPanel login;


    public inicio() {
        super("INICIO DE SESIÓN");
        setContentPane(login);
        setVisible(true);
        setSize(400,400);


        inicarsesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexionBase conexionBase = new conexionBase();
                String usuario = txtuser.getText();
                String password = txtpass.getText();

                String query = "SELECT nombre_usuario, contraseña FROM usuarios WHERE nombre_usuario = ? AND contraseña = ? ";

                try (Connection connection = conexionBase.conexionBases();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    preparedStatement.setString(1, usuario);
                    preparedStatement.setString(2, password);


                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {

                            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                            JOptionPane.showMessageDialog(null,"Bienvenid@ "+usuario+" al sistema");
                            menu menu1 = new menu(usuario);
                            menu1.usuarios(usuario);
                            menu1.setVisible(true);

                            // Cerrar la ventana actual de inicio de sesión
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos");
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al realizar el inicio de sesión: " + ex.getMessage());
                }
            }
        });
    }


}

