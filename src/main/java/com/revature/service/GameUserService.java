package com.revature.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.GameUserAlreadyExistsException;
import com.revature.repository.GameUserRepository;
import com.revature.repository.entity.GameUser;

@Service
public class GameUserService {
	
	private GameUserRepository gameUserRepository;

	@Autowired
	public GameUserService(GameUserRepository gameUserRepository) {
		this.gameUserRepository = gameUserRepository;
	}
	
	public boolean exists(long id)
	{
		return gameUserRepository.existsById(id);
	}
	
	public Optional<GameUser> findById(Long id)
	{
		return gameUserRepository.findById(id);
	}
	
	public Optional<GameUser> findByUsernameAndPassword(String username, String password)
	{
		return gameUserRepository.findGameUserByUsernameAndPassword(username, password);
	}
	
	public GameUser register(String username, String password) throws GameUserAlreadyExistsException
	{
		Optional<GameUser> userInfo = gameUserRepository.findGameUserByUsername(username);
		if(userInfo.isPresent())
		{
			throw new GameUserAlreadyExistsException("Username is already taken");
		}
		else
		{
			try
			{
				 return gameUserRepository.save(new GameUser(username, password));
			}
			catch(Exception e)
			{
				System.out.println("Error creating user");
				e.printStackTrace();
			}
		}
		throw new GameUserAlreadyExistsException("Unknown exception occured");
	}
	
	public GameUser save(GameUser gameUser)
	{
		return gameUserRepository.save(gameUser);
	}
	
	public void deleteById(Long id)
	{
		gameUserRepository.deleteById(id);
	}

}