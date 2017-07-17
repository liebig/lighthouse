package de.liebig.lighthouse.software;

import org.springframework.stereotype.Service;

@Service
public class SoftwareService {

	public String getUserData(SoftwareEnum software) {
		switch (software) {
		case APACHE:
			return "#cloud-config\r\n" + //
					"packages:\r\n" + //
					"  - apache2";
		case NGINX:
			return "#cloud-config\r\n" + //
					"packages:\r\n" + //
					"  - nginx";
		default:
			throw new UnsupportedOperationException("No user data for software type " + software.name() + " defined");
		}
	}

}
