/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfxfinal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MemeMaster64
 */
public class DataBase {

    private static String driverClassName = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://localhost/libraryfinal";
    private static String username = "postgres";
    private static String passwd = "159753258000kK";// edw eisagete ton kwdiko sas apo tin postgres
    private static Connection conn = null;
    private static Statement statement = null;
    private static ResultSet rs = null;
    private static PreparedStatement stt = null;
    private static int i = 0;
    private static DataBase db = null;
    static User user = null;
    private static Book[] library;
    private static Book[] rentedBooks;
    private static ArrayList<String> genres;

    static boolean makeAdmin(String name) throws SQLException {
        stt = conn.prepareStatement("select makeAdmin(?)");
        stt.setString(1, name);
        rs = stt.executeQuery();
        rs.next();
        return rs.getBoolean("makeadmin");
    }

    public void setGenre(String s) {
        genres.add(s);
    }

    private DataBase() throws ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, username, passwd);
        statement = conn.createStatement();

    }

    public static Book findBookWithID(int ID) {
        for (int i = 0; i < library.length; i++) {
            if (library[i].getId() == ID) {
                return library[i];
            }
        }
        return null;
    }

    public static DataBase getInstance() throws ClassNotFoundException, SQLException {
        if (db == null) {
            db = new DataBase();
        }
        return db;
    }

    public void loadLibrary() throws SQLException, IOException {
        String s = "select * from getbooks()";
        rs = statement.executeQuery(s);
        File file = null;
        Stack<Book> bookStack = new Stack();
        while (rs.next()) {
            int bid = rs.getInt("bid");
            String title = rs.getString("title");
            String overview = rs.getString("overview");
            String release_date = rs.getString("release_date");
            int rating = rs.getInt("rating");
            int votes_count = rs.getInt("votes_count");
            double votes_avg = rs.getDouble("votes_avg");
            int quantity = rs.getInt("quantity");
            int monthly_price = rs.getInt("monthly_price");
            String author = rs.getString("author");
//            int genre_id = rs.getInt("genre_id");
            String publisher = rs.getString("publisher");
            int pages = rs.getInt("pages");
            byte[] image = rs.getBytes("image");;
            bookStack.push(
                    new Book(bid, title, overview,
                            rating, votes_count, votes_avg, monthly_price, new ArrayList<String>(),
                            publisher, quantity, pages,
                            author, release_date,
                            null, image
                    ));

        }
        Book[] library = new Book[bookStack.size()];
        for (int i = 0; i < library.length; i++) {
            library[i] = bookStack.pop();
        }
        this.library = library;
    }

    public void loadRentedBooks(int cid) throws SQLException, IOException {
        stt = conn.prepareStatement("select * from rentedbooks(?)");
        stt.setInt(1, cid);
        rs = stt.executeQuery();
        File file = null;
        Stack<Book> rentedStack = new Stack();
        while (rs.next()) {
            int bid = rs.getInt("bid");
            int rentId = rs.getInt("rent_id");
            Timestamp rentDate = rs.getTimestamp("rentdate");
            System.out.println(rentId);
            for (int i = 0; i < library.length; i++) {
                if (bid == library[i].getId()) {

                    rentedStack.push(library[i]);
                    library[i].setRentalId(rentId);
                    library[i].setRentDate(rentDate);
                }
            }

        }
        Book[] rentedBooks = new Book[rentedStack.size()];

        for (int i = 0; i < rentedBooks.length; i++) {
            rentedBooks[i] = rentedStack.pop();
        }
        this.rentedBooks = rentedBooks;
    }

    public void loadRentedBooks() throws SQLException, IOException {
        stt = conn.prepareStatement("select * from getAllRentedBooks()");
        rs = stt.executeQuery();
        File file = null;
        Stack<Book> rentedStack = new Stack();
        while (rs.next()) {
            String title = rs.getString("title");
            int rentId = rs.getInt("rent_id");
            System.out.println(rentId);
            for (int i = 0; i < library.length; i++) {
                if (title.equals(library[i].getTitle())) {

                    rentedStack.push(library[i]);
                    library[i].setRentalId(rentId);
                }
            }

        }
        Book[] rentedBooks = new Book[rentedStack.size()];

        for (int i = 0; i < rentedBooks.length; i++) {
            rentedBooks[i] = rentedStack.pop();
        }
        this.rentedBooks = rentedBooks;
    }

    public Book[] getRentedBooks() {
        return this.rentedBooks;
    }

    public Book[] getLibrary() {
        return library;
    }

    public boolean login(String username, String passwd) throws SQLException, ClassNotFoundException {

        stt = conn.prepareStatement("select login(?,?)");
        stt.setString(1, username);
        stt.setString(2, passwd);
        rs = stt.executeQuery();

        rs.next();
        int result = rs.getInt("login");
        if (result != 0) {
//                String s="select * from customer where uname='"+username+"'";
//                rs = statement.executeQuery(s);
            stt = conn.prepareStatement("select * from getUser(?)");
            stt.setString(1, username);
            rs = stt.executeQuery();
            rs.next();
            int id = rs.getInt("cid");
            String cname = rs.getString("real_name");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            // String dateOfRegister = rs.getString("dateofregister");
            String address = rs.getString("address");
            switch (result) {
                case 1:
                    user = new Customer(id, username, passwd, cname, email, phone, "", address);
                    break;
                case 2:
                    user = new Admin(id, username, passwd, cname, email, phone, "", address);
                    break;
            }
            System.out.println("Welcome " + user + " you have succesfuly logged in");
            return true;
        }
        return false;
    }

    public int signup(String username, String password, String email, String address, String phone, String rname) throws SQLException {
        stt = conn.prepareStatement("select signup(?,?,?,?,?,?,?)");
        stt.setString(1, username);
        stt.setString(2, password);
        stt.setString(3, email);
        stt.setString(4, address);
        stt.setString(5, "0000000000000000");
        stt.setString(6, phone);
        stt.setString(7, rname);
        rs = stt.executeQuery();
        rs.next();
        System.out.println(rs.getInt("signup"));

        return rs.getInt("signup");
    }

    public void bindGenreToBooks(String genre) throws SQLException {

        stt = conn.prepareStatement("select * from showbooksbygenre(?)");

        stt.setString(1, genre);
        rs = stt.executeQuery();
        while (rs.next()) {
            int bid = rs.getInt("bid");
            for (int i = 0; i < library.length; i++) {
                if (bid == library[i].getId()) {
                    System.out.println(bid);
                    library[i].addGenre(genre);
                }
            }
        }

    }

    public void addGenreToBooks(int bid, int genre) throws SQLException {

        stt = conn.prepareStatement("select * from addgenretobook(?,?)");
        stt.setInt(1, bid);
        stt.setInt(2, genre);
        rs = stt.executeQuery();
        rs.next();

    }

    public void changecredentials(Customer c) throws SQLException {
        if (user == null ) {
            return;
        }

        stt = conn.prepareStatement("select changecredentials(?,?,?,?)");
        stt.setString(1, c.getUname());
        stt.setString(2, c.getEmail());
        stt.setString(3, c.getAddress());
        stt.setString(4, c.getPhone());
        rs = stt.executeQuery();
        rs.next();
    }

    public int rentBook(int cid, int bookID) throws SQLException {
        if (user == null) {
            return 0;
        }

        for (int i = 0; i < rentedBooks.length; i++) {
            if (rentedBooks[i].getId() == bookID) {
                return 0;
            }
        }
        stt = conn.prepareStatement("select rentbook(?,?)");
        stt.setInt(1, cid);
        stt.setInt(2, bookID);
        rs = stt.executeQuery();
        rs.next();
        return rs.getInt("rentbook");
    }

    public void logout(String username) throws SQLException {
        if (user == null) {
            return;
        }

        stt = conn.prepareStatement("select logout(?)");
        stt.setString(1, username);
        rs = stt.executeQuery();
        rs.next();
        return;
    }

    public int getLatestAddition() throws SQLException {
        if (user == null) {
            return 0;
        }

        stt = conn.prepareStatement("select getlatestaddition()");
        rs = stt.executeQuery();
        rs.next();
        return rs.getInt("getlatestaddition");
    }

    public void addBook(Book b) throws SQLException {
        if (user == null || user instanceof Customer) {
            return;
        }

        stt = conn.prepareStatement("select addbook(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        stt.setString(1, b.getTitle());
        stt.setString(2, b.getDescription());
        stt.setString(3, b.getDateOfPublication());
        stt.setInt(4, b.getRating());
        stt.setInt(5, 0);
        stt.setFloat(6, 0);
        stt.setInt(7, b.getQuantity());
        stt.setFloat(8, (float) b.getMonthlyFee());
        stt.setInt(9, b.getPages());
        stt.setString(10, b.getAuthor());
        System.out.println(b.getAuthor());
        System.out.println(b.getFile().length());
        stt.setBinaryStream(11, b.getInput(), (int) b.getFile().length());
        stt.setString(12, b.getPublisher());
        stt.setString(13, user.getUname());
        rs = stt.executeQuery();
        rs.next();

    }

    public void removeBook(int bid) throws SQLException {
        if (user == null || user instanceof Customer) {
            return;
        }

        stt = conn.prepareStatement("select removebook(?)");
        stt.setInt(1, bid);
        rs = stt.executeQuery();
        rs.next();
    }

    public void addQuantity(int bid, int quantity) throws SQLException {
        if (user == null || user instanceof Customer) {
            return;
        }
        stt = conn.prepareStatement("select addquantity(?,?)");
        stt.setInt(1, bid);
        stt.setInt(2, quantity);
        rs = stt.executeQuery();
        rs.next();
    }

    public String[] showLoginLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from getloginlogs()");
        rs = stt.executeQuery();
        rs.next();
        String unames = "Ονόματα χρηστών" + "\n";
        String logindates = "Ημ/νια" + "\n";
        while (rs.next()) {
            unames += rs.getString("uname") + "\n";
            logindates += rs.getString("logindate") + "\n";
        }
        String[] s = {unames, logindates};
        return s;
    }

    public String[] showSignUpLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from getsignuplogs()");
        rs = stt.executeQuery();
        rs.next();
        String unames = "Ονόματα χρηστών" + "\n";
        String logindates = "Ημ/νια" + "\n";
        while (rs.next()) {
            unames += rs.getString("username") + "\n";
            logindates += rs.getString("datesigned") + "\n";
        }
        String[] s = {unames, logindates};
        return s;
    }

    public String[] showAddedBookLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from getaddbooklogs()");
        rs = stt.executeQuery();
        rs.next();
        String title = "Τίτλος\n";
        String quantity = "Ποσότητα\n";
        String dateAdded = "Ημ/νια\n";
        while (rs.next()) {
            title += rs.getString("title") + "\n";
            quantity += rs.getInt("quantity") + "\n";
            dateAdded += rs.getString("dateadded") + "\n";
        }
        String[] s = {title, quantity, dateAdded};
        return s;
    }

    public String[] showChangedAddressLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from showchangeaddresslogs()");
        rs = stt.executeQuery();
        rs.next();
        String uname = "Όνομα\n";
        String oldaddress = "Παλιά διεύθυνση\n";
        String newaddress = "Νέα διεύθυνση\n";
        while (rs.next()) {
            uname += rs.getString("uname") + "\n";
            oldaddress += rs.getString("oldaddress") + "\n";
            newaddress += rs.getString("newaddress") + "\n";
        }
        String[] s = {uname, oldaddress, newaddress};
        return s;
    }

    public String[] showChangedEmailLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from showchangeemaillogs()");
        rs = stt.executeQuery();
        rs.next();
        String uname = "Όνομα\n";
        String oldemail = "Παλιό email\n";
        String newemail = "Νέo email\n";
        while (rs.next()) {
            uname += rs.getString("uname") + "\n";
            oldemail += rs.getString("oldemail") + "\n";
            newemail += rs.getString("newemail") + "\n";
        }
        String[] s = {uname, oldemail, newemail};
        return s;
    }

    public String[] showChangedPhoneLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from showchangephonelogs()");
        rs = stt.executeQuery();
        rs.next();
        String uname = "Όνομα\n";
        String oldphone = "Παλιό τηλέφωνο\n";
        String newphone = "Νέo τηλέφωνο\n";
        while (rs.next()) {
            uname += rs.getString("uname") + "\n";
            oldphone += rs.getString("oldphone") + "\n";
            newphone += rs.getString("newphone") + "\n";
        }

        String[] s = {uname, oldphone, newphone};
        return s;
    }

    public static void returnBook(int s) throws SQLException {
        stt = conn.prepareStatement("select returnbook(?)");
        stt.setInt(1, s);
        stt.executeQuery();
    }

    String[] showAdminSignUpLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from getadminsignuplogs()");
        rs = stt.executeQuery();
        rs.next();
        String unames = "Ονόματα χρηστών" + "\n";
        String logindates = "Ημ/νια" + "\n";
        while (rs.next()) {
            unames += rs.getString("username") + "\n";
            logindates += rs.getString("datesigned") + "\n";
        }
        String[] s = {unames, logindates};
        return s;
    }

    String[] showAdminLoginLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from getadminloginlogs()");
        rs = stt.executeQuery();
        rs.next();
        String unames = "Ονόματα χρηστών" + "\n";
        String logindates = "Ωρα εισοδου" + "\n";
        String logoutdates ="Ωρα εξοδου"+"\n";
        while (rs.next()) {
            unames += rs.getString("uname") + "\n";
            logindates += rs.getString("logindate") + "\n";
            
        }
        String[] s = {unames, logindates};
        return s;
    }

    String[] showAdminLogoutLogs() throws SQLException {
        if (user == null || user instanceof Customer) {
            return null;
        }
        stt = conn.prepareStatement("select * from getadminlogoutlogs()");
        rs = stt.executeQuery();
        rs.next();
        String unames = "Ονόματα χρηστών" + "\n";
        String logoutdates ="Ωρα εξοδου"+"\n";
        while (rs.next()) {
            unames += rs.getString("uname") + "\n";
            logoutdates += rs.getString("logoutdate") + "\n";
            
        }
        String[] s = {unames, logoutdates};
        return s;
    }
    }


