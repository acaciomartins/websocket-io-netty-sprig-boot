package br.com.mensagemwebsocket.model;

public class MensagemChat {
	private String idSession;
	private String idPushDestinatario;
	private String texto;

	public String getIdSession() {
		return this.idSession;
	}

	public void setIdSession(final String idSession) {
		this.idSession = idSession;
	}

	public String getIdPushDestinatario() {
		return this.idPushDestinatario;
	}

	public void setIdPushDestinatario(final String idPushDestinatario) {
		this.idPushDestinatario = idPushDestinatario;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(final String texto) {
		this.texto = texto;
	}

}
