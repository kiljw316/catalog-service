package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        // given
        var book = new Book("1234567890", "Title", "Author", 9.90);

        // when
        var jsonContent = json.write(book);

        // then
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
            .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
            .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
            .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
            .isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws IOException {
        // given
        var content = """
            {
              "isbn": "1234567890",
              "title": "Title",
              "author": "Author",
              "price": 9.90
            }
            """;

        // when
        ObjectContent<Book> book = json.parse(content);

        // then
        assertThat(book)
            .usingRecursiveComparison()
            .isEqualTo(new Book("1234567890", "Title", "Author", 9.90));
    }
}
