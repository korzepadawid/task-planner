import React from 'react';
import { Container } from '@mui/material';
import NavBar from '../components/NavBar';
import { useAuthStatus } from '../hooks/useAuthStatus';

interface Props {
  children: React.ReactNode;
}

const MainLayout: React.FC<Props> = ({ children }) => {
  const isLogged = useAuthStatus();
  return (
    <>
      {isLogged && <NavBar />}
      <Container maxWidth="md">{children}</Container>
    </>
  );
};

export default MainLayout;
