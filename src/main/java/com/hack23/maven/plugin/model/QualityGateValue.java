package com.hack23.maven.plugin.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class QualityGateValue.
 */
public final class QualityGateValue {
	
	/** The ignored conditions. */
	private String ignoredConditions;

	/** The level. */
	private String level;

	/** The conditions. */
	private List<Conditions> conditions;

	/**
	 * Gets the ignored conditions.
	 *
	 * @return the ignored conditions
	 */
	public String getIgnoredConditions() {
		return ignoredConditions;
	}

	/**
	 * Sets the ignored conditions.
	 *
	 * @param ignoredConditions the new ignored conditions
	 */
	public void setIgnoredConditions(final String ignoredConditions) {
		this.ignoredConditions = ignoredConditions;
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
	 * Gets the conditions.
	 *
	 * @return the conditions
	 */
	public List<Conditions> getConditions() {
		return Collections.unmodifiableList(conditions);
	}

	/**
	 * Sets the conditions.
	 *
	 * @param conditions the new conditions
	 */
	public void setConditions(final List<Conditions> conditions) {
		this.conditions = Collections.unmodifiableList(conditions);
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