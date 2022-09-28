package com.revature.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.repository.entity.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

	@Query(value = "SELECT * FROM friend_requests WHERE receiver_id = :id", nativeQuery=true)
	ArrayList<FriendRequest> findAllByUserId(long id);

}
