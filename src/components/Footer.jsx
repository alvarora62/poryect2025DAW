import { Container } from 'react-bootstrap';
import styled from 'styled-components';

// Styled Components
const FooterWrapper = styled.footer`
  background-color: #343a40; /* darker and more elegant */
  padding: 1rem 0;
  width: 100%;
  position: fixed;
  bottom: 0;
  z-index: 1000;
`;

const FooterText = styled.p`
  text-align: center;
  margin: 0;
  color: #adb5bd;
  font-size: 0.9rem;
  letter-spacing: 0.5px;
`;

// Component
const CustomFooter = () => {
  return (
    <FooterWrapper>
      <Container>
        <FooterText>
          © {new Date().getFullYear()} Your Company Name. All rights reserved.
        </FooterText>
      </Container>
    </FooterWrapper>
  );
};

export default CustomFooter;
