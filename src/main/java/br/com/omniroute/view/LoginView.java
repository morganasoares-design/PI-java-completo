package br.com.omniroute.view;
import br.com.omniroute.model.dao.UsuarioDAO;import javax.swing.*;import java.awt.*;
public class LoginView extends JFrame {
 public LoginView(){setTitle("OMNIRoute - Login");setSize(380,230);setLocationRelativeTo(null);setDefaultCloseOperation(EXIT_ON_CLOSE);
  JTextField email=new JTextField("admin@omniroute.com");JPasswordField senha=new JPasswordField("123456");JButton entrar=new JButton("Entrar");
  JPanel p=new JPanel(new GridLayout(3,2,8,8));p.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));p.add(new JLabel("E-mail"));p.add(email);p.add(new JLabel("Senha"));p.add(senha);p.add(new JLabel(""));p.add(entrar);add(p);
  entrar.addActionListener(a->{try{if(new UsuarioDAO().login(email.getText(),new String(senha.getPassword()))){dispose();new MenuView().setVisible(true);}else JOptionPane.showMessageDialog(this,"Login inválido.");}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}});
 }
}
