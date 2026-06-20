package gerenciadorAcademico.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import gerenciadorAcademico.domain.Aluno;

public class AlunoDAO {

	public void cadastrar(Connection conn, Aluno aluno) throws IOException, SQLException {
		String comandoSQL = "INSERT INTO aluno (matricula, nome, idade, celular) VALUES (?, ?, ?, ?)";

		try (PreparedStatement pstm = conn.prepareStatement(comandoSQL)) {
			pstm.setString(1, aluno.getMatricula());
			pstm.setString(2, aluno.getNome());
			pstm.setInt(3, aluno.getIdade());
			pstm.setString(4, aluno.getCelular());

			pstm.execute();
		}
	}
}
