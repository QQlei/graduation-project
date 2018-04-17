package com.graduation.domain;

import com.graduation.domain.enums.CollectType;
import com.graduation.domain.enums.IsDelete;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 收藏
 */
@Entity
@Data
public class Collect  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private Long favoritesId;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private String title;

	@Column(nullable = true, length = 65535, columnDefinition = "Text")
	private String description;

	@Column(nullable = true)
	private String logoUrl;

	@Column(nullable = true)
	private String charset;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private CollectType type;

	@Column(nullable = true)
	private String remark;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING) 
	private IsDelete isDelete;

	@Column(nullable = false)
	private Long createTime;

	@Column(nullable = false)
	private Long lastModifyTime;

	@Column(nullable = true)
	private String category;

	@Transient
	private String collectTime;

	@Transient
	private String newFavorites;

	public Collect() {
		super();
	}


}