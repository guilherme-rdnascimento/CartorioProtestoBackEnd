package br.edu.ifpe.pdsc_modelo.entidades;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "endereco", catalog = "CartorioProtesto")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "rua", nullable = false)
	private String rua;

	@Column(name = "numero", nullable = false)
	private int numero;
	
	@Column(name = "bairro", nullable = false)
	private String bairro;
	
	@Column(name = "cidade", nullable = false)
	private String cidade;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
}