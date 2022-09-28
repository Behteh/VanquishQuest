package com.revature.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.repository.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	Optional<List<Friend>> findAllByUser1(long user1);

}
