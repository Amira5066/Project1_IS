package project1.model;

public class AudioBook extends Book {
    int runTime;

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getRunTime() {
        return runTime;
    }

    @Override
    public String toString() {
        return "Book {" +
                "id=" + super.getId() +
                ", author='" + super.getAuthor() + '\'' +
                ", title='" + super.getTitle() + '\'' +
                ", publishedDate=" + super.getPublishedDate() +
                ", runTime=" + runTime +
                " }";
    }
}
