package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
      private Integer id;
	
	@Column(length = 128, nullable =false, unique = true )
	private String email ;
	@Column(length = 20, nullable =false)
	private String password;
	@Column(length = 20, nullable =false)
	private String firstName;
	@Column(length = 20, nullable =false)
	private String lastName ;
	@Column(length = 64)
	private String photos;
	private boolean enabled ;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="user_roles", 
	joinColumns= @JoinColumn(name="user_id"),
	inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles=new HashSet<>();

	
	
	public User() {
	}
	public User( String email, String password, String firstName, String lastName) {
		
		
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		
	}
	public User(Integer id, String email, String password, String firstName, String lastName, String photos,
			boolean enabled, Set<Role> roles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photos = photos;
		this.enabled = enabled;
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public void addRoles(Role roles) {
		this.roles.add(roles);
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", roles=" + roles + "]";
	}
	
	@Transient
	public String getPhotosImagePath() {
		if(id==null || photos==null) return "/images/default-image.png";
		
		return "/user-photos/"+this.id+"/"+this.photos;
	}
	
	@Transient
	public String getFullName() {
		return firstName+" "+lastName;
	}
	

}
