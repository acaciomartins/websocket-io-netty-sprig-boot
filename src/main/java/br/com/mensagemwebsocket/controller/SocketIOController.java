package br.com.mensagemwebsocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import br.com.mensagemwebsocket.model.MensagemChat;
import br.com.mensagemwebsocket.model.MensagemOnline;
import br.com.mensagemwebsocket.model.UsuarioOnline;
import br.com.mensagemwebsocket.repository.UsuarioOnlineRepository;
import br.com.mensagemwebsocket.service.PushService;

@Component
public class SocketIOController {

	Logger LOGGER = LoggerFactory.getLogger(SocketIOController.class);

	@Autowired
	private UsuarioOnlineRepository usuarioOnlineRepository;

	@Autowired
	private PushService pushService;
	
	private SocketIONamespace namespace;

	public SocketIONamespace getNamespace() {
		return this.namespace;
	}

	@Autowired
	public SocketIOController(final SocketIOServer server) {
		this.namespace = server.addNamespace("");
		this.namespace.addConnectListener(onConnectListener);
		this.namespace.addDisconnectListener(onDisconnectListener);
		this.namespace.addEventListener("online", MensagemOnline.class, onUsuarioOnline);
		this.namespace.addEventListener("mensagem", MensagemChat.class, onMensagemEnviada);
	}

	public ConnectListener onConnectListener = new ConnectListener() {
		@Override
		public void onConnect(SocketIOClient client) {
			LOGGER.info("Usuario " + client.getSessionId() + " online.");
		}
	};

	public DisconnectListener onDisconnectListener = new DisconnectListener() {
		@Override
		public void onDisconnect(SocketIOClient client) {
			usuarioOnlineRepository.deleteByIdWebSocket(client.getSessionId().toString());
		}
	};

	public DataListener<MensagemOnline> onUsuarioOnline = new DataListener<MensagemOnline>() {
		@Override
		public void onData(SocketIOClient client, MensagemOnline mensagemOnLine, AckRequest ack) throws Exception {
			LOGGER.info("Mensagem enviada OnLine: [IdPush: " + mensagemOnLine.getIdPush() + "] [IdSocket: "
					+ mensagemOnLine.getIdSocket() + "]");

			// Salva na tabela UsuarioOnline o idPush e o idWebSocket
			final UsuarioOnline usuarioOnline = new UsuarioOnline();
			usuarioOnline.setIdPush(mensagemOnLine.getIdPush());
			usuarioOnline.setIdWebSocket(client.getSessionId().toString());
			if (usuarioOnlineRepository.findByIdPush(mensagemOnLine.getIdPush()) == null) {
				usuarioOnlineRepository.save(usuarioOnline);
			}
//			namespace.getBroadcastOperations().sendEvent("online", client, mensagemOnLine);
		}
	};

	public DataListener<MensagemChat> onMensagemEnviada = new DataListener<MensagemChat>() {

		@Override
		public void onData(SocketIOClient client, MensagemChat mensagemChat, AckRequest ack) throws Exception {
			LOGGER.debug("Mensagem enviada [ " + mensagemChat.getTexto() + " ]");
			LOGGER.debug("id push contato: " + mensagemChat.getIdPushContato());
			LOGGER.debug("response: " + mensagemChat);
			
			// Consulta Usuario (contato) Online
			final UsuarioOnline usuarioOnline = usuarioOnlineRepository
					.findByIdPush(mensagemChat.getIdPushContato());
			
			if (null != usuarioOnline) {
				namespace.getBroadcastOperations().sendEvent(mensagemChat.getIdPushContato() + "/msg",
						mensagemChat);
			} else {
				pushService.push(mensagemChat);
				System.out.println("Mensagem enviada ao PUSH");
			}
		}
	};

	public void broadcastEvent(String event, Object data) {
		this.getNamespace().getBroadcastOperations().sendEvent(event, data);
	}

	public void stopServer() {
		this.stopServer();
	}
}
