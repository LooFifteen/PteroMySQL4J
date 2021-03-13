package dev.sllcoding.pteromysql4j.ptero.api;

import com.mattmalec.pterodactyl4j.entities.impl.PteroAPIImpl;
import dev.sllcoding.pteromysql4j.ptero.auth.Account;

public class PteroApi extends PteroAPIImpl {

    public PteroApi(String applicationUrl, Account account) {
        super(applicationUrl, account.getKey());
    }

}
