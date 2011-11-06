package org.springframework.roo.project;

import org.springframework.roo.metadata.AbstractMetadataItem;
import org.springframework.roo.metadata.MetadataIdentificationUtils;
import org.springframework.roo.project.maven.Pom;
import org.springframework.roo.support.style.ToStringCreator;
import org.springframework.roo.support.util.Assert;
import org.springframework.roo.support.util.StringUtils;

/**
 * The metadata for a Maven module within the user's project. A multi-module
 * project will have several instances of this class.
 *
 * @since 1.0
 */
public class ProjectMetadata extends AbstractMetadataItem {

	// Constants
	static final String MODULE_SEPARATOR = "?";
	static final String PROJECT_MID_PREFIX = MetadataIdentificationUtils.create(ProjectMetadata.class.getName(), "the_project");

	/**
	 * Returns the metadata ID for the project-level metadata of the given module.
	 * 
	 * @param moduleName can be blank for the root or only module
	 * @return a non-blank MID
	 */
	public static String getProjectIdentifier(final String moduleName) {
		final StringBuilder sb = new StringBuilder(PROJECT_MID_PREFIX);
		if (StringUtils.hasText(moduleName)) {
			sb.append(MODULE_SEPARATOR).append(moduleName);
		}
		return sb.toString();
	}

	public static boolean isValid(final String metadataIdentificationString) {
		return metadataIdentificationString.startsWith(PROJECT_MID_PREFIX);
	}

	public static String getModuleName(final String metadataIdentificationString) {
		if (metadataIdentificationString.contains(MODULE_SEPARATOR)) {
			return StringUtils.substringAfterLast(metadataIdentificationString, MODULE_SEPARATOR);
		}
		return "";
	}
	
	// Fields
	private final Pom pom;

	/**
	 * Constructor
	 *
	 * @param pom (required)
	 */
	public ProjectMetadata(final Pom pom) {
		super(getProjectIdentifier(pom.getModuleName()));
		Assert.notNull(pom, "POMs required");
		this.pom = pom;
	}

	public Pom getPom() {
		return pom;
	}

	public String getModuleName() {
		return pom.getModuleName();
	}

	@Override
	public final String toString() {
		final ToStringCreator tsc = new ToStringCreator(this);
		tsc.append("identifier", getId());
		tsc.append("valid", isValid());
		tsc.append("pom", pom);
		return tsc.toString();
	}
}
