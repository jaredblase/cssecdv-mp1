package Controller;

import View.ForgotPassword;

public class ForgotPasswordController {
    public ForgotPasswordController(Main main, ForgotPassword view) {
        view.setBackListener(e -> main.showPanel(Panel.LOGIN));
    }
}
