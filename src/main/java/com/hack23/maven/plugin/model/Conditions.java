package com.hack23.maven.plugin.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class Conditions.
 */
public final class Conditions {
	
	/** The op. */
	private String op;

	/** The period. */
	private String period;

	/** The metric. */
	private String metric;

	/** The level. */
	private String level;

	/** The error. */
	private String error;

	/** The actual. */
	private String actual;

	/**
	 * Instantiates a new conditions.
	 */
	public Conditions() {
		super();
	}

	/**
	 * Gets the actual.
	 *
	 * @return the actual
	 */
	public String getActual() {
		return actual;
	}

	/**
	 * Sets the actual.
	 *
	 * @param actual the new actual
	 */
	public void setActual(final String actual) {
		this.actual = actual;
	}

	/**
	 * Gets the op.
	 *
	 * @return the op
	 */
	public String getOp() {
		return op;
	}

	/**
	 * Sets the op.
	 *
	 * @param op the new op
	 */
	public void setOp(final String op) {
		this.op = op;
	}

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	public void setPeriod(final String period) {
		this.period = period;
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
	 * Gets the level.
	 *
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	public void setLevel(final String level) {
		this.level = level;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(final String error) {
		this.error = error;
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