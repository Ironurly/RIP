import React from "react";
import "./newsletter.css";
import {Container, Row, Col} from "reactstrap";
import maleTourist from "../assets/images/male-tourist.png";
import {useNavigate} from "react-router-dom";

const Newsletter = () => {
    const navigate = useNavigate()
    const handleClick = (e) => {
        e.preventDefault();

        navigate("/thank-you-sub");
    };
    return (
        <section className="newsletter">
            <Container>
                <Row>
                    <Col lg="6">
                        <div className="newsletter__content">
                            <h2>Subscribe now to get useful information.</h2>

                            <div className="newsletter__input">
                                <input type="email" placeholder="enter email"/>
                                <button className="btn newsletter__btn" onClick={handleClick}>Subscribe</button>
                            </div>

                            <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Quam consequuntur earum iste
                                pariatur reprehenderit a?</p>
                        </div>
                    </Col>
                    <Col lg="6">
                        <div className="newsletter__img">
                            <img src={maleTourist} alt=""/>
                        </div>
                    </Col>
                </Row>
            </Container>
        </section>
    );
};

export default Newsletter;
