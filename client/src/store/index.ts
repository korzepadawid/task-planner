import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';
import { authReducer } from './authReducer';

export type MainState = ReturnType<typeof authReducer>;

export const store = createStore(
  authReducer,
  composeWithDevTools(applyMiddleware(thunk))
);
