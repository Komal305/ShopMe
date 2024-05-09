package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
@Autowired
private UserRepository repo;
@Autowired
private TestEntityManager entityManager;
@Test
public void testCreateUser() {
	Role roleAdmin= entityManager.find(Role.class,1);
	User userNamHM=new User("komal@gmail.com", "Komal2861", "komal", "rani");
userNamHM.addRoles(roleAdmin);
User savedUser=repo.save(userNamHM);
assertThat(savedUser.getId()).isGreaterThan(0);
}

@Test
public void testCreateNewUserWithTwoRoles() {
	User userNikhil=new User("nikhil@gmail.com","Nikhil04","nikhil","naitik");
    Role roleEditor=new Role(3);
    Role roleAssitant=new Role(5);
    
    
   // userNikhil.addRoles((Role) List.of(roleAssitant, roleEditor));
    userNikhil.addRoles(roleAssitant);
    userNikhil.addRoles(roleEditor);
    
    User savedUser = repo.save(userNikhil);
    assertThat(savedUser.getId()).isGreaterThan(0);
}

@Test
public void testListAllUsers() {
	Iterable<User> listUser=repo.findAll();
	listUser.forEach(user -> System.out.println(user));
}

@Test
public void testGetUserById() {
	User userNam=repo.findById(1).get();
	System.out.println(userNam);
	assertThat(userNam).isNotNull();
}

}
