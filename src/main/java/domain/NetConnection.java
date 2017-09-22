package domain;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * Data holder for Network Connection
 * @author Deepak Agrawal
 */
@XmlRootElement
public class NetConnection {
	String name;
	String vnfc1; // name of vnfc1
	String vnfc2; // name of vnfc2
	String cp1;   // name of cp1
	String cp2;   // name of cp2
	String vl;    // name of vl
    String uuid;
    Date time_created;
    
	public NetConnection() {
		super();
		// TODO Auto-generated constructor stub
		this.uuid = UUID.randomUUID().toString();
		this.time_created = new Date();
	}

	public NetConnection(String name, String vnfc1, String vnfc2, String cp1, String cp2, String vl) {
		super();
		this.name = name;
		this.vnfc1 = vnfc1;
		this.vnfc2 = vnfc2;
		this.cp1 = cp1;
		this.cp2 = cp2;
		this.vl = vl;
		this.uuid = UUID.randomUUID().toString();
		this.time_created = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVnfc1() {
		return vnfc1;
	}

	public void setVnfc1(String vnfc1) {
		this.vnfc1 = vnfc1;
	}

	public String getVnfc2() {
		return vnfc2;
	}

	public void setVnfc2(String vnfc2) {
		this.vnfc2 = vnfc2;
	}

	public String getCp1() {
		return cp1;
	}

	public void setCp1(String cp1) {
		this.cp1 = cp1;
	}

	public String getCp2() {
		return cp2;
	}

	public void setCp2(String cp2) {
		this.cp2 = cp2;
	}

	public String getVl() {
		return vl;
	}

	public void setVl(String vl) {
		this.vl = vl;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getTime_created() {
		return time_created;
	}

	public void setTime_created(Date time_created) {
		this.time_created = time_created;
	}
}
