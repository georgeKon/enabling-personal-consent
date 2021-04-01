package dbconsent.generation;

public class Link {
    int linkFromIndex = 0;
    int linkToIndex = 0;
    String linkTo = "";

    public Link(int linkFromIndex, int linkToIndex, String linkTo) {
        this.linkFromIndex = linkFromIndex;
        this.linkToIndex = linkToIndex;
        this.linkTo = linkTo;
    }
}
