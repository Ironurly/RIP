import React, { useState } from "react";
import axios from "axios"; // Импорт axios для работы с API
import "../styles/login.css";
import { Container, Row, Col, Form, FormGroup, Button } from "reactstrap";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";


import loginImg from "../assets/images/login.png";
import userIcon from "../assets/images/user.png";

const Login = ({ setIsAuthenticated }) => { // Принимаем функцию setIsAuthenticated
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({ email: "", password: "" });

  const handleChange = (e) => {
    setCredentials((prev) => ({ ...prev, [e.target.id]: e.target.value }));
  };

  const handleClick = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8085/api/users/login", credentials);
      console.log("Response data:", response.data); // Выводим данные ответа
      localStorage.setItem("token", response.data.token);
      localStorage.setItem("username", response.data.username);
      localStorage.setItem("isAdmin", response.data.isAdmin);
      localStorage.setItem("userId", response.data.userId);
      setIsAuthenticated(true);
      navigate("/");
    } catch (error) {
      console.error("Login error:", error); // Логируем ошибку
      if (error.response) {
        console.error("Response data:", error.response.data); // Данные ответа ошибки (если есть)
      }
      alert("Login failed! Please check your credentials.");
    }
  };


  return (
      <section>
        <Container>
          <Row>
            <Col lg="8" className="m-auto">
              <div className="login__container d-flex justify-content-between">
                <div className="login__img">
                  <img src={loginImg} alt="Login illustration" />
                </div>

                <div className="login__form">
                  <div className="user">
                    <img src={userIcon} alt="User icon" />
                  </div>
                  <h2>Login</h2>

                  <Form onSubmit={handleClick}>
                    <FormGroup>
                      <input
                          type="email"
                          placeholder="email"
                          required
                          id="email"
                          onChange={handleChange}
                      />
                    </FormGroup>
                    <FormGroup>
                      <input
                          type="password"
                          placeholder="password"
                          required
                          id="password"
                          onChange={handleChange}
                      />
                    </FormGroup>
                    <Button
                        className="btn secondary__btn auth__btn"
                        type="submit"
                    >
                      Login
                    </Button>
                  </Form>
                  <p>
                    Don't have an account? <Link to="/register">Create</Link>
                  </p>
                </div>
              </div>
            </Col>
          </Row>
        </Container>
      </section>
  );
};

export default Login;
