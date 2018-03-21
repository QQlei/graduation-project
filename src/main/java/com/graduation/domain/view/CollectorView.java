package com.graduation.domain.view;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Description:
 * @Auth: JiangL
 **/
@Entity
@Data
public class CollectorView {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="userId",length=20)
    private Long userId;
    @Column(name="counts",length=20)
    private Long counts;

}
