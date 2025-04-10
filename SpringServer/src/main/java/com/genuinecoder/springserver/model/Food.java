package com.genuinecoder.springserver.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data // Lombok annotation để tự động sinh các Getter, Setter, toString, equals, hashCode
@NoArgsConstructor // Lombok tạo constructor mặc định
@AllArgsConstructor // Lombok tạo constructor với tất cả các trường
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Food {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String detail;

	private String time;

	@Lob
	private byte[] image;

	private String video;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String guide;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String ingredient;

	private String nutrition;

	private int idAut;

	@ManyToMany(fetch = FetchType.LAZY) // Đổi sang LAZY để tránh tải quá nhiều dữ liệu không cần thiết
	@JoinTable(
			name = "food_typefood",
			joinColumns = @JoinColumn(name = "food_id"),
			inverseJoinColumns = @JoinColumn(name = "typefood_id")
	)
	private Set<TypeFood> typefoods = new HashSet<>();

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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
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

	public String getNutrition() {
		return nutrition;
	}

	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}

	public int getIdAut() {
		return idAut;
	}

	public void setIdAut(int idAut) {
		this.idAut = idAut;
	}

	public Set<TypeFood> getTypefoods() {
		return typefoods;
	}

	public void setTypefoods(Set<TypeFood> typefoods) {
		this.typefoods = typefoods;
	}
}
