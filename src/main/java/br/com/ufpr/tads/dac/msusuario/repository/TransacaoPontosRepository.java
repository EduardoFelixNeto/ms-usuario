package br.com.ufpr.tads.dac.msusuario.repository;

import br.com.ufpr.tads.dac.msusuario.entity.TransacaoPontos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransacaoPontosRepository extends JpaRepository<TransacaoPontos, Long> {
    List<TransacaoPontos> findByUsuarioIdOrderByDataHoraDesc(Long usuarioId);
}
