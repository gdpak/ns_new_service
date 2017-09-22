package domain;

import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * Data Holder for VL
 * @author Deepak Agrawal
 */

@XmlRootElement
public class VirtualLink {
	String name;
	int type;
	int tenant_vlan;
	int ip_version;
	String ip_cidr;
	int segment_id;
	int dhcp_enabled;
	// Auto-generated or indirectly retrieved
	int cloud_tenant_id;
	String uuid;
	Date time_created;
	
	public VirtualLink() {
		//super();
		// TODO Auto-generated constructor stub
		this.cloud_tenant_id =0;
		this.uuid = UUID.randomUUID().toString();
		this.time_created = new Date();
	}

	public VirtualLink(String name, int type, int tenant_vlan, int ip_version, String ip_cidr, int segment_id,
			int dhcp_enabled) {
		super();
		this.name = name;
		this.type = type;
		this.tenant_vlan = tenant_vlan;
		this.ip_version = ip_version;
		this.ip_cidr = ip_cidr;
		this.segment_id = segment_id;
		this.dhcp_enabled = dhcp_enabled;
		// this.cloud_tenant_id = cloud_tenant_id; // need to see how to retrieve this
		this.cloud_tenant_id = 0;
		this.uuid = UUID.randomUUID().toString();
		this.time_created = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTenant_vlan() {
		return tenant_vlan;
	}

	public void setTenant_vlan(int tenant_vlan) {
		this.tenant_vlan = tenant_vlan;
	}

	public int getIp_version() {
		return ip_version;
	}

	public void setIp_version(int ip_version) {
		this.ip_version = ip_version;
	}

	public String getIp_cidr() {
		return ip_cidr;
	}

	public void setIp_cidr(String ip_cidr) {
		this.ip_cidr = ip_cidr;
	}

	public int getSegment_id() {
		return segment_id;
	}

	public void setSegment_id(int segment_id) {
		this.segment_id = segment_id;
	}

	public int isDhcp_enabled() {
		return dhcp_enabled;
	}

	public void setDhcp_enabled(int dhcp_enabled) {
		this.dhcp_enabled = dhcp_enabled;
	}

	public int getCloud_tenant_id() {
		return cloud_tenant_id;
	}

	public void setCloud_tenant_id(int cloud_tenant_id) {
		this.cloud_tenant_id = cloud_tenant_id;
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
