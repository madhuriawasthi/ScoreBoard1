package dk.frankbille.scoreboard.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

public class MouseOutBehavior extends Behavior{
	private String tableName = "palyerByRating";
	
	MouseOutBehavior(String tableName) {
		this.tableName = tableName;
	}
	
	@Override
    public void bind(Component component) {
        component.add(AttributeModifier.replace("onmouseout", "removeHighlightedCssFromTable('"+tableName+"')"));
    }
}
