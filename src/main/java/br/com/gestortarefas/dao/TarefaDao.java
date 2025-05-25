package br.com.gestortarefas.dao;

import br.com.gestortarefas.model.Tarefa;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
public class TarefaDao {

    @PersistenceContext
    private EntityManager em;
    private Object concluida;

    // Salva uma nova tarefa
    public void salvar(Tarefa tarefa) {
        em.persist(tarefa);
    }

    // Atualiza uma tarefa existente
    public void atualizar(Tarefa tarefa) {
        em.merge(tarefa);
    }

    // Exclui uma tarefa (corrigido para evitar erro com entidades "detached")
    public void excluir(Tarefa tarefa) {
        Tarefa tarefaGerenciada = em.merge(tarefa);
        em.remove(tarefaGerenciada);
    }

    // Lista tarefas com filtros dinâmicos
    public List<Tarefa> listar(Integer numero, String titulo, String responsavel, Boolean prioridade, String prioridadeFiltro, String situacaoFiltro) {
        StringBuilder jpql = new StringBuilder("SELECT t FROM Tarefa t WHERE 1=1");

        if (numero != null) {
            jpql.append(" AND t.numero = :numero");
        }
        if (titulo != null && !titulo.trim().isEmpty()) {
            jpql.append(" AND LOWER(t.titulo) LIKE LOWER(:titulo)");
        }
        if (responsavel != null && !responsavel.trim().isEmpty()) {
            jpql.append(" AND LOWER(t.responsavel) LIKE LOWER(:responsavel)");
        }
        if (prioridade != null) {
            jpql.append(" AND t.prioridade = :prioridade");
        }
        if (concluida != null) {
            jpql.append(" AND t.concluida = :concluida");
        }

        jpql.append(" ORDER BY t.concluida, t.responsavel, t.prioridade, t.deadline");

        TypedQuery<Tarefa> query = em.createQuery(jpql.toString(), Tarefa.class);

        if (numero != null) {
            query.setParameter("numero", numero);
        }
        if (titulo != null && !titulo.trim().isEmpty()) {
            query.setParameter("titulo", "%" + titulo + "%");
        }
        if (responsavel != null && !responsavel.trim().isEmpty()) {
            query.setParameter("responsavel", "%" + responsavel + "%");
        }
        if (prioridade != null) {
            query.setParameter("prioridade", prioridade);
        }
        if (concluida != null) {
            query.setParameter("concluida", concluida);
        }

        return query.getResultList();
    }
}
