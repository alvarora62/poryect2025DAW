import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Auth from "../pages/Auth";
import Empleados from "../pages/Empleados";
import Repartidores from "../pages/Repartidores";
import Auditoria from "../pages/Auditoria";
import PedidoPage from "../pages/Pedido";

const AppRouter = () => {
  return (
    <Router>
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route path="/" element={<Auth />} />	
          <Route path="/empleados" element={<Empleados />} />
          <Route path="/repartidores" element={<Repartidores />} />
          <Route path="/auditoria" element={<Auditoria />} />
          <Route path="/pedido" element={<PedidoPage />} />
        </Routes>
    </Router>
  );
};

export default AppRouter;

/*


import About from "../pages/About";
import Contact from "../pages/Contact";


 <Route path="/about" element={<About />} />
          <Route path="/contact" element={<Contact />} />
*/