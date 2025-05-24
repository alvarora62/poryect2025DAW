import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import logoImage from '../assets/logo.jpg';

const StyledNavbar = styled(Navbar)`
  background-color: purple;
`;

const StyledNavLink = styled(Link)`
  color: black;
  margin: 0 1rem;
  text-decoration: none;
  font-weight: 500;

  &:hover {
    color: #ffc107;
  }
`;

const Logo = styled.img`
  height: 40px;
  margin-right: 10px;
`;

const BrandText = styled.span`
  color: black;
  font-weight: bold;
  font-size: 1.25rem;
`;

const CustomNavbar = () => {
  return (
    <StyledNavbar expand="md">
      <Container>
        <Navbar.Brand as={Link} to="/">
          <Logo src={logoImage} alt="Logo" />
          <BrandText>QuickShip</BrandText>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="main-navbar-nav" />
        <Navbar.Collapse id="main-navbar-nav">
          <Nav className="ms-auto">
            <StyledNavLink to="/home">Inicio</StyledNavLink>
            <StyledNavLink to="/empleados">Empleados</StyledNavLink>
            <StyledNavLink to="/repartidores">Repartidores</StyledNavLink>
            <StyledNavLink to="/auditoria">Auditoría</StyledNavLink>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </StyledNavbar>
  );
};

export default CustomNavbar;
