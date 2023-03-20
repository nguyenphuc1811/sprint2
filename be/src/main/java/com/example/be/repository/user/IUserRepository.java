package com.example.be.repository.user;

import com.example.be.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Transactional
public interface IUserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select * from user where username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);


    @Modifying
    @Query(value = "update user set name = :name,phone_number = :phone_number,email = :email," +
            " address = :address,age = :age,gender = :gender,date_of_birth = :date_of_birth,avatar = :avatar" +
            " where username = :username  ", nativeQuery = true)
    void updateUser(@Param("name") String name, @Param("phone_number") String phoneNumber, @Param("email") String email
            , @Param("address") String address, @Param("age") Integer age, @Param("gender") Boolean gender
            , @Param("date_of_birth") String dateOfBirth, @Param("avatar") String avatar, @Param("username") String username);

    @Modifying
    @Query(value = "update user set password = :password where username = :username", nativeQuery = true)
    void changePassword(@Param("password") String password, @Param("username") String username);

    @Query(value = "select * from user", nativeQuery = true)
    List<User> getAllUser();

    @Modifying
    @Query(value = "insert into user (name,username,email,password) values (:name,:username,:email,:password)", nativeQuery = true)
    void save(@Param("name") String name, @Param("username") String username, @Param("email") String email, @Param("password") String password);

    @Modifying
    @Query(value = "insert into user_roles (user_id,roles_id) values (:user,:role)", nativeQuery = true)
    void insertRole(@Param("user") int userID, @Param("role") int roleID);

    @Query(value = "select * from user where username = :username", nativeQuery = true)
    User userLogin(@Param("username") String username);

    @Query(value = "select * from user join user_roles on user.id = user_roles.user_id join role r on user_roles.roles_id = r.id where r.name = 'ROLE_CUSTOMER'", nativeQuery = true)
    List<User> findAllCustomer();

    @Query(value = "select * from user join user_roles on user.id = user_roles.user_id join role r on user_roles.roles_id = r.id where r.name = 'ROLE_EMPLOYEE'", nativeQuery = true)
    List<User> findAllEmployee();

    @Query(value = "select * from user join user_roles on user.id = user_roles.user_id join role r on user_roles.roles_id = r.id where r.name = 'ROLE_ADMIN'", nativeQuery = true)
    List<User> findAllAdmin();

    @Query(value = "select `user`.* " +
            " from `user` " +
            "         join `user_roles` on `user`.id = `user_roles`.user_id " +
            "         join `role` on `role`.id = `user_roles`.roles_id " +
            "where role.name = 'ROLE_CUSTOMER' " +
            "  and user.name like concat('%', :name, '%') " +
            "  and user.address like concat('%', :address, '%')"
            , nativeQuery = true)
    Page<User> findAllCustomer(Pageable pageable, @Param("name") String name, @Param("address") String address);

    @Query(value = "select `user`.*" +
            " from `user` " +
            "         join `user_roles` on `user`.id = `user_roles`.user_id " +
            "         join `role` on `role`.id = `user_roles`.roles_id " +
            "where role.name = 'ROLE_CUSTOMER' "
            , nativeQuery = true)
    Page<User> findAllCustomerNoParam(Pageable pageable);

    @Query(value = "select * from user join user_roles on user.id = user_roles.user_id join role on role.id = user_roles.roles_id where role.name = 'ROLE_CUSTOMER' and user.id = :id",
            countQuery = "select * from user join user_roles on user.id = user_roles.user_id join role on role.id = user_roles.roles_id where role.name = 'ROLE_CUSTOMER' and user.id = :id"
            , nativeQuery = true)
    Optional<User> findCustomerById(@Param("id") Integer id);

    @Query(value = "select u.* from `user` u join `user_roles` ur on u.id = ur.user_id join `role` r on ur.roles_id = r.id  where r.name = 'ROLE_CUSTOMER' and u.gender like %:genderSearch% and u.age like %:ageSearch%", countQuery = "select * from `user` where gender like %:genderSearch% and age like %:ageSearch%", nativeQuery = true)
    Page<User> findAll(@Param("genderSearch") String genderSearch, @Param("ageSearch") String age, Pageable pageable);

    @Query(value = "select u.* from `user` u join `user_roles` ur on u.id = ur.user_id join `role` r on ur.roles_id = r.id  where r.name = 'ROLE_CUSTOMER' and u.gender = :genderSearch and u.age like %:ageSearch% ", countQuery = "select * from `user` where gender like %:genderSearch% and age like %:ageSearch%", nativeQuery = true)
    Page<User> findAllByGender(@Param("genderSearch") boolean genderSearch, @Param("ageSearch") String age, Pageable pageable);

    @Query(value = "select count(u.id) from `user` u join `bill` b on u.id = b.user_id " +
            "    join `bill_history` bh on b.id = bh.bill_id where u.id =:id group by u.id", nativeQuery = true)
    Integer selectQuantity(@Param("id") int id);

    @Query(value = "select u.* from `user` u join `bill` b on u.id = b.user_id join `bill_history` bh on b.id = bh.bill_id group by u.id", nativeQuery = true)
    List<User> getUserHasBuy();
}
