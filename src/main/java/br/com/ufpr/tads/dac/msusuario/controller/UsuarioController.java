package br.com.ufpr.tads.dac.msusuarios.controller;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
}
