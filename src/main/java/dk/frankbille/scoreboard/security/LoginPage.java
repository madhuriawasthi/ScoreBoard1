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

package dk.frankbille.scoreboard.security;

import dk.frankbille.scoreboard.domain.Player;
import dk.frankbille.scoreboard.player.PlayerDeletePage;
import dk.frankbille.scoreboard.player.PlayerEditPage;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.cookies.CookieDefaults;
import org.apache.wicket.util.cookies.CookieUtils;

import dk.frankbille.scoreboard.BasePage;
import dk.frankbille.scoreboard.ScoreBoardApplication;
import dk.frankbille.scoreboard.ScoreBoardSession;
import dk.frankbille.scoreboard.components.menu.MenuItemType;
import dk.frankbille.scoreboard.domain.User;
import dk.frankbille.scoreboard.service.ScoreBoardService;
import dk.frankbille.scoreboard.user.UsernameValidator;

public class LoginPage extends BasePage {
	private static final long serialVersionUID = 1L;
	private IModel<User> userModel;


	static class LoginUser extends User {
		private static final long serialVersionUID = 1L;

		private boolean loginPersistent;
		private String password;

		public boolean isLoginPersistent() {
			return loginPersistent;
		}

		public void setLoginPersistent(boolean loginPersistent) {
			this.loginPersistent = loginPersistent;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	static class CreateUser extends User {
		private static final long serialVersionUID = 1L;

		private String repeatPassword;
		private String password;

		public String getRepeatPassword() {
			return repeatPassword;
		}

		public void setRepeatPassword(String repeatPassword) {
			this.repeatPassword = repeatPassword;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}



	static class UpdateUser extends User {
		private static final long serialVersionUID = 1L;

		private String repeatPassword;
		private String password;

		public String getRepeatPassword() {
			return repeatPassword;
		}

		public void setRepeatPassword(String repeatPassword) {
			this.repeatPassword = repeatPassword;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}


	@SpringBean
	private ScoreBoardService scoreBoardService;

	public LoginPage() {
		addLogin();

		addCreateUser();
	}

	private void addLogin() {
		final LoginUser user = new LoginUser();
		final Form<Void> loginForm = new Form<Void>("loginForm") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				if (user.isLoginPersistent()) {
					CookieDefaults settings = new CookieDefaults();
					// 14 days
					settings.setMaxAge(60*60*24*14);
					CookieUtils cookieUtils = new CookieUtils(settings);
					cookieUtils.save("loginUsername", user.getUsername());
					cookieUtils.save("loginPassword", user.getPassword());
				}
				authenticated();
			}
		};




		add(loginForm);

		loginForm.add(new FeedbackPanel("loginMessages", new ContainerFeedbackMessageFilter(loginForm)));

		final TextField<String> usernameField = new TextField<String>("usernameField", new PropertyModel<String>(user, "username"));
		loginForm.add(usernameField);

		final PasswordTextField passwordField = new PasswordTextField("passwordField", new PropertyModel<String>(user, "password"));
		loginForm.add(passwordField);

		CheckBox persistentField = new CheckBox("persistentField", new PropertyModel<Boolean>(user, "loginPersistent"));
		loginForm.add(persistentField);


		loginForm.add(new AbstractFormValidator() {
			private static final long serialVersionUID = 1L;

			@Override
			public FormComponent<?>[] getDependentFormComponents() {
				return new FormComponent<?>[] {
						usernameField,
						passwordField
				};
			}

			@Override
			public void validate(Form<?> form) {
				boolean authenticated = ScoreBoardSession.get().authenticate(usernameField.getInput(), passwordField.getInput());
				if (false == authenticated) {
					error(usernameField, "incorrectUsernamePassword");
				}
			}
		});

		loginForm.add(new Button("restoreP") {
			@Override
			public void onSubmit()
			{
				setResponsePage(RestorePassword.class);
			}
		}.setDefaultFormProcessing(false));
	}

	private void addCreateUser() {
		final CreateUser user = new CreateUser();
		final Form<Void> createUserForm = new Form<Void>("createUserForm") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				scoreBoardService.createUser(user, user.getPassword());
				ScoreBoardSession.get().authenticate(user.getUsername(), user.getPassword());
				authenticated();
			}
		};
		add(createUserForm);

		createUserForm.add(new FeedbackPanel("createUserMessages", new ContainerFeedbackMessageFilter(createUserForm)));

		final TextField<String> usernameField = new TextField<String>("usernameField", new PropertyModel<String>(user, "username"));
		usernameField.add(new UsernameValidator(new Model<User>(user)));
		usernameField.setRequired(true);
		createUserForm.add(usernameField);

		final PasswordTextField passwordField = new PasswordTextField("passwordField", new PropertyModel<String>(user, "password"));
		passwordField.setRequired(true);
		createUserForm.add(passwordField);

		final PasswordTextField repeatPasswordField = new PasswordTextField("repeatPasswordField", new PropertyModel<String>(user, "repeatPassword"));
		createUserForm.add(repeatPasswordField);

		createUserForm.add(new EqualPasswordInputValidator(passwordField, repeatPasswordField));
	}

	@Override
	public MenuItemType getMenuItemType() {
		return MenuItemType.SECURE;
	}

	private void authenticated() {
		if (ScoreBoardSession.get().isAuthenticated()) {
			continueToOriginalDestination();
		}
	}

}
