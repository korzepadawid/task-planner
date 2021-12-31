import axios from 'axios';
import { ThunkAction } from 'redux-thunk';
import { StoreType } from '.';
import { GET_MY_PROFILE_URL } from '../constants/urls';
import { AuthActionType, AuthActionTypes, User } from './storeTypes';

export const login = (
  accessToken: string
): ThunkAction<void, StoreType, unknown, AuthActionType> => async (
  dispatch
) => {
  const { data } = await axios.get(GET_MY_PROFILE_URL, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  });
  const { id, name, avatarUrl }: User = data;
  return dispatch({
    type: AuthActionTypes.AUTH_LOGIN,
    payload: {
      accessToken,
      user: { id, name, avatarUrl },
    },
  });
};

export const logout = (): ThunkAction<
  void,
  StoreType,
  unknown,
  AuthActionType
> => (dispatch) => dispatch({ type: AuthActionTypes.AUTH_LOGOUT });
