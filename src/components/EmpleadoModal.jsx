import React from 'react';
import { Modal, Form, Button } from 'react-bootstrap';

function EmpleadoModal({
  show,
  onHide,
  empleado,
  onChange,
  onSubmit,
  error,
  editMode
}) {
  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>{editMode ? 'Actualizar Empleado' : 'Añadir Nuevo Empleado'}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {error && <div className="alert alert-danger">{error}</div>}
        <Form onSubmit={onSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Nombre</Form.Label>
            <Form.Control
              type="text"
              name="nombre"
              value={empleado.nombre}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Nombre de Usuario</Form.Label>
            <Form.Control
              type="text"
              name="username"
              value={empleado.username}
              onChange={onChange}
              required
              disabled={editMode}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Contraseña</Form.Label>
            <Form.Control
              type="password"
              name="password"
              value={empleado.password}
              onChange={onChange}
              required
              disabled={editMode}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>DNI</Form.Label>
            <Form.Control
              type="text"
              name="dni"
              value={empleado.dni}
              onChange={onChange}
              required
              disabled={editMode}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Teléfono</Form.Label>
            <Form.Control
              type="tel"
              name="telefono"
              value={empleado.telefono}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              name="email"
              value={empleado.email}
              onChange={onChange}
              required
            />
          </Form.Group>
          <Button variant="primary" type="submit">
            {editMode ? 'Actualizar' : 'Guardar'}
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
}

export default EmpleadoModal;