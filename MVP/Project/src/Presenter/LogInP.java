package Presenter;

import Model.User;
import Model.UserPersistence;
import Model.Users;
import View.ILogInView;
import javafx.event.ActionEvent;

public class LogInP {
    private static ILogInView view;
    private static String loggedUser;

    public LogInP() {
    }

    public static String getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(String loggedUser) {
        LogInP.loggedUser = loggedUser;
    }

    public int logIn(ILogInView view, ActionEvent actionEvent) {
        UserPersistence userPersistence = new UserPersistence();
        this.setView(view);
        Users users = userPersistence.readUsers();
        for (User user : users.getUsersList()) {
            if (this.view.getUsernameField().equals(user.getName())) {
                if (view.getPasswordField().equals(user.getPassword())) {
                    loggedUser = user.getId();
                    if (user.getRole().equals("employee")) {
                        return 1;
                    } else {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    public ILogInView getView() {
        return view;
    }

    public void setView(ILogInView view) {
        this.view = view;
    }
}
