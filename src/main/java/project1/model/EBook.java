package project1.model;

import project1.model.Book;

public class EBook extends Book {
    String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "Book {" +
                "id=" + super.getId() +
                ", author='" + super.getAuthor() + '\'' +
                ", title='" + super.getTitle() + '\'' +
                ", publishedDate=" + super.getPublishedDate() +
                ", format=" + format +
                " }";
    }
}
