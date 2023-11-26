package project1.model;

public class PhysicalBook extends Book {
    private String cover;
    private int stock;

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Book {" +
                "id=" + super.getId() +
                ", author='" + super.getAuthor() + '\'' +
                ", title='" + super.getTitle() + '\'' +
                ", publishedDate=" + super.getPublishedDate() +
                ", cover=" + cover +
                " }";
    }
}
