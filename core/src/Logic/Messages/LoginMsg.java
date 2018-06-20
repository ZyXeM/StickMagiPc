package Logic.Messages;

import Logic.Model.Account;

public class LoginMsg extends MessagePackage {
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
