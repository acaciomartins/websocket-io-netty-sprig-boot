package br.com.mensagemwebsocket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuario_online")
public class UsuarioOnline {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "id_push")
	private String idPush;

	@Column(name = "id_web_socket")
	private String idWebSocket;

	public UsuarioOnline() {
		super();
	}

	public UsuarioOnline(@NotNull String idPush, @NotNull String idWebSocket) {
		super();
		this.idPush = idPush;
		this.idWebSocket = idWebSocket;
	}

	public String getIdPush() {
		return this.idPush;
	}

	public void setIdPush(final String idPush) {
		this.idPush = idPush;
	}

	public String getIdWebSocket() {
		return this.idWebSocket;
	}

	public void setIdWebSocket(final String idWebSocket) {
		this.idWebSocket = idWebSocket;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioOnline other = (UsuarioOnline) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
