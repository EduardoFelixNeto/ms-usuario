package br.com.ufpr.tads.dac.msusuario.service;

import br.com.ufpr.tads.dac.msusuario.dto.*;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO criar(UsuarioDTO dto);
    UsuarioDTO atualizar(Long id, UsuarioDTO dto);
    UsuarioDTO buscar(Long id);
    void comprarPontos(Long id, CompraPontosDTO dto);
    List<TransacaoPontosDTO> extrato(Long id);
    SaldoDTO saldo(Long id);
    PacienteDashboardDTO montarDashboard(String email);

    void debitarPontos(Long id, DebitoPontosDTO dto);
}

