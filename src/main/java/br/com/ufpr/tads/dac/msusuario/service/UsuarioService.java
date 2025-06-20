package br.com.ufpr.tads.dac.msusuarios.service;

import br.com.ufpr.tads.dac.msusuarios.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO criar(UsuarioDTO dto);
    UsuarioDTO atualizar(Long id, UsuarioDTO dto);
    UsuarioDTO buscar(Long id);
}
