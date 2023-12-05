package project1.model.builder;

import project1.model.AudioBook;
import project1.model.EBook;

public class EBookBuilder extends BookBuilder<EBook>{
    public EBookBuilder(Class<EBook> bookClass) {
        super(bookClass);
    }

    public EBookBuilder setFormat(String format) {
        (super.build()).setFormat(format);
        return this;
    }
}
