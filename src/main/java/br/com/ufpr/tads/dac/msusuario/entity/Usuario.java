package br.com.ufpr.tads.dac.msusuario.entity;

import br.com.ufpr.tads.dac.msusuario.dto.TipoUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String cep;

    private String endereco;

    private int pontos;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public int getPontos() { return pontos; }
    public void setPontos(int pontos) { this.pontos = pontos; }

    public static class Builder {
        private final Usuario u = new Usuario();

        public Builder nome(String nome) { u.setNome(nome); return this; }
        public Builder cpf(String cpf) { u.setCpf(cpf); return this; }
        public Builder email(String email) { u.setEmail(email); return this; }
        public Builder tipo(TipoUsuario tipo) { u.setTipo(tipo); return this; }
        public Builder cep(String cep) { u.setCep(cep); return this; }
        public Builder endereco(String endereco) { u.setEndereco(endereco); return this; }
        public Builder pontos(int pontos) { u.setPontos(pontos); return this; }
        public Usuario build() { return u; }
    }

    public static Builder builder() {
        return new Builder();
    }
}

