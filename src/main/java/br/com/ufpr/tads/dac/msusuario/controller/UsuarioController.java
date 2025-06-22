package br.com.ufpr.tads.dac.msusuario.controller;

import br.com.ufpr.tads.dac.msusuario.dto.*;
import br.com.ufpr.tads.dac.msusuario.entity.Usuario;
import br.com.ufpr.tads.dac.msusuario.repository.UsuarioRepository;
import br.com.ufpr.tads.dac.msusuario.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscar(id));
    }

    @PostMapping("/{id}/comprar-pontos")
    public ResponseEntity<Void> comprarPontos(@PathVariable Long id, @RequestBody CompraPontosDTO dto) {
        usuarioService.comprarPontos(id, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<TransacaoPontosDTO>> extrato(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.extrato(id));
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<SaldoDTO> saldo(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.saldo(id));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<PacienteDashboardDTO> dashboard() {
        String email = getEmailFromToken(); // usa o método acima
        PacienteDashboardDTO dashboard = usuarioService.montarDashboard(email);
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/buscar-id")
    public ResponseEntity<Long> buscarIdPorEmail(@RequestParam String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        return ResponseEntity.ok(usuario.getId());
    }

    public String getEmailFromToken() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        }
        throw new RuntimeException("Token JWT inválido");
    }

    @PostMapping("/{id}/debitar-pontos")
    public ResponseEntity<Void> debitarPontos(@PathVariable Long id, @RequestBody DebitoPontosDTO dto) {
        usuarioService.debitarPontos(id, dto);
        return ResponseEntity.ok().build();
    }


}
