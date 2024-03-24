import java.io.*;
import java.util.ArrayList;

// Клас Автор
class Author implements Serializable {
    private String name;
    private String surname;

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}

// Клас Книга
class Book implements Serializable {
    private String name;
    private ArrayList<Author> authors;
    private int year;
    private int editionNumber;

    public Book(String name, ArrayList<Author> authors, int year, int editionNumber) {
        this.name = name;
        this.authors = authors;
        this.year = year;
        this.editionNumber = editionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", authors=" + authors +
                ", year=" + year +
                ", editionNumber=" + editionNumber +
                '}';
    }
}

// Клас Книжкове сховище
class BookStore implements Serializable {
    private String name;
    private ArrayList<Book> books;

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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString() {
        return "BookStore{" +
                "name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}

// Клас Читач
class BookReader implements Serializable {
    private String name;
    private String surname;
    private int registrationNumber;
    private ArrayList<Book> borrowedBooks;

    public BookReader(String name, String surname, int registrationNumber) {
        this.name = name;
        this.surname = surname;
        this.registrationNumber = registrationNumber;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public String toString() {
        return "BookReader{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", registrationNumber=" + registrationNumber +
                ", borrowedBooks=" + borrowedBooks +
                '}';
    }
}

// Клас Бібліотека
class Library implements Serializable {
    private String name;
    private ArrayList<BookStore> bookStores;
    private ArrayList<BookReader> readers;

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

    public ArrayList<BookStore> getBookStores() {
        return bookStores;
    }

    public void setBookStores(ArrayList<BookStore> bookStores) {
        this.bookStores = bookStores;
    }

    public ArrayList<BookReader> getReaders() {
        return readers;
    }

    public void setReaders(ArrayList<BookReader> readers) {
        this.readers = readers;
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", bookStores=" + bookStores +
                ", readers=" + readers +
                '}';
    }
}

// Клас драйвера бібліотеки
class LibraryDriver {
    // Метод для серіалізації об'єкта
    public static void serializeObject(String fileName, Object obj) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(obj);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для десеріалізації об'єкта
    public static Object deSerializeObject(String fileName) {
        Object obj = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
            obj = is.readObject();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void main(String[] args) {
        // Створення об'єктів для тестування
        Author author1 = new Author("John", "Doe");
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(author1);
        Book book1 = new Book("Book 1", authors, 2022, 1);
        BookStore store1 = new BookStore("Store 1");
        store1.addBook(book1);
        Library library = new Library("Library 1");
        library.getBookStores().add(store1);

        // Серіалізація та десеріалізація
        serializeObject("library.ser", library);
        Library deserializedLibrary = (Library) deSerializeObject("library.ser");
        System.out.println(deserializedLibrary);
    }
}
