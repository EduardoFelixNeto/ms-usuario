package br.com.ufpr.tads.dac.msusuario.dto;

public class DebitoPontosDTO {
    private int quantidade;
    private String descricao;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
