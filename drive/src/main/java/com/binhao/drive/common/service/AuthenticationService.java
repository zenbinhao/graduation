package com.binhao.drive.common.service;

import com.binhao.drive.common.bo.SessionUser;

public interface AuthenticationService {

    String getAuthToken();

    SessionUser getSessionUser();

    SessionUser getSessionUserByIgnoreFilter(String var1);

}
