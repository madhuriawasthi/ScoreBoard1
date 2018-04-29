package dk.frankbille.scoreboard.components;

import dk.frankbille.scoreboard.domain.Game;
import dk.frankbille.scoreboard.domain.Player;
import dk.frankbille.scoreboard.domain.PlayerResult;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.list.ListItem;

import java.util.Set;

public class MouseOverBehavior extends Behavior {
	private static final long serialVersionUID = 1L;
	private String x = ""; 
	private String tableName = "palyerByRating";
	
	MouseOverBehavior(ListItem<?> item, String tableName, String fromClass){
		
		if(fromClass.equals("Game")){
			Set<Player> playerName1 = ((Game) item.getModelObject()).getTeam1().getTeam().getPlayers();
			Set<Player> playerName2 = ((Game) item.getModelObject()).getTeam2().getTeam().getPlayers();
			this.tableName = tableName;
			
			for(Player a : playerName1){
				System.out.println(""+a.getId()+a.getName());
				x = x + a.getName()+"-";
			}
			for(Player b : playerName2){
				System.out.println(""+b.getId()+b.getName());
				x = x + b.getName()+"-";
			}
			x = x + "zzzzz";
		} else if(fromClass.equals("PlayerResult")){
			this.tableName = tableName;
			x = ((PlayerResult) item.getModelObject()).getPlayer().getName();
		}
	}
	
	@Override
    public void bind(Component component) {
        component.add(AttributeModifier.replace("onmouseover", "highlightOnOntherTable('"+x+"','"+tableName+"')"));
    }
}

