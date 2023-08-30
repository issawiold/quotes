package work;

public class Quote {
    private String[] tags;
    private String quoteAuthor;
    private String likes;
    private String quoteText;

    public Quote( String text,  String author) {
        this.tags = tags;
        this.quoteAuthor = author;
        this.likes = likes;
        this.quoteText = text;
    }

    public Quote(String[] tags, String author, String likes, String text) {
        this.tags = tags;
        this.quoteAuthor = author;
        this.likes = likes;
        this.quoteText = text;
    }

    public String getText() {
        return quoteText +" "+quoteAuthor;
    }
}
