package de.liebig.lighthouse.server;

import de.liebig.lighthouse.software.SoftwareEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerCreateDto {

	private String region;
	private String size;
	private SoftwareEnum softwareEnum;
	
}
