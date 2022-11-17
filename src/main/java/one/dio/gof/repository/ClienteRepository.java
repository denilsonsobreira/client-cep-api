package one.dio.gof.repository;

import one.dio.gof.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente,Long> {
}
