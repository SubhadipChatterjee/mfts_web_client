/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package poc.mfts.types;

import java.io.Serializable;

/**
 *
 * @author subhadip.chatterjee@tcs.com
 */
public class JobExecStatus implements Serializable{

    private String id;
    private String status;
    private String startTime;
    private String duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "JobExecStatus{" + "id=" + id + ", status=" + status + ", startTime=" + startTime + ", duration=" + duration + '}';
    }
}
