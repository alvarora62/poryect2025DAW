import React from 'react';
import { Navbar, Container } from 'react-bootstrap';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import logoImage from '../../assets/logo.jpg';

// Styled Components
const StyledNavbar = styled(Navbar)`
  background-color: #6a0dad;
  padding: 0.75rem 1rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
`;

const Logo = styled.img`
  height: 40px;
  margin-right: 12px;
  border-radius: 5px;
`;

const BrandText = styled.span`
  color: #ffffff;
  font-weight: bold;
  font-size: 1.5rem;
`;

const NavbarContent = styled.div`
  display: flex;
  align-items: center;
`;

const BrandSection = styled.div`
  display: flex;
  align-items: center;
`;

const SimpleNavbar = () => {
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
        </NavbarContent>
      </Container>
    </StyledNavbar>
  );
};

export default SimpleNavbar;
