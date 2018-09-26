package hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.model.SysUser;

public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByUsername(String username);
}