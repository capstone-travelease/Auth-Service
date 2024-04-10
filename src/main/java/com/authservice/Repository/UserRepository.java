package com.authservice.Repository;

import com.authservice.DTO.UserLoginDTO;
import com.authservice.Entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
  @Query("Select u from Users u WHERE u.email = ?1 ")
  Optional<Users> findByEmail(String email);

  @Query(value = "Select new com.authservice.DTO.UserLoginDTO(u.user_id, u.email, u.full_name,u.password, u.role_id) from Users u WHERE u.email = ?1 AND u.role_id = ?2")
  UserLoginDTO generateUser(String email, Integer roleId);

  @Query(value = "Select u.email from Users u where u.email = ?1")
  String findEmail(String email);

  @Query(value = "INSERT INTO public.users(\n" +
          "\tgender, role_id, email, full_name, password, phone_number, dob)\n" +
          "\tVALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7) RETURNING user_id",nativeQuery = true)
  Integer addUser(boolean gender, Integer roleId, String email, String fullName, String password, String phoneNumber, Date dob);

  @Modifying
  @Transactional
  @Query(value = "INSERT INTO public.identify_card(\n" +
          "\tuser_id, front, back)\n" +
          "\tVALUES ( ?1, ?2, ?3)",nativeQuery = true)
  void addIndentifyCard(Integer userId, String frontImage, String backImage);
}
