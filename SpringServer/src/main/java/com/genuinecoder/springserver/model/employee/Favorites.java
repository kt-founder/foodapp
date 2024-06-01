package com.genuinecoder.springserver.model.employee;

import javax.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idUser;  // ID của người dùng

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;  // Tham chiếu đến thực thể Food

    // Constructors, getters, and setters
    public Favorites() {}
    
    
	public Favorites(int id, int idUser, Food food) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.food = food;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
    
}