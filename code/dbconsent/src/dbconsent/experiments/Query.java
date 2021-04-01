package dbconsent.experiments;

public class Query {
    private String description;
    private String query;

    public Query(String description, String query){
        this.description = description;
        this.query = query;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
