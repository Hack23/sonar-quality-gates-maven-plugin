package com.hack23.maven.plugin.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class Measures.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Measures {
	
	/** The component. */
	private String component;

	/** The metric. */
	private String metric;

	/** The value. */
	private String value;

	/**
	 * Instantiates a new measures.
	 */
	public Measures() {
		super();
	}

	/**
	 * Gets the component.
	 *
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}

	/**
	 * Sets the component.
	 *
	 * @param component the new component
	 */
	public void setComponent(final String component) {
		this.component = component;
	}

	/**
	 * Gets the metric.
	 *
	 * @return the metric
	 */
	public String getMetric() {
		return metric;
	}

	/**
	 * Sets the metric.
	 *
	 * @param metric the new metric
	 */
	public void setMetric(final String metric) {
		this.metric = metric;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(final String value) {
		this.value = value;
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
