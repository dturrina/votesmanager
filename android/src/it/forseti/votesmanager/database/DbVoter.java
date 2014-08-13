package it.forseti.votesmanager.database;

/**
 * Class representing the database Voter record.
 * It should only be used as a wrapper between database records and application beans.
 *
 * @author dturrina
 * @since  0.2
 */
public class DbVoter {

    /**
     * Each Voter record has a unique identifier (id) and a name.
     */
    private long id;
    private String name;

    /**
     * Identifier getter
     *
     * @return the identifier
     */
    public long getId() {
        return id;
    }

    /**
     * Identifier setter
     *
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Name getter
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return a string with a log-friendly description of the object.
     * It contains the id and the name.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("DbVoter={");
        buf.append("id=");
        buf.append(getId());
        buf.append("; name=");
        buf.append(getName());
        buf.append("}");
        return buf.toString();
    }
}
