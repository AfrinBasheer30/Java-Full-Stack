package MiniProject;
import java.util.*;
import java.io.*;
class Book {
    private String id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getId() {
        return id;
    }
    public boolean isIssued() {
        return isIssued;
    }
    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + ", /nTitle: " + title + ",/nAuthor: " + author +
                ", /nStatus: " + (isIssued ? "Issued" : "Available");
    }
}

class Member {
    String memberId;
    private String name;
    private List<String> borrowedBookIds;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.borrowedBookIds = new ArrayList<>();
    }

    public List<String> getBorrowedBookIds() {
        return borrowedBookIds;
    }
    public void borrowBook(String bookId) {
        borrowedBookIds.add(bookId);
    }
    public void returnBook(String bookId) {
        borrowedBookIds.remove(bookId);
    }
}

class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String msg) { super(msg); }
}

class InvalidReturnException extends Exception {
    public InvalidReturnException(String msg) { super(msg); }
}

class Library {
    HashMap<String, Book> inventory = new HashMap<>();
    HashMap<String, Member> members = new HashMap<>();

    public void addBook(Book book) {
        inventory.put(book.getId(), book);
        log("Added Book: " + book.getId());
    }

    public void addMember(Member member) {
        members.put(member.memberId, member);
        log("Added Member: " + member.memberId);
    }

    public void issueBook(String bookId, String memberId) throws BookNotAvailableException {
        Book book = inventory.get(bookId);
        Member member = members.get(memberId);

        if (book == null || member == null || book.isIssued()) {
            throw new BookNotAvailableException("Book cannot be issued (not found or already issued).");
        }

        book.setIssued(true);
        member.borrowBook(bookId);
        log("Issued Book: " + bookId + " to Member: " + memberId);
    }

    public void returnBook(String bookId, String memberId, int daysLate) throws InvalidReturnException {
        Book book = inventory.get(bookId);
        Member member = members.get(memberId);

        if (book == null || member == null || !book.isIssued() || !member.getBorrowedBookIds().contains(bookId)) {
            throw new InvalidReturnException("Invalid return operation.");
        }

        book.setIssued(false);
        member.returnBook(bookId);

        int fine = daysLate * 2;
        System.out.println("Late Fee: ₹" + fine);
        log("Returned Book: " + bookId + " from Member: " + memberId + " | Fine: ₹" + fine);
    }

    public void showInventory() {
        inventory.values().forEach(System.out::println);
    }

    public void log(String msg) {
        try(FileWriter fw = new FileWriter("log.txt", true)) {
            fw.write(msg + "\n");
        } catch(Exception e) {}
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while(true) {
            System.out.println("\n===== Library Menu =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Show Inventory");
            System.out.println("6. Exit");
            System.out.print("Enter Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            try {
                switch(ch) {
                    case 1:
                        System.out.print("Enter Book ID: ");
                        String id = sc.nextLine();
                        System.out.print("Enter Title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter Author: ");
                        String author = sc.nextLine();
                        library.addBook(new Book(id, title, author));
                        break;

                    case 2:
                        System.out.print("Enter Member ID: ");
                        String mid = sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        library.addMember(new Member(mid, name));
                        break;

                    case 3:
                        System.out.print("Enter Book ID: ");
                        String b1 = sc.nextLine();
                        System.out.print("Enter Member ID: ");
                        String m1 = sc.nextLine();
                        library.issueBook(b1, m1);
                        break;

                    case 4:
                        System.out.print("Enter Book ID: ");
                        String b2 = sc.nextLine();
                        System.out.print("Enter Member ID: ");
                        String m2 = sc.nextLine();
                        System.out.print("Days Late: ");
                        int days = sc.nextInt();
                        library.returnBook(b2, m2, days);
                        break;

                    case 5:
                        library.showInventory();
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        return;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
