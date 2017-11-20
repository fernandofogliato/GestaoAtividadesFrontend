package br.com.fogliato.core.rest.client.atividade;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.google.gson.Gson;

import br.com.fogliato.core.domain.AtividadeDto;
import br.com.fogliato.core.domain.MensagemDto;
import br.com.fogliato.core.exception.AtividadeException;
import br.com.fogliato.core.rest.client.gson.GsonMessageBodyHandler;

/**
 * 
 * Classe responsável por consumir operações do GestaoAtividadesBackend, utiliza o {@link Gson} 
 * como provider padrão para transformações objeto->JSON e JSON->objeto
 * 
 * @author Fernando Fogliato  
 */
public class AtividadeRestClient implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// A URL poderia constar num arquivo .properties para poder facilitar a atualização e troca entre ambientes
	private static final String URL_SERVICO = "http://localhost:8080/GestaoAtividadesBackend/resources/";
	
	private ResteasyClient client;
	private ResteasyWebTarget target;
	private AtividadeResource atividadeResource;
	
	public AtividadeRestClient() {
        this.client = new ResteasyClientBuilder().build();
        this.target = client.target(URL_SERVICO);
        this.atividadeResource = target.proxy(AtividadeResource.class);
        
        // Registra o Gson para ser utilizado no lugar do Jackson
        this.client.register(GsonMessageBodyHandler.class);
	}

	public AtividadeDto buscarAtividadePor(Long id) throws AtividadeException  {
		
        Response resposta = atividadeResource.getAtividadeById(id);
        try {
        	verificarStatus(resposta);
        	return resposta.readEntity(AtividadeDto.class);
        } finally {
        	resposta.close();
        }
	}
	
	public AtividadeDto criarAtividade(AtividadeDto atividade) throws AtividadeException  {
		
		Response resposta = atividadeResource.criarAtividade(atividade);
		try {
			verificarStatus(resposta);	
			return resposta.readEntity(AtividadeDto.class);			
		} finally {
			resposta.close();
		}
	}
	
	public AtividadeDto alterarAtividade(AtividadeDto atividade) throws AtividadeException  {
		
		Response resposta = atividadeResource.alterarAtividade(atividade);
		try {
			verificarStatus(resposta);	
			return resposta.readEntity(AtividadeDto.class);	
		} finally {
			resposta.close();
		}
	}
	
	public void removerAtividade(AtividadeDto atividade) throws AtividadeException  {
		
		Response resposta = atividadeResource.removerAtividade(atividade);
		try {
			verificarStatus(resposta);
		} finally {
			resposta.close();
		}
	}
	
	public AtividadeDto concluirAtividade(AtividadeDto atividade) throws AtividadeException {
		
		Response resposta = atividadeResource.concluirAtividade(atividade);
		try {
			verificarStatus(resposta);
			return resposta.readEntity(AtividadeDto.class);
		} finally {
			resposta.close();
		}
	}
	
	public AtividadeDto reabrirAtividade(AtividadeDto atividade) throws AtividadeException  {
		
		Response resposta = atividadeResource.reabrirAtividade(atividade.getIdAtividade());
		try {
			verificarStatus(resposta);
			return resposta.readEntity(AtividadeDto.class);
		} finally {
			resposta.close();
		}
	}
	
	public List<AtividadeDto> listarAtividadesEmAberto() throws AtividadeException  {		
		
		Response resposta = atividadeResource.getAtividadesEmAberto();
		try {
			verificarStatus(resposta);
			return resposta.readEntity(new GenericType<List<AtividadeDto>>(){});
		} finally {
			resposta.close();
		}
	}
	
	public List<AtividadeDto> listarAtividadesConcluidas() throws AtividadeException  {
		
		Response resposta = atividadeResource.getAtividadesConcluidas();
		try {
			verificarStatus(resposta);	
			return resposta.readEntity(new GenericType<List<AtividadeDto>>(){});
		} finally {
			resposta.close();
		}
	}
	
	private void verificarStatus(Response resposta) throws AtividadeException {
		
		if (resposta.getStatus() == 500) {
			MensagemDto mensagem = resposta.readEntity(MensagemDto.class);
			throw new AtividadeException(mensagem.getMensagem());
		}
	}
		
}
