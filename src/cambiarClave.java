import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class cambiarClave extends JFrame {
    private JPanel panel1;
    private JPasswordField txtNueva;
    private JButton regresarAlMenúButton;
    private JButton cambiarClaveButton;
    private String nombreUsuario; // Agregar campo para almacenar el nombre de usuario

    public cambiarClave(String nombreUsuario){ // Cambiar el constructor para recibir el nombre de usuario
        this.nombreUsuario = nombreUsuario; // Almacenar el nombre de usuario
        setVisible(true);
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,400);

        cambiarClaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nuevaContraseña = new String(txtNueva.getPassword()); // Obtener la nueva contraseña
                cambiar(nombreUsuario, nuevaContraseña); // Llamar al método cambiar con el nombre de usuario y la nueva contraseña
            }
        });
        regresarAlMenúButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                menu menu= new menu(nombreUsuario);
                menu.setVisible(true);

            }
        });
    }

    public void cambiar(String nombreUsuario, String nuevaContraseña){ // Modificar el método cambiar para recibir la nueva contraseña
        conexionBase conexionBase = new conexionBase();

        String query = "UPDATE usuarios SET contraseña = ? WHERE nombre_usuario = ?";
        try (
                Connection connection = conexionBase.conexionBases();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Establecer los parámetros de la consulta
            preparedStatement.setString(1, nuevaContraseña);
            preparedStatement.setString(2, nombreUsuario);

            // Ejecutar la consulta de actualización
            int filasActualizadas = preparedStatement.executeUpdate();

            // Verificar si se actualizaron filas
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Contraseña cambiada correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Usuario no encontrado");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cambiar la contraseña: " + ex.getMessage());
        }
    }
}

