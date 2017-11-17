package br.com.fogliato.core.enums;

public enum TipoAtividade {
	
	DESENVOLVIMENTO("D"),
	ATENDIMENTO("A"),
	MANUTENCAO("M"),
	MANUTENCAO_URGENTE("U");
	
	private String value;
	
    private TipoAtividade(String value) {
        this.value = value;
    }

    public static TipoAtividade fromType(String type) {
        for (TipoAtividade tipoAtividade : TipoAtividade.values()) {
            if(type.equalsIgnoreCase(tipoAtividade.getValue())) {
                return tipoAtividade;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }
	
}
