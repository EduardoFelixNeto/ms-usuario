package br.com.ufpr.tads.dac.msusuario.service;

import br.com.ufpr.tads.dac.msusuario.dto.CompraPontosDTO;
import br.com.ufpr.tads.dac.msusuario.dto.SaldoDTO;
import br.com.ufpr.tads.dac.msusuario.dto.TransacaoPontosDTO;
import br.com.ufpr.tads.dac.msusuario.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO criar(UsuarioDTO dto);
    UsuarioDTO atualizar(Long id, UsuarioDTO dto);
    UsuarioDTO buscar(Long id);
    void comprarPontos(Long id, CompraPontosDTO dto);
    List<TransacaoPontosDTO> extrato(Long id);
    SaldoDTO saldo(Long id);
}

