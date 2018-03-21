package com.graduation.domain.view;

import lombok.Data;

import java.io.Serializable;
@Data
public class CollectSummary  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private String profilePicture;
	private Long favoritesId;
	private String url;
	private String title;
	private String description;
	private String logoUrl;
	private String type;
	private String remark;
	private Long lastModifyTime;
	private String userName;
	private Long favoriteId;
	private String favoriteName;
	private String collectTime;
	private String newFavorites;
	private Long praiseCount;
	private Long commentCount;
	private boolean isPraise;

	public CollectSummary(){

	}

	public CollectSummary(CollectView view) {
		this.id = view.getId();
		this.userId = view.getUserId();
		this.profilePicture = view.getProfilePicture();
		this.favoritesId = view.getFavoriteId();
		this.url = view.getUrl();
		this.title = view.getTitle();
		this.description = view.getDescription();
		this.logoUrl = view.getLogoUrl();
		this.type = view.getType();
		this.remark = view.getRemark();
		this.lastModifyTime = view.getLastModifyTime();
		this.userName = view.getUserName();
		this.favoriteId = view.getFavoriteId();
		this.favoriteName = view.getFavoriteName();
	}

}