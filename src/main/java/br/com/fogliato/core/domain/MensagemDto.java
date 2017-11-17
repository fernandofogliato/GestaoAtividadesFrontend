package br.com.fogliato.core.domain;

import java.io.Serializable;

public class MensagemDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String mensagem;

    public MensagemDto(String mensagem) {
        this.mensagem = mensagem;
    }

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}