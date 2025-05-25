package br.com.gestortarefas.controller;

import br.com.gestortarefas.dao.TarefaDao;
import br.com.gestortarefas.model.Tarefa;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class TarefaBean implements Serializable {

    private Tarefa tarefa = new Tarefa();
    private List<Tarefa> tarefas;

    // Filtros
    private Integer idFiltro;
    private String tituloOuDescricaoFiltro;
    private String responsavelFiltro;
    private Boolean concluidaFiltro;

    @Inject
    private TarefaDao tarefaDao;

    @PostConstruct
    public void init() {
        Object tarefaFlash = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("tarefaEditada");
        if (tarefaFlash instanceof Tarefa) {
            this.tarefa = (Tarefa) tarefaFlash;
        }
    }

    public String salvar() {
        try {
            tarefaDao.salvar(tarefa);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Tarefa salva com sucesso!"));
            tarefa = new Tarefa();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar: " + e.getMessage(), null));
        }
        return null;
    }
    private String prioridadeFiltro;
    private String situacaoFiltro;

    public String buscar() {
        try {
            // Chama a DAO com os filtros (podem estar null)
            tarefas = tarefaDao.listar(idFiltro, tituloOuDescricaoFiltro, responsavelFiltro, concluidaFiltro, prioridadeFiltro, situacaoFiltro);

            if (tarefas == null || tarefas.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Busca sem resultados!", null));
                tarefas = new ArrayList<>();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao buscar: " + e.getMessage(), null));
            tarefas = new ArrayList<>();
        }
        return null;
    }

    public String prepararEdicao(Tarefa tarefaSelecionada) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("tarefaEditada", tarefaSelecionada);
        return "cadastro?faces-redirect=true";
    }

    public String excluir(Tarefa tarefaSelecionada) {
        try {
            tarefaDao.excluir(tarefaSelecionada);
            buscar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Tarefa excluída com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir: " + e.getMessage(), null));
        }
        return null;
    }

    public String atualizar() {
        try {
            tarefaDao.atualizar(tarefa);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Tarefa atualizada com sucesso!"));
            tarefa = new Tarefa();
            return "lista?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao atualizar: " + e.getMessage(), null));
            return null;
        }
    }

    public String concluir(Tarefa tarefaSelecionada) {
        try {
            tarefaSelecionada.setConcluida(true);
            tarefaDao.atualizar(tarefaSelecionada);
            buscar();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Tarefa marcada como concluída!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao concluir: " + e.getMessage(), null));
        }
        return null;
    }
}
