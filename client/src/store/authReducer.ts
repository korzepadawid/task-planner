import { AuthState, AuthActionType, AuthActionTypes } from './storeTypes';

export const initialAuthState: AuthState = {
  accessToken: undefined,
  user: undefined,
  loggedAt: undefined,
};

export const authReducer = (
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  _: AuthState = initialAuthState,
  action: AuthActionType
): AuthState => {
  if (action.type === AuthActionTypes.AUTH_LOGIN) {
    const { user, accessToken } = action.payload;
    return { user, accessToken, loggedAt: new Date() };
  }
  return initialAuthState;
};
