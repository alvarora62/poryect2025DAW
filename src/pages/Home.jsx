import React from 'react';
import CustomNavbar from '../components/Navbar';
import { Container } from 'react-bootstrap';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

const StyledContainer = styled(Container)`
  margin-top: 2rem;
  text-align: center;
`;

const HomePage = () => {
  return (
    <>
      <CustomNavbar />
      <StyledContainer>
        <h1>Bienvenido al Sistema</h1>
        <p>Selecciona una sección del menú para continuar.</p>
      </StyledContainer>
    </>
  );
};

export default HomePage;
