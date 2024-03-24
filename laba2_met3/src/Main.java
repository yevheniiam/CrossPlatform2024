import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Author implements Externalizable {
    private String name;
    private String surname;

    public Author() {}

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        surname = (String) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(surname);
    }

    // Геттеры, сеттеры та перевизначений метод toString()
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
        return name + " " + surname;
    }
}


class Book implements Externalizable {
    private String title;
    private List<Author> authors;

    public Book() {}

    public Book(String title, List<Author> authors) {
        this.title = title;
        this.authors = authors;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        authors = (List<Author>) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeObject(authors);
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
}

class BookStore implements Externalizable {
    private String name;
    private List<Book> books;

    public BookStore() {
        this.name = "";
        this.books = new ArrayList<>();
    }


    public BookStore(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        books = (List<Book>) in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(books);
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
}

public class Main {
    public static void main(String[] args) {
        // Створюємо авторів
        Author author1 = new Author("Олександр", "Пушкін");
        Author author2 = new Author("Лев", "Толстой");

        // Створюємо книги
        List<Author> authors1 = new ArrayList<>();
        authors1.add(author1);
        Book book1 = new Book("Євгенія Онегін", authors1);

        List<Author> authors2 = new ArrayList<>();
        authors2.add(author2);
        Book book2 = new Book("Война та мир", authors2);

// Створюємо книгарню
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        BookStore bookStore = new BookStore("Книжковий світ", books);


        serialize(bookStore, "bookstore.ser");


        BookStore deserializedBookStore = deserialize("bookstore.ser");

        // Виводимо інформацію про десеріалізовану книгарню
        System.out.println("Назва книгарні (десеріалізована): " + deserializedBookStore.getName());
        System.out.println("Книги у книгарні (десеріалізований):");
        for (Book book : deserializedBookStore.getBooks()) {
            System.out.println("- " + book.getTitle() + ", автор(и): " + book.getAuthors());
        }
    }

    private static void serialize(BookStore bookstore, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(bookstore);
            System.out.println("Серіалізація успішно виконана");
        } catch (IOException e) {
            System.err.println("Помилка серіалізації: " + e.getMessage());
        }
    }

    private static BookStore deserialize(String filename) {
        BookStore bookstore = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            bookstore = (BookStore) in.readObject();
            System.out.println("Серіалізація успішно виконана");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Помилка серіалізації: " + e.getMessage());
        }
        return bookstore;
    }
}