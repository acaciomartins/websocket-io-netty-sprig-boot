package br.com.mensagemwebsocket.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.mensagemwebsocket.model.MensagemChat;

@Service
public class PushService {

	@Autowired
	public PushService push;

	@Value("${onesignal.app-id}")
	private String appId;

	@Value("${onesignal.api-key}")
	private String appKey;

	public void push(final MensagemChat mensagemChat) {
		try {
			String jsonResponse;

			URL url = new URL("https://onesignal.com/api/v1/notifications");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);

			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestMethod("POST");

//			String strJsonBody = "{" + "\"app_id\": \"4d77d6a0-b6a4-4e6d-a4ee-7b25400d055b\","
//					+ "\"include_player_ids\": [\"918c364e-ade4-4781-be59-ad8daa6a00a2\"],"
//					+ "\"data\": {\"foo\": \"bar\"}," 
//					+   "\"headings\": {\"en\": \"Titulo\"},"
//					+ "\"contents\": {\"en\": \"Mensagem\"}" + "}";

			String strJsonBody = "{" + "\"app_id\": \"" + appId + "\"," + "\"include_player_ids\": [\""
					+ mensagemChat.getIdPushDestinatario() + "\"]," + "\"data\": {\"idPushRemetente\": \""
					+ mensagemChat.getIdPushRemetente() + "\"}," + "\"headings\": {\"en\": \""
					+ mensagemChat.getNomeRemetente() + "\"}," + "\"contents\": {\"en\": \"" + mensagemChat.getTexto()
					+ "\"}" + "}";

			System.out.println("strJsonBody:\n" + strJsonBody);

			byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			con.setFixedLengthStreamingMode(sendBytes.length);

			OutputStream outputStream = con.getOutputStream();
			outputStream.write(sendBytes);

			int httpResponse = con.getResponseCode();
			System.out.println("httpResponse: " + httpResponse);

			if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
				Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
			} else {
				Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
				jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
				scanner.close();
			}
			System.out.println("jsonResponse:\n" + jsonResponse);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void main(String[] args) {
		final PushService push = new PushService();
		final MensagemChat mensagemChat = new MensagemChat();
		mensagemChat.setNomeRemetente("Acácio");
		mensagemChat.setIdPushDestinatario("4d77d6a0-b6a4-4e6d-a4ee-7b25400d055b");
		mensagemChat.setTexto("Texto mensagem teste");

		push.push(mensagemChat);
	}
}
