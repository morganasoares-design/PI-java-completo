package br.com.omniroute.model.dao;
import br.com.omniroute.util.DB;import java.sql.*;
public class UsuarioDAO {
 public boolean login(String email,String senha)throws SQLException{
  try(Connection c=DB.get();PreparedStatement p=c.prepareStatement("SELECT id FROM Usuario WHERE email=? AND senha=?")){
   p.setString(1,email);p.setString(2,senha);try(ResultSet r=p.executeQuery()){return r.next();}
  }
 }
}
