package models;

import java.sql.Timestamp;

/**
 * @author Yullia Shcherbakova
 * @project final
 */
public class AppointingTimeAndPerson {
    private int id;
    private int appointedId;
    private Timestamp time;
    private int performerId;
    private boolean wasAppointed;

    /**
     * @param id
     * @param appointerId
     * @param time
     * @param performerId
     * @param wasAppointed
     */
    public AppointingTimeAndPerson(int id, int appointerId, Timestamp time, int performerId, boolean wasAppointed) {
        this.id = id;
        this.appointedId = appointerId;
        this.time = time;
        this.performerId = performerId;
        this.wasAppointed = wasAppointed;
    }

    /**
     * @param time
     * @param performerId
     */
    public AppointingTimeAndPerson(Timestamp time, int performerId) {
        this.time = time;
        this.performerId = performerId;
    }

    /**
     *
     */
    public AppointingTimeAndPerson() {
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public int getPerformerId() {
        return performerId;
    }

    /**
     * @param performerId
     */
    public void setPerformerId(int performerId) {
        this.performerId = performerId;
    }

    /**
     * @return
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * @return
     */
    public int getAppointedId() {
        return appointedId;
    }

    /**
     * @param appointedId
     */
    public void setAppointedId(int appointedId) {
        this.appointedId = appointedId;
    }

    /**
     * @return
     */
    public boolean isWasAppointed() {
        return wasAppointed;
    }

    /**
     * @param wasAppointed
     */
    public void setWasAppointed(boolean wasAppointed) {
        this.wasAppointed = wasAppointed;
    }
}
