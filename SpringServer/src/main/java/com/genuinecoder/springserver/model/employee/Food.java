package com.genuinecoder.springserver.model.employee;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String detail;
	private String type;
	private String time;
	@Lob
	private byte[] image;
	private String video;
	private String guide;
	private String ingredient;
	private String nutrition;
	private String base64;
	private int idAut;
	
	public Food() {
		super();
	}


	public Food(int id, String name, String detail, String type, String time, byte[] image, String video, String guide,
			String ingredient, String nutrition, String base64, int idAut) {
		super();
		this.id = id;
		this.name = name;
		this.detail = detail;
		this.type = type;
		this.time = time;
		this.image = image;
		this.video = video;
		this.guide = guide;
		this.ingredient = ingredient;
		this.nutrition = nutrition;
		this.base64 = base64;
		this.idAut = idAut;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	

	public String getNutrition() {
		return nutrition;
	}



	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}

	

	public String getDetail() {
		return detail;
	}



	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getIdAut() {
		return idAut;
	}

	public void setIdAut(int idAut) {
		this.idAut = idAut;
	}
	
	
	public String getVideo() {
		return video;
	}



	public void setVideo(String video) {
		this.video = video;
	}



	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", detail=" + detail + ", type=" + type + ", time=" + time
				+ ", image=" +  ", video=" + video + ", guide=" + guide + ", ingredient=" + ingredient
				+ ", nutrition=" + nutrition  + ", idAut=" + idAut + "]";
	}

	
	
}
