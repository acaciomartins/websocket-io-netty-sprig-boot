package br.com.mensagemwebsocket.model;

public class MensagemChat {
	private String idSession;
	private String idPushDestinatario;
	private String idPushRemetente;
	private String nomeRemetente;
	private String texto;

	public String getIdSession() {
		return idSession;
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

	public String getIdPushRemetente() {
		return this.idPushRemetente;
	}

	public void setIdPushRemetente(final String idPushRemetente) {
		this.idPushRemetente = idPushRemetente;
	}

	public String getNomeRemetente() {
		return this.nomeRemetente;
	}

	public void setNomeRemetente(final String nomeRemetente) {
		this.nomeRemetente = nomeRemetente;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
