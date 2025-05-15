package Conexion_;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame {

    private JTextField txtTarjeta;
    private JPasswordField txtNip;
    private JButton btnLogin;
    private String numeroTarjetaActual;

    public LoginUI() {
        setTitle("Login - Cajero Automático");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(34, 45, 65));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTarjeta = new JLabel("Número de tarjeta:");
        lblTarjeta.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblTarjeta, gbc);

        txtTarjeta = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtTarjeta, gbc);

        JLabel lblNip = new JLabel("NIP:");
        lblNip.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblNip, gbc);

        txtNip = new JPasswordField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtNip, gbc);

        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        add(panel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> intentarLogin());

        setVisible(true);
    }

    private void intentarLogin() {
        String tarjeta = txtTarjeta.getText().trim();
        String nip = new String(txtNip.getPassword()).trim();

        if (tarjeta.isEmpty() || nip.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa ambos campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean valido = LogicaCambioNIP.verificarLogin(tarjeta, nip);

        if (valido) {
            numeroTarjetaActual = tarjeta;
            mostrarPerfil();
        } else {
            JOptionPane.showMessageDialog(this, "Número de tarjeta o NIP incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarPerfil() {
        JFrame perfil = new JFrame("Perfil del Cliente");
        perfil.setSize(450, 350);
        perfil.setLocationRelativeTo(null);
        perfil.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        perfil.setLayout(new BorderLayout());

        JPanel panelInfo = new JPanel(new GridLayout(4, 1, 10, 10));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelInfo.setBackground(new Color(45, 55, 80));

        JLabel lblBienvenida = new JLabel("Bienvenido, " + LogicaCambioNIP.obtenerNombre(numeroTarjetaActual));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));
        panelInfo.add(lblBienvenida);

        // Aquí podrías cargar y mostrar saldo de la cuenta si lo tienes en BD
        JLabel lblSaldo = new JLabel("Saldo disponible: $10,000.00 (Ejemplo)");
        lblSaldo.setForeground(Color.WHITE);
        lblSaldo.setFont(new Font("Arial", Font.PLAIN, 16));
        panelInfo.add(lblSaldo);

        perfil.add(panelInfo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(34, 45, 65));

        JButton btnRetiro = new JButton("Retiro");
        btnRetiro.setEnabled(false);
        JButton btnDeposito = new JButton("Depósito");
        btnDeposito.setEnabled(false);
        JButton btnConsulta = new JButton("Consulta");
        btnConsulta.setEnabled(false);

        JButton btnCambioNIP = new JButton("Cambio de NIP");

        btnRetiro.setPreferredSize(new Dimension(110, 40));
        btnDeposito.setPreferredSize(new Dimension(110, 40));
        btnConsulta.setPreferredSize(new Dimension(110, 40));
        btnCambioNIP.setPreferredSize(new Dimension(140, 40));

        panelBotones.add(btnRetiro);
        panelBotones.add(btnDeposito);
        panelBotones.add(btnConsulta);
        panelBotones.add(btnCambioNIP);

        perfil.add(panelBotones, BorderLayout.CENTER);

        btnCambioNIP.addActionListener(e -> mostrarCambioNIPDialog());

        perfil.setVisible(true);
    }

    private void mostrarCambioNIPDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Nuevo NIP:"));
        JPasswordField txtNuevoNIP1 = new JPasswordField();
        panel.add(txtNuevoNIP1);

        panel.add(new JLabel("Confirmar nuevo NIP:"));
        JPasswordField txtNuevoNIP2 = new JPasswordField();
        panel.add(txtNuevoNIP2);

        int opcion = JOptionPane.showConfirmDialog(this, panel, "Cambio de NIP", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion == JOptionPane.OK_OPTION) {
            String nip1 = new String(txtNuevoNIP1.getPassword()).trim();
            String nip2 = new String(txtNuevoNIP2.getPassword()).trim();

            if (nip1.isEmpty() || nip2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe llenar ambos campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!nip1.equals(nip2)) {
                JOptionPane.showMessageDialog(this, "Los NIPs no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean actualizado = LogicaCambioNIP.actualizarNIP(numeroTarjetaActual, nip1);

            if (actualizado) {
                String nombre = LogicaCambioNIP.obtenerNombre(numeroTarjetaActual);
                String correo = LogicaCambioNIP.obtenerCorreo(numeroTarjetaActual);
                if (correo != null && !correo.isEmpty()) {
                    EmailSender.enviarCorreoCambioNIP(correo, nombre != null ? nombre : "");
                }
                JOptionPane.showMessageDialog(this, "NIP cambiado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el NIP.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginUI());
    }
}
