package br.senac.pi.exemplows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuarioDAO {
	
	public boolean inserirUsuario(Usuario usuario) {
		try {
			Connection conn = ConexaoDB.conectar();
			String sqlInsert = "INSERT INTO usuario VALUES (null, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);
			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setInt(2, usuario.getIdade());
			preparedStatement.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean atualizarUsuario(Usuario usuario) {
		try {
			Connection conn = ConexaoDB.conectar();
			String sqlUpdate = "UPDATE usuario SET nome = ?, idade = ? WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlUpdate);
			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setInt(2, usuario.getIdade());
			preparedStatement.setInt(3, usuario.getId());
			preparedStatement.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean excluirUsuario(Usuario usuario) {
		try {
			Connection conn = ConexaoDB.conectar();
			String sqlDelete = "DELETE FROM usuario WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlDelete);
			preparedStatement.setInt(1, usuario.getId());
			preparedStatement.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean excluirUsuario(int id) {
		return excluirUsuario(new Usuario(id, "", 0));
	}
	
	public ArrayList<Usuario> listarTodosUsuarios() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		try {
			Connection conn = ConexaoDB.conectar();
			String sqlAll = "SELECT * FROM usuario";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlAll);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(result.getInt("id"));
				usuario.setNome(result.getString("nome"));
				usuario.setIdade(result.getInt("idade"));
				lista.add(usuario);
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Usuario buscarPorID(int id) {
		Usuario usuario = null;
		try {
			Connection conn = ConexaoDB.conectar();
			String sqlUser = "SELECT * FROM usuario WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlUser);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				usuario = new Usuario();
				usuario.setId(result.getInt(1));
				usuario.setNome(result.getString(2));
				usuario.setIdade(result.getInt(3));
			} else {
				return usuario;
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

}
