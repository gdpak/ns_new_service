package domain;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConnectionPoint {
	String name;
	int order;
	int is_default;
	int vr_port_type;
	int anti_spoof_protection;
    // Auto-generated elements
	String uuid;
	Date time_created;
	
	public ConnectionPoint() {
		super();
		// TODO Auto-generated constructor stub
		this.uuid = UUID.randomUUID().toString();
		this.time_created = new Date();
	}

	public ConnectionPoint(String name, int order, int is_default, int vr_port_type, int anti_spoof_protection) {
		super();
		this.name = name;
		this.order = order;
		this.is_default = is_default;
		this.vr_port_type = vr_port_type;
		this.anti_spoof_protection = anti_spoof_protection;
		this.uuid = UUID.randomUUID().toString();
		this.time_created = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getIs_default() {
		return is_default;
	}

	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}

	public int getVr_port_type() {
		return vr_port_type;
	}

	public void setVr_port_type(int vr_port_type) {
		this.vr_port_type = vr_port_type;
	}

	public int getAnti_spoof_protection() {
		return anti_spoof_protection;
	}

	public void setAnti_spoof_protection(int anti_spoof_protection) {
		this.anti_spoof_protection = anti_spoof_protection;
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