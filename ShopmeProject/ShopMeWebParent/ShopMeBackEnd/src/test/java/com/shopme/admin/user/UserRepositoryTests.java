package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@EnableJpaRepositories
public class UserRepositoryTests {
	
@Autowired 
private UserRepository repo;
@Autowired
private TestEntityManager entityManager;


//@Test
//public void testCreateUser() {
//	Role roleAdmin= entityManager.find(Role.class,1);
//	User userNamHM=new User("komal@gmail.com", "Komal2861", "komal", "rani");
//userNamHM.addRoles(roleAdmin);
//User savedUser=repo.save(userNamHM);
//assertThat(savedUser.getId()).isGreaterThan(0);
//}

//@Test
//public void testCreateNewUserWithTwoRoles() {
//	User userNikhil=new User("nikhil@gmail.com","Nikhil04","nikhil","naitik");
//    Role roleEditor=new Role(3);
//    Role roleAssitant=new Role(5);
//    
//    
//   // userNikhil.addRoles((Role) List.of(roleAssitant, roleEditor));
//    userNikhil.addRoles(roleAssitant);
//    userNikhil.addRoles(roleEditor);
//    
//    User savedUser = repo.save(userNikhil);
//    assertThat(savedUser.getId()).isGreaterThan(0);
//}
//
//@Test
//public void testListAllUsers() {
//	Iterable<User> listUser=repo.findAll();
//	listUser.forEach(user -> System.out.println(user));
//}
//
//@Test
//public void testGetUserById() {
//	User userNam=repo.findById(1).get();
//	System.out.println(userNam);
//	assertThat(userNam).isNotNull();
//}
//@Test
//public void testUpdateDetails() {
//	User userNam=repo.findById(1).get();
//	userNam.setEnabled(true);
//	userNam.setEmail("komal123@gmail.com");
//	repo.save(userNam);	
//}
//
//@Test
//public void testUpdateUserRole() {
//	User userRavi=repo.findById(15).get();
//	Role roleEditor=new Role(5);
//	Role roleSalesperson= new Role(6);
//	userRavi.getRoles().remove(roleEditor);
//	userRavi.addRoles(roleSalesperson);
//	repo.save(userRavi);
//}
//
//@Test
//public void testDeleteUser() {
//	Integer userId=21;
//	repo.deleteById(userId);
//}

//@Test
//public void testGetUserByEmail() {
//	String email="nikhil@gmail.com";
//	User user = repo.getUserByEmail(email);
//	assertThat(user).isNotNull();
//}

//@Test
//public void testCountById() {
//	Integer id=38;
//	Long countById = repo.countById(id);
//	assertThat(countById).isNotNull().isGreaterThan(0);
//}

//@Test
//public void testDisableUser() {
//	Integer id =43;
//	repo.updateEnabledStatus(id, false);
//}

//@Test
//public void testEnableUser() {
//	Integer id =44;
//	repo.updateEnabledStatus(id, true);
//}

//@Test
//public void testListFirstPage() {
//	int pageNumber=0;
//	int pageSize=4;
//	 Pageable pageable=PageRequest.of(pageNumber, pageSize);
//	 Page<User> page= repo.findAll(pageable);
//	 
//	 List<User> listUsers = page.getContent();
//	 listUsers.forEach(user -> System.out.println(user));
//	 assertThat(listUsers.size()).isEqualByComparingTo(pageSize);
//}

@Test
public void testSearchUsers() {
	String keyword="Arya";
	int pageNumber=0;
	int pageSize=4;
	 Pageable pageable=PageRequest.of(pageNumber, pageSize);
	 Page<User> page= repo.findAll(keyword, pageable);
	 
	 List<User> listUsers = page.getContent();
	 listUsers.forEach(user -> System.out.println(user));
	 assertThat(listUsers.size()).isGreaterThan(0);
}

}
