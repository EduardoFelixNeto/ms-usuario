package br.com.ufpr.tads.dac.msusuario.client;

import br.com.ufpr.tads.dac.msusuario.dto.AgendamentoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ms-consulta", url = "${msconsulta.url}") // ex: http://localhost:8082
public interface ConsultaClient {

    @GetMapping("/agendamentos/por-paciente")
    List<AgendamentoDTO> listarAgendamentosPorPaciente(@RequestParam Long pacienteId);
}
