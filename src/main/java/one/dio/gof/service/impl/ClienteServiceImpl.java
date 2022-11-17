package one.dio.gof.service.impl;

import one.dio.gof.model.Cliente;
import one.dio.gof.model.Endereco;
import one.dio.gof.repository.ClienteRepository;
import one.dio.gof.repository.EnderecoRepository;
import one.dio.gof.service.ClienteService;
import one.dio.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;
    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorID(Long id) {
        if (clienteRepository.existsById(id)) {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            return cliente.get();
        }
        return null;
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCEP(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clientDB = clienteRepository.findById(id);
        if (clientDB.isPresent()) {
            salvarClienteComCEP(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCEP(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
