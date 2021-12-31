export interface User {
  id: number;
  name: string;
  avatarUrl: string;
}

export type AuthState = {
  user: User | undefined;
  accessToken: string | undefined;
  loggedAt: Date | undefined;
};

export enum AuthActionTypes {
  AUTH_LOGIN = 'AUTH_LOGIN',
  AUTH_LOGOUT = 'AUTH_LOGOUT',
}

export interface LoginAction {
  type: AuthActionTypes.AUTH_LOGIN;
  payload: {
    accessToken: string;
    user: User;
  };
}

export interface LogoutAction {
  type: AuthActionTypes.AUTH_LOGOUT;
}

export type AuthActionType = LoginAction | LogoutAction;
