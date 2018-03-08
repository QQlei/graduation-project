package com.graduation.domain;

import com.graduation.domain.enums.LetterType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 私信
 * 
 * @author JiangL
 * 
 */
@Entity
@Data
public class Letter extends Entitys implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable = false)
	private Long sendUserId;
	@Column(nullable = false, length = 65535, columnDefinition = "Text")
	private String content;
	@Column(nullable = false)
	private Long receiveUserId;
	@Column(nullable = true)
	private Long pid;
	@Enumerated(EnumType.STRING)
	@Column(nullable = true)
	private LetterType type;
	@Column(nullable = false)
	private Long createTime;
	@Transient
	private String sendType;


	public Letter() {
		super();
	}

}