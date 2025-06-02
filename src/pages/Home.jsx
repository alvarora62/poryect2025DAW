import CustomNavbar from '../components/generic/Navbar';
import CustomFooter from '../components/generic/Footer';
import { Container, Row, Col } from 'react-bootstrap';
import styled from 'styled-components';
import InfoCard from '../components/home/InfoCard';

const StyledMain = styled.main`
  flex: 1;
  background-color: #f8f9fa;
  padding: 4rem 0;
`;

const StyledHeading = styled.h1`
  font-size: 2.5rem;
  color: #6a0dad;
  font-weight: 700;
  text-align: center;
  margin-bottom: 2rem;
`;

const HomePage = () => {
  return (
    <>
      <CustomNavbar />
      <StyledMain>
        <Container>
          <StyledHeading>Bienvenido al Sistema</StyledHeading>
          <Row>
            <Col md={6}>
              <InfoCard title="Resumen de Empleados" type="Empleado" />
            </Col>
            <Col md={6}>
              <InfoCard title="Resumen de Repartidores" type="Repartidor" />
            </Col>
          </Row>
          <Row>
            <Col md={6}>
              <InfoCard title="Pedidos Recientes" type="Pedido" />
            </Col>
            <Col md={6}>
              <InfoCard title="Auditorías" type="Auditoria" />
            </Col>
          </Row>
        </Container>
      </StyledMain>
      <CustomFooter />
    </>
  );
};

export default HomePage;
