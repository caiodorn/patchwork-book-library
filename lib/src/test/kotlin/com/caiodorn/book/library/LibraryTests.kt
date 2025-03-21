package com.caiodorn.book.library

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainOnly
import io.kotest.matchers.shouldBe

// ### Context:
// I have many books which I would like to share with my community. That sounds like a book-lending library. Please write some software to help me do that.
class LibraryTests : FeatureSpec({
    var library: Library? = null

    beforeEach {
        library = Library(
            listOf(
                Book("Clean Code", "Robert C. Martin", "978-0132350884"),
                Book("God Emperor of Dune", "Frank Herbert", "978-0575075061"),
                Book("Java Performance", "Scott Oaks", "978-1492056119"),
                Book("Effective Java", "Joshua Bloch", "978-0321356680"),
                Book("Children of Dune", "Frank Herbert", "978-1473233782"),
                Book("The Voyage of the Beagle", "Charles Darwin", "978-1660289875"),
                Book("Java: The Complete Reference, Eleventh Edition", "Herbert Schildt", "978-1260440232", true),
            )
        )
    }

    // As a library user, I would like to be able to find books by my favourite author, so that I know if they are available in the library.
    // As a library user, I would like to be able to find books by title, so that I know if they are available in the library.
    // As a library user, I would like to be able to find books by ISBN, so that I know if they are available in the library.
    feature("find books") {
        scenario("by author") {
            library?.findBooksByAuthor("Frank Herbert") shouldContainOnly listOf(
                Book("Children of Dune", "Frank Herbert", "978-1473233782"),
                Book("God Emperor of Dune", "Frank Herbert", "978-0575075061")
            )
        }
        scenario("by author, none found") {
            library?.findBooksByAuthor("Caio Antunes").shouldBeEmpty()
        }
        scenario("by title") {
            library?.findBooksByTitle("Children of Dune") shouldContainOnly listOf(
                Book("Children of Dune", "Frank Herbert", "978-1473233782")
            )
        }
        scenario("by title, none found") {
            library?.findBooksByTitle("Design Patterns").shouldBeEmpty()
        }
        scenario("by ISBN") {
            library?.findBooksByIsbn("978-1492056119") shouldContainOnly listOf(
                Book("Java Performance", "Scott Oaks", "978-1492056119")
            )
        }
        scenario("by ISBN, none found") {
            library?.findBooksByIsbn("123-0123456789").shouldBeEmpty()
        }
    }

    // As a library user, I would like to be able to borrow a book, so I can read it at home.
    // As a library user, I should be to prevented from borrowing reference books, so that they are always available.
    feature("borrow book") {
        scenario("successfully given book is available") {
            shouldNotThrow<BookUnavailableException> {
                library?.borrowBook(Book("Children of Dune", "Frank Herbert", "978-1473233782"), User("John Snow"))
            }
        }
        scenario("throw exception if book not available") {
            shouldThrow<BookUnavailableException> {
                val book = Book("Children of Dune", "Frank Herbert", "978-1473233782")
                val user = User("John Snow")

                //given borrowed
                library?.borrowBook(book, user)

                //when
                library?.borrowBook(book, user)
            }.message shouldBe "This book is currently unavailable"
        }
        scenario("throw exception if reference book") {
            shouldThrow<BookUnavailableException> {
                library?.borrowBook(
                    Book("Java: The Complete Reference, Eleventh Edition", "Herbert Schildt", "978-1260440232", true),
                    User("John Snow")
                )
            }.message shouldBe "Reference books are not available for borrowing"
        }
    }

    // As the library owner, I would like to know how many books are being borrowed, so I can see how many are outstanding.
    feature("count books currently borrowed") {
        scenario("no outstanding borrowings") {
            library?.countBooksCurrentlyBorrowed() shouldBe 0
        }
        scenario("has outstanding borrowings") {
            library?.borrowBook(Book("Children of Dune", "Frank Herbert", "978-1473233782"), User("John Snow"))
            library?.borrowBook(Book("Java Performance", "Scott Oaks", "978-1492056119"), User("Joe Satriani"))

            library?.countBooksCurrentlyBorrowed() shouldBe 2
        }
    }
})
