package br.com.omniroute;
import br.com.omniroute.util.DB;
import br.com.omniroute.view.LoginView;
import javax.swing.*;
public class Main {
 public static void main(String[] args) {
  DB.init();
  SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
 }
}
