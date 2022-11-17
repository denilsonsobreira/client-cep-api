package one.dio.gof.repository;

import one.dio.gof.model.Endereco;
import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<Endereco,String> {
}
