import React from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import styled from 'styled-components';
import { Link, useNavigate } from 'react-router-dom';
import logoImage from '../../assets/logo.jpg';

// Styled Components
const StyledNavbar = styled(Navbar)`
  background-color: #6a0dad;
  padding: 0.75rem 1rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

const Logo = styled.img`
  height: 60px;
  margin-right: 12px;
  border-radius: 5px;
`;

const BrandText = styled.span`
  color: #ffffff;
  font-weight: bold;
  font-size: 1.5rem;
  margin: auto;
`;

const StyledNavLink = styled(Link)`
  color: #ffffff;
  margin: 0 1rem;
  text-decoration: none;
  font-weight: 500;
  position: relative;
  transition: color 0.3s;

  &:hover {
    color: #ffc107;
  }

  &::after {
    content: '';
    display: block;
    height: 2px;
    background: #ffc107;
    width: 0;
    transition: width 0.3s;
    position: absolute;
    bottom: -4px;
    left: 0;
  }

  &:hover::after {
    width: 100%;
  }
`;

const NavbarContent = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
`;

const BrandSection = styled.div`
  display: flex;
  align-items: center;
`;

const Separator = styled.div`
  width: 1px;
  height: 40px;
  background-color: rgba(255, 255, 255, 0.5);
  margin: 0 1.5rem;
`;

const MenuSection = styled(Nav)`
  display: flex;
  align-items: center;
`;

// Optional logout handler logic
const CustomNavbar = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Optional: clear auth tokens or session info here
    navigate('/');
  };

  return (
    <StyledNavbar expand="md" variant="dark">
      <Container>
        <NavbarContent>
          <BrandSection>
            <Navbar.Brand as={Link} to="/" className="d-flex align-items-center">
              <Logo src={logoImage} alt="Logo" />
              <BrandText>QuickShip</BrandText>
            </Navbar.Brand>
          </BrandSection>

          <Navbar.Toggle aria-controls="main-navbar-nav" />
          <Navbar.Collapse id="main-navbar-nav">
            <MenuSection className="ms-auto">
              <StyledNavLink to="/home">Inicio</StyledNavLink>
              <StyledNavLink to="/empleados">Empleados</StyledNavLink>
              <StyledNavLink to="/repartidores">Repartidores</StyledNavLink>
              <StyledNavLink to="/pedido">Pedidos</StyledNavLink>
              <StyledNavLink to="/auditoria">Auditoría</StyledNavLink>
            </MenuSection>
          </Navbar.Collapse>
          <Separator />
          <StyledNavLink as="button" onClick={handleLogout} style={{ background: 'none', border: 'none' }}>
                Cerrar sesión
          </StyledNavLink>
        </NavbarContent>
      </Container>
    </StyledNavbar>
  );
};

export default CustomNavbar;