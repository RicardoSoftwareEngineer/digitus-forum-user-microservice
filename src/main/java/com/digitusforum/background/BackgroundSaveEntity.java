package com.digitusforum.background;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BackgroundSave")
public class BackgroundSaveEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	private String userId;
	private String name;
	@Lob
	@Column(columnDefinition = "MEDIUMTEXT")
	private String wallpaperData;
	private String dominantColor;
	private ZonedDateTime createdIn;
	private boolean deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWallpaperData() {
		return wallpaperData;
	}

	public void setWallpaperData(String wallpaperData) {
		this.wallpaperData = wallpaperData;
	}

	public String getDominantColor() {
		return dominantColor;
	}

	public void setDominantColor(String dominantColor) {
		this.dominantColor = dominantColor;
	}

	public ZonedDateTime getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(ZonedDateTime createdIn) {
		this.createdIn = createdIn;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
