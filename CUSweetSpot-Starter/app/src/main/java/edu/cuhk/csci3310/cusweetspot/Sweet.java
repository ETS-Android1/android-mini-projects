package edu.cuhk.csci3310.cusweetspot;

//
// Name: Yeung Man Wai
// SID: 1155126854
//


public class Sweet {
    private String filename;
    private String sweet_name;
    private String resto;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSweet_name() {
        return sweet_name;
    }

    public void setSweet_name(String sweet_name) {
        this.sweet_name = sweet_name;
    }

    public String getResto() {
        return resto;
    }

    public void setResto(String resto) {
        this.resto = resto;
    }

    @Override
    public String toString() {
        return "Sweet{" +
                "filename='" + filename + '\'' +
                ", sweet_name='" + sweet_name + '\'' +
                ", resto='" + resto + '\'' +
                '}';
    }
}
