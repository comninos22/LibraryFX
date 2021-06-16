/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfxfinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.image.Image;

/**
 *
 * @author MemeMaster64
 */
public class Book {

    private int id;//
    private String title;//
    private String description;//
    private int rating;//
    private int voteSum;//
    private double voteMean;//
    private double monthlyFee;//
    private ArrayList<String> genres;
    private String publisher;
    private int quantity;//
    private int pages;//
    private String author;
    private String dateOfPublication;//
    private Image img;//
    private FileInputStream input;//
    private File file;//
    private int imageHeight = 120;
    private int imageWidth = 200;
    private int rentalId;
    private Date date;
    private Timestamp rentDate;
    private Timestamp returnDate;

    public Book(int id, String title, String description, int rating,
            int voteSum, double voteMean, double monthlyFee, ArrayList<String> genres,
            String publisher, int quantity, int pages, String author,
            String dateOfPublication, File file, byte[] bytea
    ) throws FileNotFoundException, IOException {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.voteSum = voteSum;
        this.voteMean = voteMean;
        this.monthlyFee = monthlyFee;
        this.genres = genres;
        this.publisher = publisher;
        this.quantity = quantity;
        this.pages = pages;
        this.author = author;
        this.dateOfPublication = dateOfPublication;
        this.img = img;
        this.input = input;
        this.author = author;
        if (file != null) {
            this.file = file;
            this.input = new FileInputStream(file);
            this.img = new Image(file.toURI().toString(), imageHeight, imageWidth, false, true);
        }
        if (bytea != null) {
            //ByteArrayOutputStream(bytea.length);
            //file=File.createTempFile("temp"+i, "jpg",new File("C:/temps/"));
            file = new File("temp" + this.id + ".jpg");
            OutputStream target = new FileOutputStream(file);
            target.write(bytea);
            target.close();
            file.deleteOnExit();
            this.input = new FileInputStream(file);
            this.img = new Image(file.toURI().toString(), imageHeight, imageWidth, false, true);
        }

    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Timestamp getRentDate() {
        return rentDate;
    }

    public void setRentDate(Timestamp rentDate) {
        this.rentDate = rentDate;
        this.returnDate = new Timestamp(this.rentDate.getYear(), (this.rentDate.getMonth() + 2) % 12, this.rentDate.getDate(), 0, 0, 0, 0);

    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public String getAuthors() {
        return author;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getRentalId() {
        return rentalId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "Book{"
                + " \nid=" + id
                + " \ntitle=" + title
                + " \ndescription=" + description
                + " \nrating=" + rating
                + " \nvoteSum=" + voteSum
                + " \nvoteMean=" + voteMean
                + " \nmonthlyFee=" + monthlyFee
                + " \ngenres=" + genres
                + " \npublisher=" + publisher
                + " \nquantity=" + quantity
                + " \npages=" + pages
                + " \nauthor=" + author
                + " \ndateOfPublication=" + dateOfPublication
                + " \nimg=" + img
                + " \ninput=" + input
                + " \nimageHeight=" + imageHeight
                + " \nimageWidth=" + imageWidth
                + '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getVoteSum() {
        return voteSum;
    }

    public void setVoteSum(int voteSum) {
        this.voteSum = voteSum;
    }

    public double getVoteMean() {
        return voteMean;
    }

    public void setVoteMean(double voteMean) {
        this.voteMean = voteMean;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void addGenre(String genres) {
        this.genres.add(genres);
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }

    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public Image getImg() {
        return img;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public FileInputStream getInput() {
        return input;
    }

    public void setInput(FileInputStream input) {
        this.input = input;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
