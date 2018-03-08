package com.graduation.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 属性设置
 * 
 * @author JiangL
 * 
 */
@Entity
@Data
public class Config extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private String defaultFavorties;
	@Column(nullable = false)
	private String defaultCollectType;
	@Column(nullable = false)
	private String defaultModel;
	@Column(nullable = false)
	private Long createTime;
	@Column(nullable = false)
	private Long lastModifyTime;
	@Transient
	private String collectTypeName;
	@Transient
	private String modelName;

	public Config() {
		super();
	}

}