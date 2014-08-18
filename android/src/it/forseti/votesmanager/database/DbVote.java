package it.forseti.votesmanager.database;

/**
 * Class representing the database Vote record.
 * It should only be used as a wrapper between database records and application beans.
 *
 * @author dturrina
 * @since  0.2
 */
public class DbVote {

    /**
     * Each Vote record has a unique identifier (id), a value (vote) and the corresponding voter
     * and competitor identifiers.
     */
    private long id;
    private long voterId;
    private long competitorId;
    private double vote;

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
     * Voter identifier getter
     *
     * @return the voter id
     */
    public long getVoterId() {
        return voterId;
    }

    /**
     * Voter identifier setter
     *
     * @param voterId the id of the voter to set
     */
    public void setVoterId(long voterId) {
        this.voterId = voterId;
    }

    /**
     * Competitor identifier getter
     *
     * @return the competitor id
     */
    public long getCompetitorId() {
        return competitorId;
    }

    /**
     * Competitor identifier setter
     *
     * @param competitorId the id of the competitor to set
     */
    public void setCompetitorId(long competitorId) {
        this.competitorId = competitorId;
    }

    /**
     * Vote getter
     *
     * @return the value of the vote
     */
    public double getVote() {
        return vote;
    }

    /**
     * Vote setter
     *
     * @param vote the value of the vote to set
     */
    public void setVote(double vote) {
        this.vote = vote;
    }

    /**
     * Return a string with a log-friendly description of the object.
     * It contains the id, the competitor and voter id, and the vote.
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("DbVote={");
        buf.append("id=");
        buf.append(getId());
        buf.append("; competitorId=");
        buf.append(getCompetitorId());
        buf.append("; voterId=");
        buf.append(getVoterId());
        buf.append("; vote=");
        buf.append(getVote());
        buf.append("}");
        return buf.toString();
    }
}
