package br.com.ufpr.tads.dac.msusuario.service.impl;

import br.com.ufpr.tads.dac.msusuario.client.ConsultaClient;
import br.com.ufpr.tads.dac.msusuario.dto.*;
import br.com.ufpr.tads.dac.msusuario.entity.*;
import br.com.ufpr.tads.dac.msusuario.repository.TransacaoPontosRepository;
import br.com.ufpr.tads.dac.msusuario.repository.UsuarioRepository;
import br.com.ufpr.tads.dac.msusuario.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TransacaoPontosRepository transacaoRepository;
    private final ConsultaClient consultaClient;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, TransacaoPontosRepository transacaoRepository, ConsultaClient consultaClient) {
        this.usuarioRepository = usuarioRepository;
        this.transacaoRepository = transacaoRepository;
        this.consultaClient = consultaClient;
    }

    @Override
    public UsuarioDTO criar(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome);
        usuario.setCpf(dto.cpf);
        usuario.setEmail(dto.email);
        usuario.setCep(dto.cep);
        usuario.setEndereco(dto.endereco);
        usuario.setTipo(TipoUsuario.valueOf(dto.tipo));
        usuario.setPontos(dto.pontos);
        return toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO atualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setNome(dto.nome);
        usuario.setEmail(dto.email);
        usuario.setCep(dto.cep);
        usuario.setEndereco(dto.endereco);
        return toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioDTO buscar(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        return toDTO(usuario);
    }

    @Override
    public void comprarPontos(Long id, CompraPontosDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();

        int quantidade = dto.quantidadePontos;
        BigDecimal valorCalculado = BigDecimal.valueOf(quantidade).multiply(BigDecimal.valueOf(5));

        usuario.setPontos(usuario.getPontos() + quantidade);

        TransacaoPontos transacao = new TransacaoPontos();
        transacao.setUsuario(usuario);
        transacao.setTipo(TipoTransacao.ENTRADA);
        transacao.setDescricao("COMPRA DE PONTOS");
        transacao.setQuantidadePontos(quantidade);
        transacao.setValorReais(valorCalculado);
        transacao.setDataHora(LocalDateTime.now());

        usuarioRepository.save(usuario);
        transacaoRepository.save(transacao);
    }


    @Override
    public List<TransacaoPontosDTO> extrato(Long id) {
        return transacaoRepository.findByUsuarioIdOrderByDataHoraDesc(id).stream().map(t -> {
            TransacaoPontosDTO dto = new TransacaoPontosDTO();
            dto.tipo = t.getTipo().name();
            dto.dataHora = t.getDataHora();
            dto.descricao = t.getDescricao();
            dto.quantidadePontos = t.getQuantidadePontos();
            dto.valorReais = t.getValorReais();
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public SaldoDTO saldo(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        SaldoDTO saldo = new SaldoDTO();
        saldo.pontos = usuario.getPontos();
        saldo.historico = extrato(id);
        return saldo;
    }

    private UsuarioDTO toDTO(Usuario entity) {
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

    public PacienteDashboardDTO montarDashboard(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        int saldo = usuario.getPontos();

        List<AgendamentoDTO> agendamentos = consultaClient.listarAgendamentosPorPaciente(usuario.getId());

        PacienteDashboardDTO dto = new PacienteDashboardDTO();
        dto.setSaldoPontos(saldo);
        dto.setAgendamentos(agendamentos);

        return dto;
    }

    @Override
    @Transactional
    public void debitarPontos(Long idUsuario, DebitoPontosDTO dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getPontos() < dto.getQuantidade()) {
            throw new RuntimeException("Saldo insuficiente para débito de pontos.");
        }

        usuario.setPontos(usuario.getPontos() - dto.getQuantidade());
        usuarioRepository.save(usuario);

        var transacao = new TransacaoPontos();
        transacao.setUsuario(usuario);
        transacao.setQuantidadePontos(dto.getQuantidade());
        transacao.setDescricao(dto.getDescricao());
        transacao.setTipo(TipoTransacao.SAIDA);
        transacao.setDataHora(LocalDateTime.now());
        transacaoRepository.save(transacao);
    }


}
