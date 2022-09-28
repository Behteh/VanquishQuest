package com.revature.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.NoFriendsException;
import com.revature.repository.FriendRepository;
import com.revature.repository.entity.Friend;

@Service
public class FriendService {
	
	private FriendRepository friendRepository;

	@Autowired
	public FriendService(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}
	
	public Optional<Friend> findById(Long id)
	{
		return friendRepository.findById(id);
	}
	
	public List<Friend> findAllFriendsByUser1(long user1) throws NoFriendsException
	{
		Optional<List<Friend>> friends = friendRepository.findAllByUser1(user1);
		if(friends.isPresent())
		{
			return friends.get();
		}
		throw new NoFriendsException();
	}
	
	public Friend save(Friend friend)
	{
		return friendRepository.save(friend);
	}
	
	public void deleteById(Long id)
	{
		friendRepository.deleteById(id);
	}
	
	public boolean exists(long id)
	{
		return friendRepository.existsById(id);
	}
}
