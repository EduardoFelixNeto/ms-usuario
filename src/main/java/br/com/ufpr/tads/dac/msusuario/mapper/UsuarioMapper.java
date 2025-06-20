package br.com.ufpr.tads.dac.msusuarios.mapper;

import br.com.ufpr.tads.dac.msusuarios.dto.UsuarioDTO;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.id = entity.getId();
        dto.nome = entity.getNome();
        dto.cpf = entity.getCpf();
        dto.email = entity.getEmail();
        dto.cep = entity.getCep();
        dto.endereco = entity.getEndereco();
        dto.pontos = entity.getPontos();
        dto.tipo = entity.getTipo().name();
        return dto;
    }
}
