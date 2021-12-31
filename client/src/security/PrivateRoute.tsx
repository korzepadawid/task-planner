import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { useAuthStatus } from '../hooks/useAuthStatus';

interface Props {
  component: React.FC;
  path: string;
}

const PrivateRoute: React.FC<Props> = ({ component, path }) => {
  const isLogged = useAuthStatus();

  if (!isLogged) {
    return <Redirect to="/" />;
  }

  return <Route path={path} component={component} />;
};

export default PrivateRoute;
