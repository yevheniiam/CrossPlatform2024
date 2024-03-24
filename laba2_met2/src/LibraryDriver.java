import java.io.*;
import java.util.*;

class Human implements Serializable {
    private String firstName;
    private String lastName;

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName;
    }
}

class Author extends Human implements Serializable {
    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }
}

class Book implements Serializable {
    private String title;
    private transient List<Author> authors;
    private int publicationYear;
    private int editionNumber;

    public Book(String title, List<Author> authors, int publicationYear, int editionNumber) {
        this.title = title;
        this.authors = authors;
        this.publicationYear = publicationYear;
        this.editionNumber = editionNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(authors.size());
        for (Author author : authors) {
            out.writeObject(author);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int authorSize = in.readInt();
        authors = new ArrayList<>();
        for (int i = 0; i < authorSize; i++) {
            authors.add((Author) in.readObject());
        }
    }

    @Override
    public String toString() {
        StringBuilder authorsStr = new StringBuilder();
        for (Author author : authors) {
            authorsStr.append(author.toString()).append(", ");
        }
        return "Title: " + title + ", Authors: " + authorsStr.toString() + ", Year: " + publicationYear + ", Edition: " + editionNumber;
    }
}

class BookStore implements Serializable {
    private String name;
    private List<Book> books;

    public BookStore(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    @Override
    public String toString() {
        return "BookStore: " + name + ", Books: " + books.size();
    }
}

class BookReader extends Human implements Serializable {
    private int registrationNumber;
    private List<Book> borrowedBooks;

    public BookReader(String firstName, String lastName, int registrationNumber) {
        super(firstName, lastName);
        this.registrationNumber = registrationNumber;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return super.toString() + ", Registration Number: " + registrationNumber + ", Borrowed Books: " + borrowedBooks.size();
    }
}

class Library implements Serializable {
    private String name;
    private transient List<BookStore> bookStores;
    private transient List<BookReader> readers;

    public Library(String name) {
        this.name = name;
        this.bookStores = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookStore> getBookStores() {
        return bookStores;
    }

    public void setBookStores(List<BookStore> bookStores) {
        this.bookStores = bookStores;
    }

    public List<BookReader> getReaders() {
        return readers;
    }

    public void setReaders(List<BookReader> readers) {
        this.readers= readers;
    }

    public void addBookStore(BookStore bookStore) {
        bookStores.add(bookStore);
    }

    public void removeBookStore(BookStore bookStore) {
        bookStores.remove(bookStore);
    }

    public void addReader(BookReader reader) {
        readers.add(reader);
    }

    public void removeReader(BookReader reader) {
        readers.remove(reader);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(bookStores.size());
        for (BookStore store : bookStores) {
            out.writeObject(store);
        }
        out.writeInt(readers.size());
        for (BookReader reader : readers) {
            out.writeObject(reader);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int storeSize = in.readInt();
        bookStores = new ArrayList<>();
        for (int i = 0; i < storeSize; i++) {
            bookStores.add((BookStore) in.readObject());
        }
        int readerSize = in.readInt();
        readers = new ArrayList<>();
        for (int i = 0; i < readerSize; i++) {
            readers.add((BookReader) in.readObject());
        }
    }

    @Override
    public String toString() {
        return "Library: " + name + ", BookStores: " + bookStores.size() + ", Readers: " + readers.size();
    }
}

public class LibraryDriver {
    public static void serializeLibrary(Library library, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(library);
            System.out.println("Library serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Library deserializeLibrary(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Library library = (Library) ois.readObject();
            System.out.println("Library deserialized successfully.");
            return library;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Creating and populating a library
        Library library = new Library("Central Library");
        BookStore fictionSection = new BookStore("Fiction Section");
        BookStore nonFictionSection = new BookStore("Non-Fiction Section");
        library.addBookStore(fictionSection);
        library.addBookStore(nonFictionSection);

        Author author1 = new Author("John", "Doe");
        Author author2 = new Author("Jane", "Smith");
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        Book book1 = new Book("Sample Fiction Book", authors, 2022, 1);
        Book book2 = new Book("Sample Non-Fiction Book", authors, 2023, 2);
        fictionSection.addBook(book1);
        nonFictionSection.addBook(book2);

        BookReader reader1 = new BookReader("Alice", "Johnson", 1001);
        BookReader reader2 = new BookReader("Bob", "Williams", 1002);
        library.addReader(reader1);
        library.addReader(reader2);

        // Serializing and deserializing the library
        serializeLibrary(library, "library.ser");
        Library deserializedLibrary = deserializeLibrary("library.ser");
        System.out.println("Deserialized Library: " + deserializedLibrary.toString());
    }
}

