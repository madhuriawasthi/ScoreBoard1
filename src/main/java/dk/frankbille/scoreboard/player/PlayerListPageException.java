/*
 * ScoreBoard
 * Copyright (C) 2012-2018 Frank Bille
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

package dk.frankbille.scoreboard.player;

import dk.frankbille.scoreboard.BasePage;
import dk.frankbille.scoreboard.components.menu.MenuItemType;
import dk.frankbille.scoreboard.domain.Player;
import dk.frankbille.scoreboard.security.SecureRenderingLink;
import dk.frankbille.scoreboard.service.ScoreBoardService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class PlayerListPageException extends BasePage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private ScoreBoardService scoreBoardService;

	public PlayerListPageException() {
		add(new SecureRenderingLink<Void>("addNewPlayerLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {


				getRequestCycle().setResponsePage(new PlayerEditPage(new Model<Player>(new Player())));

			}
		});
		// Edited by Madhuri
		add(new SecureRenderingLink<Void>("deletePlayerLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {

				getRequestCycle().setResponsePage(new PlayerDeletePage(new Model<Player>(new Player())));

			}

		});
		// Edited end
		IModel<List<Player>> playerListModel = new LoadableDetachableModel<List<Player>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Player> load() {
				return scoreBoardService.getAllPlayers();
			}
		};


		add(new ListView<Player>("players", playerListModel) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Player> item) {
				PageParameters parameters = new PageParameters();
				parameters.set(0, item.getModelObject().getId());
				BookmarkablePageLink<Player> link = new BookmarkablePageLink<Player>("playerLink", PlayerPage.class, parameters);
				link.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
				item.add(link);

				item.add(new Label("fullName", new PropertyModel<String>(item.getModel(), "fullName")));
				item.add(new Label("groupName", new PropertyModel<String>(item.getModel(), "groupName")));
			}
		});
	}

	@Override
	public MenuItemType getMenuItemType() {
		return MenuItemType.PLAYERS;
	}

}
