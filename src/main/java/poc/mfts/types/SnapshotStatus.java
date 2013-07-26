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
public class SnapshotStatus implements Serializable{

    private JobExecStatus[] jobExecutions;

    public JobExecStatus[] getJobExecutions() {
        return jobExecutions;
    }

    public void setJobExecutions(JobExecStatus[] jobExecutions) {
        this.jobExecutions = jobExecutions;
    }

    @Override
    public String toString() {
        return "SnapshotStatus{" + "jobExecutions=" + jobExecutions + '}';
    }
}
