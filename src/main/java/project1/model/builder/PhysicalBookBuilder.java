package project1.model.builder;


import project1.model.PhysicalBook;

public class PhysicalBookBuilder extends BookBuilder<PhysicalBook> {
    public PhysicalBookBuilder(Class<PhysicalBook> bookClass) {
        super(bookClass);
    }

    public PhysicalBookBuilder setCover(String cover) {
        (super.build()).setCover(cover);
        return this;
    }

    public PhysicalBookBuilder setStock(int stock) {
        (super.build()).setStock(stock);
        return this;
    }

}
