package br.com.alurafood.pagamentos.service;

import java.util.Optional;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.http.PedidoClient;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    private final ModelMapper modelMapper;

    private final PedidoClient pedido;

    public PagamentoService(PagamentoRepository repository, ModelMapper modelMapper, PedidoClient pedido) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.pedido = pedido;
    }

    public Page<PagamentoDTO> obterTodos(Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO criarPagamento(PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public void excluirPagamento(Long id) {
        repository.deleteById(id);
    }

    public void confirmarPagamento(Long id){
        Optional<Pagamento> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new EntityNotFoundException();
        }

        Pagamento pagamento = optional.get();
        pagamento.setStatus(Status.CONFIRMADO);
        repository.save(pagamento);

        pedido.atualizaPagamento(optional.get().getPedidoId());
    }

    public void alteraStatus(Long id){
        Optional<Pagamento> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new EntityNotFoundException();
        }

        Pagamento pagamento = optional.get();
        pagamento.setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        repository.save(pagamento);
    }

}