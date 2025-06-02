import CustomNavbar from '../components/generic/Navbar';
import CustomFooter from '../components/generic/Footer';
import { Container } from 'react-bootstrap';
import styled from 'styled-components';

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
      <CustomFooter />
    </>
  );
};

export default HomePage;
