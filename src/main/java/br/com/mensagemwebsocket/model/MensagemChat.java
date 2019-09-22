package br.com.mensagemwebsocket.model;

public class MensagemChat {
	private String idSession;
	private String idPushContato;
	private String idPushRemetente;
	private String nomeRemetente;
	private String texto;

	public String getIdSession() {
		return this.idSession;
	}

	public void setIdSession(final String idSession) {
		this.idSession = idSession;
	}

	public String getIdPushContato() {
		return this.idPushContato;
	}

	public void setIdPushContato(final String idPushContato) {
		this.idPushContato = idPushContato;
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
		return this.texto;
	}

	public void setTexto(final String texto) {
		this.texto = texto;
	}

}
