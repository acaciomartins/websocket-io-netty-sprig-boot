package br.com.mensagemwebsocket.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.mensagemwebsocket.model.UsuarioOnline;

@Repository
public interface UsuarioOnlineRepository extends CrudRepository<UsuarioOnline, Long> {

	@Transactional
	Long deleteByIdWebSocket(final String idWebSocket);

	UsuarioOnline findByIdPush(final String idPush);

}
