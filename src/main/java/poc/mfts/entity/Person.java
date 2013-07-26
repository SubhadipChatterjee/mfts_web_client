/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package poc.mfts.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author subhadip.chatterjee@tcs.com
 */
@Entity
public class Person implements Serializable {

    public Person() {
    }
    @Id
    private String id;
    @Column(name = "FIRST_NAME", nullable = false)
    private String fname;
    @Column(name = "LAST_NAME", nullable = false)
    private String lname;
    @Column(name = "JOINED_AT", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date doj;
    @Column(name = "DEPARTMENT")
    private String dept;
    @Column(name = "JUST_IN")
    private int newRecord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getDoj() {
        return doj;
    }

    public void setDoj(Date doj) {
        this.doj = doj;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getNewRecord() {
        return newRecord;
    }

    public void setNewRecord(int newRecord) {
        this.newRecord = newRecord;
    }

    
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", fname=" + 
                fname + ", lname=" + lname + ", doj=" + 
                doj + ", dept=" + dept + '}';
    }
}