package br.com.fogliato.core.rest.client.atividade;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fogliato.core.domain.AtividadeDto;

/**
 * Interface responsável por mapear as operações e parâmetros do serviço "Atividade". 
 * É utilizada pelo {@link AtividadeRestClient} para poder invocar as operações do backend passando os parâmetros de acordo.
 * @author Fernando Fogliato
 */
@Path("/atividade")
public interface AtividadeResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response criarAtividade(AtividadeDto atividadeDto);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarAtividade(AtividadeDto atividadeDto);
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerAtividade(AtividadeDto atividadeDto);

    @Path("/concluir")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response concluirAtividade(AtividadeDto atividadeDto);
    
    @Path("/{id}/reabrir")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reabrirAtividade(@PathParam("id") final Long idAtividade);

    @Path("/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtividadeById(@PathParam("id") final Long idAtividade);

    @Path("/abertas")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtividadesEmAberto();
    
    @Path("/concluidas")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAtividadesConcluidas();

}
