package Tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class MysqlConnect {
	Connection conn = null;
	
	public MysqlConnect connect()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/Assistan","root","");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
			return null;
		}
		
		return this;
	}
		
	public boolean Exist(String id) {
		boolean existe =false;
		String sql = "SELECT CASE WHEN COUNT(*)=1 then 'true' else 'false' END AS resultado FROM puntos WHERE id = ?";
		try {
			
			PreparedStatement stm = this.conn.prepareStatement(sql);
			
			stm.setString(1, id);
			
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				existe = rs.getBoolean(1);
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return existe;
	}
	
	public User getDataUser(String id){
		
		User data = new User();
		
		String sql="SELECT * FROM puntos WHERE id = ? limit 1";
		
		try {
			PreparedStatement stm = this.conn.prepareStatement(sql);
			
			stm.setString(1, id);
			
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				data.setId(rs.getString("id"));
				data.setIdiom(rs.getString("idioma"));
				data.setName(rs.getString("nombre"));
				data.setLastName(rs.getString("apellido_paterno"));
				data.setSecondLastname(rs.getString("apellido_materno"));
				data.setPoints(rs.getInt("puntos"));
				return data;
			}
			return null;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	
	public boolean updateUserPoints(User user) {
		
		String sql = "UPDATE `puntos` SET `puntos`= ? WHERE `id` = ?";
		try {
			
			PreparedStatement stm = this.conn.prepareStatement(sql);
			
			stm.setInt(1, user.getPoints());
			stm.setString(2, user.getId());
			
			stm.executeUpdate();
			
			return true;
			
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null,"Error: "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}
	
	public void disconnect()
	{
		if(this.conn != null) {try {this.conn.close();} catch (SQLException e) {}}
	}
	
	
	
}
