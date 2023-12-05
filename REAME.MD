Era mai bine daca AudioBook si EBook le faceam cu o compozitie:  

public class AudioBook {  

    int runTime;
    Book basicBook;
    
    public void setBook(Book book) {  
        this.basicBook = book;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
    // getters for book and runTime  
}

Asa imi era mult mai simplu ca din tabelul book sa imi fac o lista de Books, iar mai apoi sa fac o lista de AudioBooks si EBooks din celalalte doua tabele, la sfarsit ferificand care au un id comun.  
Imi era mult mai simplu si la functia save deoarece aveam deja implementata toata logica pentru salvarea unui Book de care ma puteam folosi.  
M-am gandit sa scriu un readme ca sa nu imi uit celelalte idei si pentru ca nu o sa ajung sa implementez varianta aceasta :)