import { createStore, applyMiddleware, Store } from 'redux';
import thunk from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';
import { authReducer } from './authReducer';
import { AuthActionType, AuthState } from './storeTypes';

export const store: Store<AuthState, AuthActionType> = createStore(
  authReducer,
  composeWithDevTools(applyMiddleware(thunk))
);

export type StoreType = typeof store;
