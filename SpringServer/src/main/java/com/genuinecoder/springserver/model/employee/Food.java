package com.genuinecoder.springserver.model.employee;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String detail;
	private String time;
	@Lob
	private byte[] image;
	private String video;
	private String guide;
	private String ingredient;
	private String nutrition;
	private String base64;
	private int idAut;
	@ManyToMany
	@JoinTable(
			name = "food_typefood",
			joinColumns = @JoinColumn(name = "food_id"),
			inverseJoinColumns = @JoinColumn(name = "typefood_id")
	)
	private Set<TypeFood> typefoods = new HashSet<>();

	public Food() {
	}

	public Food(String name, String detail, String time, byte[] image, String video, String guide,
				String ingredient, String nutrition, String base64, int idAut) {
		this.name = name;
		this.detail = detail;
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

	public Set<TypeFood> getTypefoods() {
		return typefoods;
	}

	public void setTypefoods(Set<TypeFood> typefoods) {
		this.typefoods = typefoods;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", detail=" + detail + ", time=" + time
				+ ", image=" +  ", video=" + video + ", guide=" + guide + ", ingredient=" + ingredient
				+ ", nutrition=" + nutrition  + ", idAut=" + idAut + "]";
	}

	
	
}
