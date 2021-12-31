import { useSelector } from 'react-redux';
import { StoreType } from '../store';

export const useAuthStatus = (): boolean =>
  useSelector((state: StoreType) => state.accessToken !== undefined);
