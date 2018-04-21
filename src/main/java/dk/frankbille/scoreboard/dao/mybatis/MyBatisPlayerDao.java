/*
 * ScoreBoard
 * Copyright (C) 2012-2013 Frank Bille
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dk.frankbille.scoreboard.dao.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dk.frankbille.scoreboard.dao.PlayerDao;
import dk.frankbille.scoreboard.domain.Player;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class MyBatisPlayerDao implements PlayerDao {

	private PlayerMapper playerMapper;

	@Autowired
	public MyBatisPlayerDao(PlayerMapper playerMapper) {
		this.playerMapper = playerMapper;
	}

	@Override
	public void savePlayer(Player player) {
		if (player.getId() == null) {
			System.out.println("clicked on save button51"+player.getName()+player.getGroupName());
			playerMapper.insertPlayer(player);
			System.out.println("clicked on save button52");
		} else {
			System.out.println("clicked on save button53"+player.getName()+player.getGroupName());
			playerMapper.updatePlayer(player);
			System.out.println("clicked on save button54");
		}
	}

	@Override//edited by Madhuri
	public void deletePlayer(Player player) {
		if (player.getId() == null) {
			System.out.println("clicked on delete button51"+player.getName()+player.getGroupName());
		//	playerMapper.insertPlayer(player);
			playerMapper.deletePlayer(player);
		} else {
			System.out.println("clicked on delete button52"+player.getName()+player.getGroupName());
			playerMapper.deletePlayer(player);
			System.out.println("clicked on delete button53");
		}
	}
	@Override
	public List<Player> getAllPlayers() {
		return playerMapper.getPlayers();
	}

	@Override
	public Player getPlayer(Long playerId) {
		return playerMapper.getPlayer(playerId);
	}
	
	@Override
	public List<Player> searchPlayers(String term) {
		return playerMapper.searchPlayers(term);
	}

}
