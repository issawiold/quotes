package work;

public class Quote {
    private String[] tags;
    private String author;
    private String likes;
    private String text;

    public Quote(String[] tags, String author, String likes, String text) {
        this.tags = tags;
        this.author = author;
        this.likes = likes;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
