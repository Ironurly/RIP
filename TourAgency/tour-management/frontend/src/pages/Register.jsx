import React, {useState} from "react";
import "../styles/login.css";
import {Container, Row, Col, Form, FormGroup, Button} from "reactstrap";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

import registerImg from "../assets/images/register.png";
import userIcon from "../assets/images/user.png";

const Register = () => {
    const [credentials, setCredentials] = useState({
        username: "",
        email: "",
        password: "",
    });

    const navigate = useNavigate();

    const handleChange = (e) => {
        setCredentials((prev) => ({...prev, [e.target.id]: e.target.value}));
    };

    const handleClick = async (e) => {
        e.preventDefault();

        try {
            // Отправка данных на сервер
            const response = await axios.post("http://localhost:8085/api/users", credentials);
            alert("Account created successfully!"); // Уведомление об успехе
            navigate("/login"); // Перенаправление на страницу входа
        } catch (error) {
            alert("Failed to register! Please try again."); // Уведомление об ошибке
            console.error(error);
        }
    };

    return (
        <section>
            <Container>
                <Row>
                    <Col lg="8" className="m-auto">
                        <div className="login__container d-flex justify-content-between">
                            <div className="login__img">
                                <img src={registerImg} alt=""/>
                            </div>

                            <div className="login__form">
                                <div className="user">
                                    <img src={userIcon} alt=""/>
                                </div>
                                <h2>Register</h2>

                                <Form onSubmit={handleClick}>
                                    <FormGroup>
                                        <input
                                            type="text"
                                            placeholder="Username"
                                            required
                                            id="username"
                                            onChange={handleChange}
                                        />
                                    </FormGroup>
                                    <FormGroup>
                                        <input
                                            type="email"
                                            placeholder="Email"
                                            required
                                            id="email"
                                            onChange={handleChange}
                                        />
                                    </FormGroup>
                                    <FormGroup>
                                        <input
                                            type="password"
                                            placeholder="Password"
                                            required
                                            id="password"
                                            onChange={handleChange}
                                        />
                                    </FormGroup>
                                    <Button
                                        className="btn secondary__btn auth__btn"
                                        type="submit"
                                    >
                                        Create account
                                    </Button>
                                </Form>
                                <p>
                                    Have an account? <Link to="/login">Login</Link>
                                </p>
                            </div>
                        </div>
                    </Col>
                </Row>
            </Container>
        </section>
    );
};

export default Register;
