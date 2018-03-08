package com.graduation.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 消息
 * 
 * @author JiangL
 * 
 */
@Entity
@Data
public class Notice extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = true)
	private String collectId;
	@Column(nullable = false)
	private String type;
	@Column(nullable = true)
	private String operId;
	@Column(nullable = false)
	private String readed;
	@Column(nullable = false)
	private Long createTime;

	public Notice() {
		super();
	}

}