package com.caiodorn.book.library

class Library(books: Collection<Book>?) {
    private val books: MutableCollection<Book> = books?.toMutableList() ?: mutableListOf()
    private val borrowings: MutableList<Borrowing> = mutableListOf()

    fun findBooksByAuthor(author: String): Collection<Book> {
        return books.filter { book -> book.author == author }.toList()
    }

    fun findBooksByTitle(title: String): Collection<Book> {
        return books.filter { book -> book.title == title }.toList()
    }

    fun findBooksByIsbn(isbn: String): Collection<Book> {
        return books.filter { book -> book.isbn == isbn }.toList()
    }

    fun borrowBook(book: Book, user: User) {
        if (book.isReference) {
            throw BookUnavailableException("Reference books are not available for borrowing")
        }

        if (borrowings.map { borrowing -> borrowing.book }.toList().contains(book)) {
            throw BookUnavailableException()
        }

        borrowings.add(Borrowing(book, user))
    }

    fun countBooksCurrentlyBorrowed(): Int {
        return borrowings.count()
    }
}
