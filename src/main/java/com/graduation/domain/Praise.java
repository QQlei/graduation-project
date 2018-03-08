package com.graduation.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 点赞
 * 
 * @author JiangL
 * 
 */
@Entity
@Data
public class Praise extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private Long collectId;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private Long createTime;

	public Praise() {
		super();
	}


}