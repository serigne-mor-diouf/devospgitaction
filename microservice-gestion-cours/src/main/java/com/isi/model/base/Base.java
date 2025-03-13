package com.isi.model.base;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Base implements Serializable{
		
	/**git branch -d nom_de_la_branche

	 *
	 */

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false)
	protected Long id;

	@Column(name = "created_date", updatable = false)
	@Temporal(TIMESTAMP)
	@CreatedDate
	@JsonIgnore
	protected Date creationDate;

	@Column(name = "modified_date")
	@LastModifiedDate
	@Temporal(TIMESTAMP)
	@JsonIgnore
	protected Date lastModifiedDate;

	@CreatedBy
	@Column(name="created_by", updatable = false)
	@JsonIgnore
	protected String createdBy;

	@LastModifiedBy
	@Column(name="modified_by")
	@JsonIgnore
	protected String modifiedBy;

	@Column(name = "optlock", nullable = false)
	@Version
	@JsonIgnore
	private long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void set(long l) {
	}

}