import { useSelector } from 'react-redux';
import { MainState } from '../store';

export const useAuthStatus = (): boolean =>
  useSelector((state: MainState) => state.accessToken !== undefined);
