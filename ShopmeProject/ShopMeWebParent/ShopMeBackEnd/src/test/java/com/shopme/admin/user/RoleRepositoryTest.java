package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleadmin = new Role("Admin", "manage everything");
          Role savedRole = repo.save(roleadmin);	
          assertThat(savedRole.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateRestRole() {
		Role roleSalseperson = new Role("Salseperson", "manage product price, customer, shipping, oredrs and sales report");
		Role roleEditor = new Role("Editor", "manage categories, brands, products, articles and menus");
		Role roleShipper = new Role("Shipper", "view product, view oredrs and update order status");
		Role roleAssistant = new Role("Assistant", "manage questions and review");
		
		 repo.saveAll(List.of(roleSalseperson,roleEditor, roleShipper, roleAssistant));	
	}
	

}
