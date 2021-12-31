import { Container } from '@mui/material';
import React from 'react';
import NavBar from '../components/NavBar';

interface Props {
  children: React.ReactNode;
}

const MainLayout: React.FC<Props> = ({ children }) => (
  <>
    <NavBar />
    <Container maxWidth="sm">{children}</Container>
  </>
);

export default MainLayout;
