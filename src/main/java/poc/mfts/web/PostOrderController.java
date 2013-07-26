/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package poc.mfts.web;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poc.mfts.ejb.PostOrderSessionBean;
import poc.mfts.entity.Person;
import poc.mfts.types.JobExecStatus;
import poc.mfts.types.SnapshotStatus;
import poc.springbatch.types.UserRegistrationStatus;

/**
 *
 * @author subhadip.chatterjee@tcs.com
 */
@ManagedBean(name = "controller")
@SessionScoped
public class PostOrderController implements Serializable{

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final String EMPTY_STRING = "";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @EJB
    private PostOrderSessionBean ejbInstance;
    private String uid = EMPTY_STRING;
    private String firstName = EMPTY_STRING;
    private String lastName = EMPTY_STRING;
    private Date dateOfJoin; // expected format: YYYY-MM-DD
    private String department = EMPTY_STRING;
    private UserRegistrationStatus regnStatus = UserRegistrationStatus.UNKNOWN;
    private String jsonData = EMPTY_STRING;
    private SnapshotStatus snapshotStatus;

    static {
        dateFormatter.setLenient(false);
    }

    /**
     * Creates a new instance of PostOrderController
     */
    public PostOrderController() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        if (uid == null || uid.isEmpty()) {
            logger.error("User id is empty!");
            throw new IllegalArgumentException("User id is empty!");
        }
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            logger.error("First Name is empty!");
            throw new IllegalArgumentException("First Name is empty!");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            logger.error("First Name is empty!");
            throw new IllegalArgumentException("Last Name is empty!");
        }
        this.lastName = lastName;
    }

    public String getDateOfJoin() {
        return (dateOfJoin != null ? dateFormatter.format(dateOfJoin) : "");
    }

    public void setDateOfJoin(String dateOfJoin) {
        if (dateOfJoin == null || dateOfJoin.isEmpty()) {
            logger.error("Date of Join is empty!");
            throw new IllegalArgumentException("Date of Join is empty!");
        }
        try {
            this.dateOfJoin = dateFormatter.parse(dateOfJoin);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegnStatus() {
        return regnStatus.toString();
    }

    public void setRegnStatus(UserRegistrationStatus regnStatus) {
        this.regnStatus = regnStatus;
    }

    public String getJsonData() {
        return jsonData;
    }

    public SnapshotStatus getSnapshotStatus() {
        return snapshotStatus;
    }

    public void save() {
        if (logger.isInfoEnabled()) {
            logger.info("Form data is submitted");
        }
        Person person = new Person();
        person.setId(uid);
        person.setFname(firstName);
        person.setLname(lastName);
        person.setDoj(dateOfJoin);
        person.setDept(department);
        person.setNewRecord(1);
        try {
            ejbInstance.save(person);
            regnStatus = UserRegistrationStatus.SUCCESS;
        } catch (RuntimeException ex) {
            regnStatus = UserRegistrationStatus.FAILURE;
        }
        if (logger.isInfoEnabled()) {
            logger.info("Form data is saved");
        }
    }

    public void reset() {
        uid = EMPTY_STRING;
        firstName = EMPTY_STRING;
        lastName = EMPTY_STRING;
        dateOfJoin = null;
        department = EMPTY_STRING;
        regnStatus = UserRegistrationStatus.UNKNOWN;
    }

    public void getStatusAdminAPIView() throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("getStatusAdminAPIView()");
        }
        Client client = ClientBuilder.newClient();
        jsonData = client.target("http://localhost:8080/process/jobs/executions.json")
                .request().get(String.class);
        
        /*Long & Tedious - JSON data processing*/
        JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
        JsonObject jsonObject = jsonReader.readObject();
        JsonObject executions = jsonObject.getJsonObject("jobExecutions");
        Set<String> keys = executions.keySet();
        if (!keys.isEmpty()) {
            snapshotStatus = new SnapshotStatus();
            JobExecStatus[] array_of_status = new JobExecStatus[keys.size()];
            snapshotStatus.setJobExecutions(array_of_status);
            int counter = 0;
            Iterator<String> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                JobExecStatus eachInstanceExecStatus = new JobExecStatus();
                String jobInstanceId = keyIterator.next();
                JsonObject eachInstanceData = executions.getJsonObject(jobInstanceId);
                eachInstanceExecStatus.setId(jobInstanceId);
                eachInstanceExecStatus.setStatus(eachInstanceData.getString("status"));
                eachInstanceExecStatus.setStartTime(eachInstanceData.getString("startTime"));
                eachInstanceExecStatus.setDuration(eachInstanceData.getString("duration"));

                array_of_status[counter++] = eachInstanceExecStatus;
            }
        } 
        /*JSON Processing is over*/
        FacesContext.getCurrentInstance().getExternalContext().redirect("apiView.jsf");
    }

    public void getStatusAdminUIView() throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("getStatusAdminUIView() is invoked");
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/process/jobs/executions");
    }
}