package br.com.fogliato.view.utils;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JsfUtils implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static void exibirMensagem(FacesMessage.Severity severidade, String msg) {
		
		FacesMessage mensagem = new FacesMessage(msg);
		mensagem.setSeverity(severidade);
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
	}
	
}