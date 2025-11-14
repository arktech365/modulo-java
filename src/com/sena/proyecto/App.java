package com.sena.proyecto;

import com.sena.proyecto.view.UsuarioForm;

public class App {
    public static void main(String[] args) {
        // Usar invokeLater para thread safety en Swing
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new UsuarioForm().setVisible(true);
            }
        });
    }
}