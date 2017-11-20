package br.com.fogliato.view.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.fogliato.core.domain.AtividadeDto;
import br.com.fogliato.core.enums.TipoAtividade;
import br.com.fogliato.core.rest.client.atividade.AtividadeRestClient;
import br.com.fogliato.view.utils.BundleUtils;
import br.com.fogliato.view.utils.JsfUtils;

@ManagedBean(name = "gerenciarAtividades")
@ViewScoped
public class GerenciarAtividadesBack implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String OPCAO_FILTRO_EM_ABERTO = "A";
	private static final String OPCAO_FILTRO_CONCLUIDAS = "C";
	
	private AtividadeRestClient atividadeRestClient;
	private AtividadeDto atividade;
	private List<AtividadeDto> atividades;
	private boolean modoCadastro;
	private String filtro;
	
	@PostConstruct
	public void inicializar() {
		this.atividadeRestClient = new AtividadeRestClient();
		this.atividade = new AtividadeDto();
		this.atividades = new ArrayList<>();
		this.modoCadastro = false;
		this.filtro = OPCAO_FILTRO_EM_ABERTO;
	}
		
	public void listarAtividades() {		
		try {
			if (OPCAO_FILTRO_EM_ABERTO.equals(filtro))
				atividades = atividadeRestClient.listarAtividadesEmAberto();			
			else 
				atividades = atividadeRestClient.listarAtividadesConcluidas();			
		} catch (Exception e) {
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_ERROR, "Não foi possível listar as atividades. Erro: " + e.getMessage());
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	public void salvar() {
		try {
			if (atividade.getIdAtividade() != null && atividade.getIdAtividade() > 0) 
				atividadeRestClient.alterarAtividade(atividade);
			else
				atividade = atividadeRestClient.criarAtividade(atividade);				
			
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_INFO, "Atividade salva com sucesso!");
			ativarModoPesquisa();
			listarAtividades();			
		} catch (Exception e) {
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar a atividade. " + e.getMessage());
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	public void alterar(AtividadeDto atividade) {
		ativarModoCadastro();
		this.atividade = atividade;
	}
	
	public void remover(AtividadeDto atividade) {
		try {
			atividadeRestClient.removerAtividade(atividade);
			listarAtividades();
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_INFO, "Atividade removida com sucesso!");
		} catch (Exception e) {
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_ERROR, "Não foi possível remover a atividade. " + e.getMessage());
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	public void selecionar(AtividadeDto atividade) {
		this.atividade = atividade;
	}
	
	public void concluir() {
		try {
			atividadeRestClient.concluirAtividade(atividade);
			listarAtividades();
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_INFO, "Atividade concluída com sucesso!");
		} catch (Exception e) {
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_ERROR, "Não foi possível concluir a atividade. " + e.getMessage());
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	public void reabrir(AtividadeDto atividade) {
		try {
			this.atividade = atividadeRestClient.reabrirAtividade(atividade);
			listarAtividades();	
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_INFO, "Atividade reaberta com sucesso!");
		} catch (Exception e) {
			JsfUtils.exibirMensagem(FacesMessage.SEVERITY_ERROR, "Não foi possível reabrir a atividade. " + e.getMessage());
			FacesContext.getCurrentInstance().validationFailed();
		}
	}
	
	public void ativarModoCadastro() {
		this.modoCadastro = true;
		this.atividade = new AtividadeDto();
		this.atividade.setTipoAtividade(TipoAtividade.DESENVOLVIMENTO);
	}
	
	public void ativarModoPesquisa() {
		this.modoCadastro = false;
		this.atividades.clear();
	}
	
	public List<TipoAtividade> getTiposAtividades() {
		return Arrays.asList(TipoAtividade.values());
	}

	public AtividadeDto getAtividade() {
		return atividade;
	}

	public void setAtividade(AtividadeDto atividade) {
		this.atividade = atividade;
	}

	public List<AtividadeDto> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<AtividadeDto> atividades) {
		this.atividades = atividades;
	}

	public boolean isModoCadastro() {
		return modoCadastro;
	}
	
    public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public String getDescricaoEnum(Enum<?> enumObject) {
        return new BundleUtils().getDescricaoEnum(enumObject);
    }
	
    public String getOpcaoFiltroEmAberto() {
    	return OPCAO_FILTRO_EM_ABERTO;
    }
    
    public String getOpcaoFiltroConcluidas() {
    	return OPCAO_FILTRO_CONCLUIDAS;
    }
    
    public boolean isPermitidoExcluir(AtividadeDto atividade) {
    	return TipoAtividade.MANUTENCAO_URGENTE != atividade.getTipoAtividade();
    }
    
    public boolean isConcluida(AtividadeDto atividade) {
    	return atividade.getDataConclusao() != null;
    }
}
