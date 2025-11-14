package com.sena.proyecto.view;

import com.sena.proyecto.dao.UsuarioDAO;
import com.sena.proyecto.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioForm extends JFrame {

    UsuarioDAO dao = new UsuarioDAO();

    // Componentes de la interfaz
    JTextField txtNombre, txtApellido, txtEmail, txtPassword, txtRol, txtEstado, txtId;
    JTable table;

    public UsuarioForm() {
        setTitle("Gestión de Usuarios");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Crear componentes
        crearComponentes();
        
        // Hacer visible la ventana
        setVisible(true);
    }

    private void crearComponentes() {
        // Panel de entrada de datos
        JPanel panelEntrada = new JPanel(new GridLayout(4, 4, 5, 5));
        
        panelEntrada.add(new JLabel("ID:"));
        txtId = new JTextField(5);
        panelEntrada.add(txtId);

        panelEntrada.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(10);
        panelEntrada.add(txtNombre);

        panelEntrada.add(new JLabel("Apellido:"));
        txtApellido = new JTextField(10);
        panelEntrada.add(txtApellido);

        panelEntrada.add(new JLabel("Email:"));
        txtEmail = new JTextField(15);
        panelEntrada.add(txtEmail);

        panelEntrada.add(new JLabel("Password:"));
        txtPassword = new JTextField(10);
        panelEntrada.add(txtPassword);

        panelEntrada.add(new JLabel("Rol:"));
        txtRol = new JTextField(10);
        panelEntrada.add(txtRol);

        panelEntrada.add(new JLabel("Estado (1=Activo/0=Inactivo):"));
        txtEstado = new JTextField(3);
        panelEntrada.add(txtEstado);

        add(panelEntrada);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnListar = new JButton("Listar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnListar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones);

        // Tabla
        table = new JTable(new DefaultTableModel(
            new Object[]{"ID","Nombre","Apellido","Email","Password","Rol","Estado"}, 0));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(750, 300));
        add(scroll);

        // Eventos de botones
        btnAgregar.addActionListener(e -> agregar());
        btnListar.addActionListener(e -> listar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void agregar() {
        try {
            Usuario u = new Usuario(
                0,
                txtNombre.getText(),
                txtApellido.getText(),
                txtEmail.getText(),
                txtPassword.getText(),
                txtRol.getText(),
                txtEstado.getText().equals("1")
            );

            if (dao.insertarUsuario(u)) {
                JOptionPane.showMessageDialog(this, "✅ Usuario agregado correctamente");
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al agregar usuario");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        }
    }

    private void listar() {
        try {
            List<Usuario> lista = dao.listarUsuarios();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            for (Usuario u : lista) {
                model.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getEmail(),
                    u.getPassword(),
                    u.getRol(),
                    u.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al listar: " + e.getMessage());
        }
    }

    private void actualizar() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Ingrese un ID para actualizar");
                return;
            }

            Usuario u = new Usuario(
                Integer.parseInt(txtId.getText()),
                txtNombre.getText(),
                txtApellido.getText(),
                txtEmail.getText(),
                txtPassword.getText(),
                txtRol.getText(),
                txtEstado.getText().equals("1")
            );

            if (dao.actualizarUsuario(u)) {
                JOptionPane.showMessageDialog(this, "✅ Usuario actualizado correctamente");
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al actualizar usuario");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        try {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Ingrese un ID para eliminar");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar el usuario ID: " + txtId.getText() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.eliminarUsuario(Integer.parseInt(txtId.getText()))) {
                    JOptionPane.showMessageDialog(this, "✅ Usuario eliminado correctamente");
                    listar();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Error al eliminar usuario");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtRol.setText("");
        txtEstado.setText("");
    }
}