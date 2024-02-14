import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu extends JFrame {
    private JPanel panel1;
    private JButton RETIROButton;
    private JButton DEPÓSITOButton;
    private JButton VERSALDOButton;
    private JButton CAMBIARCLAVEButton1;
    private JButton SALIRButton;
    private String nombreUsuario;

    public void usuarios(String usuario) {
        this.nombreUsuario = usuario;
    }

    public menu(String nombreUsuario) {
        super("Menu de opciones");
        this.nombreUsuario = nombreUsuario; // Almacenar el nombre de usuario

        setVisible(true);
        setSize(400, 400);
        setContentPane(panel1);

        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel1);
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea salir ? ","AVISO" ,JOptionPane.YES_NO_OPTION);

                try {
                    if (respuesta == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        VERSALDOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                saldos saldos = new saldos();
                saldos.usuarios(nombreUsuario);
                saldos.setVisible(true);
                dispose();
            }
        });
        CAMBIARCLAVEButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cambiarClave cambiarClave = new cambiarClave(nombreUsuario); // Pasar el nombre de usuario al constructor
                cambiarClave.setVisible(true); // Mostrar la ventana para cambiar la clave
                dispose();
            }
        });
        DEPÓSITOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                depositos depositos = new depositos();
                depositos.usuarios(nombreUsuario);
                depositos.setVisible(true);
                dispose();
            }
        });
        RETIROButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                retiros retiros = new retiros();
                retiros.usuarios(nombreUsuario);
                retiros.setVisible(true);
                dispose();
            }
        });
    }
}
