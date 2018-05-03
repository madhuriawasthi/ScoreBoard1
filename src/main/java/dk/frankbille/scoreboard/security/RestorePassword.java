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

package dk.frankbille.scoreboard.security;

import dk.frankbille.scoreboard.BasePage;
import dk.frankbille.scoreboard.components.menu.MenuItemType;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import dk.frankbille.scoreboard.service.ScoreBoardService;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class RestorePassword extends BasePage {
    @SpringBean
    private ScoreBoardService scoreBoardService;
    @Override
    public MenuItemType getMenuItemType() {
        return MenuItemType.SECURE;
    }

    public RestorePassword(){
        resetPassword();
    }

    private void resetPassword() {
        final LoginPage.UpdateUser user = new LoginPage.UpdateUser();
        final Form<Void> updatePasswordForm = new Form<Void>("resetPasswordForm") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit() {

                scoreBoardService.updatePassword(user.getUsername(), user.getPassword());
                setResponsePage(LoginPage.class);


            }
        };
        add(updatePasswordForm);

        updatePasswordForm.add(new FeedbackPanel("restorepassword", new ContainerFeedbackMessageFilter(updatePasswordForm)));

        final TextField<String> usernameField = new TextField<String>("usernameField", new PropertyModel<String>(user, "username"));
        usernameField.setRequired(true);
        updatePasswordForm.add(usernameField);

        final PasswordTextField passwordField = new PasswordTextField("passwordField", new PropertyModel<String>(user, "password"));
        passwordField.setRequired(true);
        updatePasswordForm.add(passwordField);

        final PasswordTextField repeatPasswordField = new PasswordTextField("repeatPasswordField", new PropertyModel<String>(user, "repeatPassword"));
        updatePasswordForm.add(repeatPasswordField);

        updatePasswordForm.add(new EqualPasswordInputValidator(passwordField, repeatPasswordField));
    }

}
