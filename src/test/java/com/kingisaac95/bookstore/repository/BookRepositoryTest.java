package com.kingisaac95.bookstore.repository;

import com.kingisaac95.bookstore.model.Book;
import com.kingisaac95.bookstore.model.Language;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {
  @Inject
  private BookRepository bookRepository;

  @Test
  public void create() throws Exception {
    // Test book count
    assertEquals(Long.valueOf(0), bookRepository.countAll());
    assertEquals(0, bookRepository.findAll().size());

    // Test create book
    Book book = new Book("book title", "description", 12F, "isbn", new Date(), 120, "http://baaboo.com", Language.ENGLISH);
    bookRepository.create(book);
    Long bookId = book.getId();

    // Assert that book has been created
    assertNotNull(bookId);

    // Find created book
    Book bookFound = bookRepository.find(bookId);

    // Validate book data
    assertEquals("book title", bookFound.getTitle());

    // Test book count
    assertEquals(Long.valueOf(1), bookRepository.countAll());
    assertEquals(1, bookRepository.findAll().size());

    // Test delete book
    bookRepository.delete(bookId);

    // Test book count
    assertEquals(Long.valueOf(0), bookRepository.countAll());
    assertEquals(0, bookRepository.findAll().size());
  }

  @Deployment
  public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
            .addClass(BookRepository.class)
            .addClass(Book.class)
            .addClass(Language.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
  }
}
