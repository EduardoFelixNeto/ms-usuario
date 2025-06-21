package br.com.ufpr.tads.dac.msusuario.dto;

import java.util.List;

public class PacienteDashboardDTO {
    private int saldoPontos;
    private List<AgendamentoDTO> agendamentos;

    // getters e setters

    public int getSaldoPontos() {
        return saldoPontos;
    }

    public void setSaldoPontos(int saldoPontos) {
        this.saldoPontos = saldoPontos;
    }

    public List<AgendamentoDTO> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoDTO> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
