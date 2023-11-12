package project1.model.builder;

import project1.model.AudioBook;

public class AudioBookBuilder extends BookBuilder<AudioBook>{

    public AudioBookBuilder(Class<AudioBook> bookClass) {
        super(bookClass);
    }

    public AudioBookBuilder setRunTime(int runTime) {
        (super.build()).setRunTime(runTime);
        return this;
    }
}
