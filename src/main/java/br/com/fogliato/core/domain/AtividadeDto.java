package br.com.fogliato.core.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.com.fogliato.core.enums.TipoAtividade;

public class AtividadeDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long idAtividade;

    private String titulo;

    private String descricao;

    private TipoAtividade tipoAtividade;

    private LocalDateTime dataConclusao;

    public Long getIdAtividade() {
        return idAtividade;
    }

    public void setIdAtividade(Long idAtividade) {
        this.idAtividade = idAtividade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoAtividade getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(TipoAtividade tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAtividade == null) ? 0 : idAtividade.hashCode());
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
        AtividadeDto other = (AtividadeDto) obj;
        if (idAtividade == null) {
            if (other.idAtividade != null)
                return false;
        } else if (!idAtividade.equals(other.idAtividade))
            return false;
        return true;
    }

}
