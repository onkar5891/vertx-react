package ok.movie;

public class Movie {
    private String id;
    private String name;
    private String genre;

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Movie{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", genre='" + genre + '\'' +
            '}';
    }
}
