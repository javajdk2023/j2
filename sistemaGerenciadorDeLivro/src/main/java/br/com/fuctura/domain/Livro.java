package br.com.fuctura.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_livro", schema = "fuctura")
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String titulo;
	private int qtdPagina;
}
