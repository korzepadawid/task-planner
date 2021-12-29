import { Container } from '@mui/material';
import React from 'react';

interface Props {
  children: React.ReactNode;
}

const MainLayout: React.FC<Props> = ({ children }) => (
  <Container maxWidth="sm">{children}</Container>
);

export default MainLayout;
