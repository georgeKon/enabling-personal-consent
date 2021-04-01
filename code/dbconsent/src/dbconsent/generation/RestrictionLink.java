package dbconsent.generation;

public class RestrictionLink<T> {
    String connectionPredicate = "";
    Link link;
    Restriction restriction;
    T restrictionValue;
    Double restrictivity = 0.0;
    public RestrictionLink(String connectionPredicate, Link link, Restriction restriction, T restrictionValue, Double restrictivity){
        this.connectionPredicate = connectionPredicate;
        this.link = link;
        this.restriction = restriction;
        this.restrictionValue = restrictionValue;
        this.restrictivity= restrictivity;
    }
}
