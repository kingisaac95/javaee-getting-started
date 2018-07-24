package com.kingisaac95.bookstore.repository;

import com.kingisaac95.bookstore.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

/**
 * SUPPORTS - best for read-only transactions
 * if invoked outside a transaction, a new transaction will not be created
 * but if invoked within a transaction, it'll be executed within the context of
 * that transaction to allow database isolation
 * REQUIRED - best for write-only transactions
 */

@Transactional(SUPPORTS)
public class BookRepository {

  // inject persistence unit
  @PersistenceContext(unitName = "bookStorePU")
  private EntityManager em;

  public Book find(Long id) {
    return em.find(Book.class, id);
  }

  public List<Book> findAll() {
    TypedQuery<Book> query = em.createQuery("select b from Book b order by b.title", Book.class);
    return query.getResultList();
  }

  public Long countAll() {
    TypedQuery<Long> query = em.createQuery("select count(b) from Book b", Long.class);
    return query.getSingleResult();
  }

  @Transactional(REQUIRED)
  public Book create(Book book) {
    em.persist(book);
    return book;
  }

  @Transactional(REQUIRED)
  public void delete(Long id) {
    em.remove(em.getReference(Book.class, id));
  }
}
