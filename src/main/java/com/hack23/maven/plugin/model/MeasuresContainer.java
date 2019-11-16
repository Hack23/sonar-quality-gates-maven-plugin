package com.hack23.maven.plugin.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class MeasuresContainer.
 */
public final class MeasuresContainer {

	/** The measures. */
	private List<Measures> measures;

	/**
	 * Instantiates a new measures container.
	 */
	public MeasuresContainer() {
		super();
	}

	/**
	 * Gets the measures.
	 *
	 * @return the measures
	 */
	public List<Measures> getMeasures() {
		return Collections.unmodifiableList(measures);
	}

	/**
	 * Sets the measures.
	 *
	 * @param measures the new measures
	 */
	public void setMeasures(final List<Measures> measures) {
		this.measures = Collections.unmodifiableList(measures);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public boolean equals(final Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
